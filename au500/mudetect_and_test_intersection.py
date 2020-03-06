"""
This script is to take the intersection of :
1. the test instances (400 instances) of API usage which we will annotate
2. the identified misuse instances of MUDetect

Mainly useful for finding an estimate of the progress of MUdetect.
This script differs from output_and_test_intersection.py in that it ignores the class of the method name:
e.g. getHeaderPanel#Map# vs FontEncodingView.getHeaderPanel#Map#
"""




test_cases = []
test_cases_misuses = []

with open('annotation_prep/to_annotate.csv') as infile:
	infile.readline()
	for line in infile:
		API = line.split(',')[0]
		API =  API.split('__')[0].split('.')[-1] + "." + API.split('__')[1]

		method = line.split(',')[1]
		github_url = line.split(',')[2]
		
		path = github_url.split('/')[4] + '/' + '/'.join(github_url.split('/')[7:])
		path = path.split('#')[0]

		
		after_classname = '.'.join(method.split('.')[1:])

		is_misuse = line.split(',')[3].strip() == 'M'

		
		location = path + ' - ' + after_classname + "," + API
		# print(location, is_misuse)

		test_cases.append(location)
		if is_misuse:
			test_cases_misuses.append(location)


identified_misuses = set()
with open('mudetect_findings.csv') as infile:
	for line in infile:
		line = line.strip()	

		# location_and_api = line[:line.rindex(',')]
		location = line.split(',')[0]
		location = location.split('/Users/first_author/repos_for_misuses/')[1]
		APIs = line.strip().split(',')[1:]
		for API in APIs:

			location_and_api = location + ',' +API.split('(')[0] # API should look like Class.methodName

			identified_misuses.add(location_and_api)



print('==== from mudetect_and_test_intersection =====')

print('total guesses by mudetect', len(set(test_cases) & identified_misuses))
print('total correct misuse guesses by output', len(set(test_cases_misuses) & identified_misuses))
print('total misuses in test', len(test_cases_misuses))

preds = set(test_cases) & identified_misuses
corrects = set(test_cases_misuses) & identified_misuses
false_positives =  preds - corrects

