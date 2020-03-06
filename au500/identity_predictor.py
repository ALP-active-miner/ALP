"""
Prints out test instances of instances mined in ALPPipelineCombOtherProjectForAPIUsageGraphBuilder.java.
Useful for printing out the test instances in the same format as out the other output.


"""

# print('importing')


import numpy as np
import pandas as pd

import sys


from joblib import dump, load


# ok let's change it up
#  sys argv: directory prefix
# e.g.  python classify2.py /Users/first_author/repos/MUDetect/src2egroum2aug/output/java.util.StringTokenizer__nextToken__0/ java.util.StringTokenizer__nextToken__0 

import random


# print('dir=', sys.argv[1])
directory = sys.argv[1] if sys.argv[1].endswith('/') else sys.argv[1] + '/'
# print('api (used as prefix for look for files) = ', sys.argv[2])

test_path =  directory + sys.argv[2] + '_othercombing_test_formatted.txt'
file_id_mappings_path= directory + sys.argv[2] + '_othercombing_test_graph_id_mapping.txt'

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


file_mappings = get_file_id_mappings()

for file_id in file_mappings.keys():
	print(file_mappings[file_id].strip() + "," + sys.argv[2])

sys.stderr.write("done\n")

