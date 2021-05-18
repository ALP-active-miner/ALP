print('importing')


import networkx as nx
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_selection import mutual_info_classif

import sys

import networkx.algorithms.isomorphism as iso
from joblib import dump, load

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
from sklearn.metrics import make_scorer, accuracy_score, precision_score, recall_score, f1_score

print('done importing')

scoring = {'accuracy' : make_scorer(accuracy_score), 
           'precision' : make_scorer(precision_score),
           'recall' : make_scorer(recall_score), 
           'f1_score' : make_scorer(f1_score)}

#  sys argv: directory prefix
# e.g.  python classify.py /Users/first_author/repos/ALP/output_main/java.util.StringTokenizer__nextToken__0/ java.util.StringTokenizer__nextToken__0 

import random

print('dir=', sys.argv[1])
directory = sys.argv[1] if sys.argv[1].endswith('/') else sys.argv[1] + '/'
print('api (used as prefix for look for files) = ', sys.argv[2])

feature_vector_path = directory + sys.argv[2] + '_formatted_result_features.txt'
best_subgraphs_path = directory + sys.argv[2] + '_formatted_result_best_subgraphs.txt'
subgraphs_path = directory + sys.argv[2] + '_formatted_result'
test_path =  directory + sys.argv[2] + '_test_formatted.txt'
file_id_mappings_path= directory + sys.argv[2] + '_test_graph_id_mapping.txt'


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
	result = {}
	for line in open(file_id_mappings_path):
		splitted = line.split(',')
		dummy = splitted[0]
		index = int(splitted[1])
		testfile = splitted[2]
		file = testfile.split('- ')[-1]

		result[index] = file
	return result

def train_outlier_detection(X):
	from sklearn.neighbors import LocalOutlierFactor

	lof = LocalOutlierFactor(n_neighbors=5,contamination=0.01, novelty=True, p =2)
	lof.fit(X)

	return [lof]

def train_model():
	# feature_vector_path = sys.argv[1] # "/Users/first_author/repos/ALP/output_main/java.io.ByteArrayOutputStream__toByteArray__0_formatted.txt_result_features.txt"

	orig_table = pd.read_csv(feature_vector_path)
	orig_table = orig_table.drop('graph_id',axis=1)

	table = orig_table.sample(frac=1)

	Y = table.iloc[:, 0].values
	X = table.iloc[:, 1:].values

	df = pd.DataFrame(X)
	from sklearn.utils import shuffle

	corr_matrix = df.corr().abs()
	upper = corr_matrix.where(np.triu(np.ones(corr_matrix.shape), k=1).astype(np.bool))

	# Find index of feature columns with correlation greater than 0.95
	# to_drop = [column for column in upper.columns if any(upper[column] > 0.95)]
	to_drop = []
	for column in upper.columns:
		if any(upper[column] > 0.95):
			to_drop.append(column)

	to_drop= []	

	new_df = df.drop(df[to_drop], axis=1)
	print("dropping: ", to_drop)
	

	class_weight={0: 2, 1: 1}
	class_weight2={0: 3, 1: 1}

	knn1 = KNeighborsClassifier(n_neighbors=1)
	knn = KNeighborsClassifier(n_neighbors=3)
	knn5 = KNeighborsClassifier(n_neighbors=5)
	log_reg = LogisticRegression(class_weight='balanced')
	log_reg2 = LogisticRegression(class_weight=class_weight)
	log_reg3 = LogisticRegression(class_weight=class_weight2)
	nb = BernoulliNB()
	nb1 = ComplementNB()
	# svm = SVC(gamma='auto', class_weight='balanced', probability=True)
	svm1 = SVC(gamma='auto', probability=True)
	svm2 = SVC(gamma='auto', class_weight=class_weight, probability=True)
	svm3 = SVC(gamma='auto', class_weight=class_weight2, probability=True)
	# lin_clf = LinearSVC( class_weight='balanced', probability=True)
	lin_clf2 = LinearSVC( class_weight=class_weight)
	lin_clf3 = LinearSVC( class_weight=class_weight2)
	tree = DecisionTreeClassifier(max_depth=5)
	tree2 = DecisionTreeClassifier(max_depth=5,class_weight = class_weight)
	tree3 = DecisionTreeClassifier(max_depth=5,class_weight = class_weight2)
	forest = RandomForestClassifier(max_depth=5, random_state=0)
	# eclf = VotingClassifier(estimators=[ 
	# 	('knn', knn), ('knn5', knn5), ('log_reg', log_reg), ('nb', nb), ('nb1', nb1), ('svm1', svm1), ('tree', tree)],voting='soft')


	models =  [ nb,nb1,  svm3, lin_clf3, knn, knn5]
	best_model = None
	for model in models:
		scores = cross_validate(model, new_df, Y, cv=2, scoring=scoring)
		print(model)
		# print('scores are')
		# print(scores)

		f_scores = scores['test_f1_score']
		print('average f_scores', np.mean(f_scores))
		print('===')

		if best_model is None or np.mean(f_scores) > best_model[1]:
			best_model = (model, np.mean(f_scores) )

	print('		using')
	print(best_model)

	table = orig_table.sample(frac=1)
	Y = table.iloc[:, 0].values
	X = table.iloc[:, 1:].values
	best_model[0].fit(X, Y)

	novelty_detectors = train_outlier_detection(table.iloc[:, 1:].values)


	return  to_drop,  [best_model[0]], novelty_detectors, X


try:
	print('\ttrying to read models')
	to_drop = load('models/' + sys.argv[2] + '_to_drop.joblib') 
	print('read to drop')
	models = load('models/' + sys.argv[2] + '_models.joblib') 
	print('read model')
	subgraphs = load('models/' + sys.argv[2] + '_subgraphs.joblib') 
	print('read subgraphs')
	novelty_detector = load('models/' + sys.argv[2] + '_novelty_detector.joblib')
except Exception as e:
	print(e)
	print('\tfailed to read models')
	print('\tinstead, reading features and training model')
	# to_drop, models, novelty_detectors, X = train_model()
	raise e

# read subgraph features
def read_selected_subgraph_ids():
	subgraph_ids = []
	# best_subgraphs_path =sys.argv[2] # "/Users/first_author/repos/ALP/output_main/java.io.ByteArrayOutputStream__toByteArray__0_formatted.txt_result_best_subgraphs.txt"
	with open(best_subgraphs_path) as infile:
		for line in infile:
			splitted = line.split(',')
			subgraph_ids.append(splitted[0])
	return subgraph_ids

print('\treading selected subgraph features')
subgraph_ids = read_selected_subgraph_ids()

# for debugging
subgraph_ids_ordered_by_subgraph_file = []

def read_subgraph_features():
	subgraphs = []
	# subgraphs_path = sys.argv[3] # "/Users/first_author/repos/ALP/output_main/java.io.ByteArrayOutputStream__toByteArray__0_formatted.txt_result"
	with open(subgraphs_path) as infile:
		current_subgraph_id = None
		SG = None
		for line in infile:
			splitted = line.split()
			if splitted[0] == 't':
				current_subgraph_id = splitted[2]

				if current_subgraph_id not in subgraph_ids:
					#skip this subgraph
					SG = None
					continue

				SG = nx.MultiDiGraph()
				subgraphs.append(SG)	
				subgraph_ids_ordered_by_subgraph_file.append(current_subgraph_id)
			elif splitted[0] == 'v':
				node_id = splitted[1]
				label = splitted[2]
				if SG is not None:
					SG.add_node(node_id, label=label) 

			elif splitted[0] == 'e':
				source = splitted[1]
				target = splitted[2]
				label = splitted[3]
				if SG is not None:
					SG.add_edge(source, target, label=label )

			else:
				raise Exception("incorrect")

		if SG is not None:
			subgraphs.append(SG)	
	return subgraphs

subgraphs = read_subgraph_features()

assert len(subgraphs) == len(subgraph_ids)

def read_graphs():
	# construct feature vectors for testing instances
	graphs = []
	# test_path = sys.argv[4] #"/Users/first_author/repos/ALP/output_main/java.io.ByteArrayOutputStream__toByteArray__0_testing_formatted.txt"
	print('\t\t from ', test_path)
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
				from IPython import embed
				embed()
				raise Exception("incorrect")
	return graphs

print('\treading the graphs')
graphs = read_graphs()

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
					print('test graph ' , i, 'test has :', str(j), " subgraph id=",str(subgraph_ids_ordered_by_subgraph_file[j]))
				subgraph_vector[0][j] = 1.0
		test_vector[i] = subgraph_vector
	df = pd.DataFrame(test_vector)
	new_df = df.drop(df[to_drop], axis=1)

	return new_df

test_vector = construct_test_vector()

print('predicting')

file_mappings = get_file_id_mappings()

print('zero vector')
zero_vector = np.zeros((1, test_vector.shape[1]))
print(models[0].predict(zero_vector))


print(file_mappings)
for model in models:
	print('model' , model)
	outliers = novelty_detector[0].predict(test_vector)
	outlier_score = novelty_detector[0].score_samples(test_vector)
	preds = model.predict(test_vector)
	# print(test_vector)
	print(preds)
	for file_id in file_mappings.keys():

		if outliers[file_id] < 0:
			print(file_mappings[file_id],'is outlier', outlier_score[file_id])
		else:
			print('answer: ', file_mappings[file_id], " = " , preds[file_id])
		

print('1: correct usage. 0: misuse')
