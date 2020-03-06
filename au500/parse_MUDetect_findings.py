"""
Use this to parse the output of MUdetect: e.g. the stuff in findings/xmlgraphics-fop-findings-output.yml
findings/.*-findings-output.yml

Print the output to mudetect_findings.csv
"""

import sys

locations_and_APIs = []
with open(sys.argv[1]) as infile:
    current_file = None
    current_method = None
    location = None

    is_reading_pattern_violation = False
    location_and_API = None

    for line in infile:

        if not is_reading_pattern_violation:
            if 'file:' in line:
                file = line.strip()
                file = '/Users/first_author/repos_for_misuses/' + file.split('/')[3] + '/' + '/'.join(file.split('/')[5:])
        
                current_file = file
            if 'method:' in line:
                method = line.split('method:')[1].strip().replace('()','#').replace('(', '#').replace(')','#')
        
                current_method = method
                location = current_file + ' - ' + current_method
            
            if 'pattern_violation: ' in line:
                is_reading_pattern_violation = True


        elif 'target_environment_mapping' in line:
            is_reading_pattern_violation = False  

            if location_and_API not in locations_and_APIs:
                locations_and_APIs.append(location_and_API)
            # print('===')     
            location_and_API = None

        else:
            if 'label=' in line and ') L' in line and 'shape="box"' in line:
                # print(line.strip())     
                name = line.split('label="')[1].split('"')[0]
                name = name.split(' L')[0]
                if location_and_API is None:
                    location_and_API = location + ',' + name
                else:
                    location_and_API += ',' + name    
                

for item in locations_and_APIs:
    print(item)

