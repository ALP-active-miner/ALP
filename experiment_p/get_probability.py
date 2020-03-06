"""
Finds usage of API in project. (Done in ALPPipelineCombProjectForAPIUsageGraphBuilder.java)


"""

# print('importing')


import networkx as nx
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_selection import mutual_info_classif

import sys

import networkx.algorithms.isomorphism as iso

from sklearn.ensemble import BaggingClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.feature_selection import SelectKBest, chi2, mutual_info_classif
from sklearn.naive_bayes import GaussianNB, BernoulliNB, ComplementNB
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC, LinearSVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import VotingClassifier
from sklearn.model_selection import cross_val_score, cross_validate



from joblib import dump, load


# ok let's change it up
#  sys argv: directory prefix
# e.g.  python get_probability.py /Users/first_author/repos/MUDetect/src2egroum2aug/output/java.util.StringTokenizer__nextToken__0/ java.util.StringTokenizer__nextToken__0 


# print('dir=', sys.argv[1])
directory = sys.argv[1] if sys.argv[1].endswith('/') else sys.argv[1] + '/'
# print('api (used as prefix for look for files) = ', sys.argv[2])

test_path =  directory + sys.argv[2] + '_combing_test_formatted.txt'
file_id_mappings_path= directory + sys.argv[2] + '_combing_test_graph_id_mapping.txt'


def categorical_multiedge_match_any(attr, default):
    if nx.utils.is_string_like(attr):
        def match(datasets1, datasets2):
            values1 = set([data.get(attr, default) for data in datasets1.values()])
            values2 = set([data.get(attr, default) for data in datasets2.values()])
            return all(value2 in values1 for value2 in values2)
    else:
        attrs = list(zip(attr, default))  # Python 3

        def match(datasets1, datasets2):
            values1 = set([])
            for data1 in datasets1.values():
                x = tuple(data1.get(attr, d) for attr, d in attrs)
                values1.add(x)
            values2 = set([])
            for data2 in datasets2.values():
                x = tuple(data2.get(attr, d) for attr, d in attrs)
                values2.add(x)
            return values1 == values2
    return match

def get_file_id_mappings():
	# file_id_mappings_path = sys.argv[5]
	result = {}
	for line in open(file_id_mappings_path):
		splitted = line.split(',')
		path = splitted[0]
		index = int(splitted[1])
		testfile = splitted[2]
		file = testfile.split('- ')[-1]

		result[index] = testfile
	return result



to_drop = load('models/' + sys.argv[2] + '_to_drop.joblib') 
models = load('models/' + sys.argv[2] + '_models.joblib') 
subgraphs = load('models/' + sys.argv[2] + '_subgraphs.joblib') 
novelty_detectors = load('models/' + sys.argv[2] + '_novelty_detector.joblib')
novelty_detector = novelty_detectors[0]

subgraph_ids_ordered_by_subgraph_file = load('models/' + sys.argv[2] + '_subgraph_ids_ordered_by_subgraph_file.joblib') 

# assert len(subgraphs) == len(subgraph_ids)

def read_graphs():
	# construct feature vectors for testing instances
	graphs = []
	# test_path = sys.argv[4] #"/Users/first_author/repos/MUDetect/src2egroum2aug/output/java.io.ByteArrayOutputStream__toByteArray__0_testing_formatted.txt"
	# print('\t\t from ', test_path)
	with open(test_path) as infile:
		G = None
		for line in infile:
			splitted = line.split()
			if splitted[0] == 't':
				current_graph_id = splitted[2]

				G  = nx.MultiDiGraph()
			elif splitted[0] == 'v':
				node_id = splitted[1]
				label = splitted[2]
				G .add_node(node_id, label=label)

			elif splitted[0] == 'e':
				source = splitted[1]
				target = splitted[2]
				label = splitted[3]
				G .add_edge(source, target, label=label )

			elif splitted[0] == '-':
				
				if G  is not None:
					graphs.append(G )
				G = None
			else:
				raise Error("incorrect")
	return graphs

try:
# print('\treading the graphs')
	graphs = read_graphs()
except Exception as e:
	print('err at ', sys.argv)
	raise e

def construct_test_vector():
	test_vector = np.zeros((len(graphs), len(subgraphs)))
	for i, graph in enumerate(graphs):
		subgraph_vector = np.zeros((1, len(subgraphs)))
		for j, subgraph in enumerate(subgraphs):
			# G1, G2 -> G1 should be the bigger one
			gm = nx.algorithms.isomorphism.MultiDiGraphMatcher(graph, subgraph, node_match=iso.categorical_node_match('label', None), edge_match=categorical_multiedge_match_any('label', None))
			has_iso = gm.subgraph_is_monomorphic()

			if has_iso:
				if j not in to_drop:
					pass
					# print('test graph ' , i, 'test has :', str(j), " subgraph id=",str(subgraph_ids_ordered_by_subgraph_file[j]))
				subgraph_vector[0][j] = 1.0
		test_vector[i] = subgraph_vector
	df = pd.DataFrame(test_vector)
	new_df = df.drop(df[to_drop], axis=1)

	return new_df

test_vector = construct_test_vector()


file_mappings = get_file_id_mappings()



count = 0

if len(test_vector) > 0:
	# print(file_mappings)

	answer_and_score = {}
	for model in models:

		outliers = novelty_detector.predict(test_vector)
		outlier_score = novelty_detector.score_samples(test_vector)

		preds = model.predict(test_vector)
		# embed()

		# print(test_vector)
		# print(preds)
		for file_id in file_mappings.keys():

			# print('answer: ', file_mappings[file_id].strip(), " = " , preds[file_id])

			# misuse_prob = preds[file_id][0]
			is_misuse = preds[file_id] == 0
			if outliers[file_id] < 0:

				continue
			if is_misuse:  #misuse_prob > 0.5:
				count += 1
				answer_and_score[file_mappings[file_id].strip() + "," + sys.argv[2]] = outlier_score[file_id]
				# sys.stderr.write("\tmisuse?" + file_mappings[file_id].strip() + "\n")
				# sys.stderr.write(str(test_vector[file_id : file_id+1]))
				# sys.stderr.write("\n")
				

		break

	sorted_ans = {k: v for k, v in sorted(answer_and_score.items(), key=lambda item: item[1])}

	for i, (ans, val) in enumerate(sorted_ans.items()):

		print(ans + "," + str(val))
		if i > 10:
			break
else:
	sys.stderr.write("test vector is 0-len\n")
# print(count, '/', len(file_mappings.keys()))

sys.stderr.write("done\n")