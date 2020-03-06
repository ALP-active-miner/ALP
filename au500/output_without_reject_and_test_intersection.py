"""
This script is to take the intersection of :
1. the test instances (400 instances) of API usage which we will annotate
2. the identified misuse instances of our tool

Mainly useful for finding an estimate of the progress of our tool
"""


test_cases = []
test_cases_misuses = []

with open('annotation_prep/to_annotate.csv') as infile:
	infile.readline()
	for line in infile:
		API = line.split(',')[0]
		method = line.split(',')[1]
		github_url = line.split(',')[2]
		
		path = github_url.split('/')[4] + '/' + '/'.join(github_url.split('/')[7:])
		path = path.split('#')[0]

		is_misuse = line.split(',')[3].strip() == 'M'

		
		location = path + ' - ' + method + "," + API
		# print(location, is_misuse)

		test_cases.append(location)
		if is_misuse:
			test_cases_misuses.append(location)

identified_misuses = set()
with open('all_probability_for_othercombing_no_reject_instances.txt') as infile:
	for line in infile:
		location_and_api = line[:line.rindex(',')]
		identified_misuses.add(location_and_api.split('/Users/first_author/AU500_repositories/')[1])


print('==== from output_without_reject_and_test_intersection =====')
print('total guesses by output_without_reject', len(set(test_cases) & identified_misuses))
print('total correct misuse guesses by output_without_reject', len(set(test_cases_misuses) & identified_misuses))
print('total misuses in test', len(test_cases_misuses))
prec = len(set(test_cases_misuses) & identified_misuses) / len(set(test_cases) & identified_misuses)
print('precision = ', prec)
recall = len(set(test_cases_misuses) & identified_misuses) / len(test_cases_misuses)
print('recall = ', recall)

print('F1 = ', (2 * prec * recall / (prec + recall)))

# for item in set(test_cases) & identified_misuses:
# 	print(item)

# from IPython import embed
# embed()
