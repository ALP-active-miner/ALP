"""

"""

from numpy.random import choice

import sys

from os import listdir
from os.path import isfile, join
from IPython import embed

directory_path = sys.argv[1]

onlyfiles = [f for f in listdir(directory_path) if isfile(join(directory_path, f)) and f.endswith('misuses.csv')]

project_urls = {
	'bcel' : 'https://github.com/apache/commons-bcel/blob/a35b256ce3c2aebcc96d054b2a526bb37b4f0bd4',
	'asterisk-java': 'https://github.com/asterisk-java/asterisk-java/blob/304421c261da68df03ad2fb96683241c8df12c0a',
	'chensun': 'https://github.com/ms969/ChenSun/blob/cf23b99a0c78596b5dc5bb25691736c9abd0a84d',
	'closure': 'https://github.com/google/closure-library/blob/840ddca5b28cea7563a5be20d2624478af67bc02',
	'itext': 'https://github.com/itext/itextpdf/blob/adda8eb38978eeea6e86aca64ae746754c103dae',
	'jigsaw': '~/Downloads',
	'jmrtd': '~/Downloads/jmrtd',
	'jodatime': 'https://github.com/JodaOrg/joda-time/blob/64a991fcb7651919d85bc50456730c693c7e10e1',
	'lucene': 'https://github.com/apache/lucene-solr/blob/7c757a03c16e6e60d73abeec699d8db81a21ee32',
	'testng': 'https://github.com/cbeust/testng/blob/677302cb8b5a2507df97c5822eef3a03ebc4e23a'
}

for f in onlyfiles:

	pairs = []
	print('reading ', directory_path + '/' + f)
	with open(directory_path + '/' + f) as infile:
		for line in infile:
			splitted = line.split(',')
			value = float(splitted[-1])

			pairs.append((line, value))

	lines = [line for line, value in pairs]
	probs = [value for line, value in pairs]

	lowest = min(probs)
	

	probs = [-1 * lowest + prob for prob in probs]
	highest = max(probs)
	offset = highest / 10

	probs = [prob + offset for prob in probs]
	sum_probs = sum(probs)

	probs = [prob / sum_probs for prob in probs]

	# print(probs)

	if len(probs) < 20:
		draw = lines
	else:
		draw = choice(lines, 20, p=probs, replace=False)
	print(draw)

	with open(directory_path + '/' + f.split('.csv')[0] + "_20_random.csv", 'w+') as outfile:
		for one in draw:

			splitted = one.split(',')
			path = splitted[0].split('/Users/first_author/repos/MUBench/mubench-checkouts/')[1]
			method = splitted[0].split(' - ')[1]

			project = path.split('/')[0]

			# url = project_urls[project] + '/' + file_location

			uri = '~/Downloads/benchmark_projects/' + path.split(' - ')[0]

			API = splitted[1]

			outfile.write(API + "," + uri + "," + method +",")
			outfile.write('\n')

