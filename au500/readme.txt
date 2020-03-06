ALPPipelineCombOtherProjectForAPIUsageGraphBuilder.java should be run, after modifying the paths to point to the right paths.
e.g. 
"/Users/first_author/repos_for_misuses/commons-bcel/",
"/Users/first_author/repos_for_misuses/commons-math/",
"/Users/first_author/repos_for_misuses/commons-text/",
"/Users/first_author/repos_for_misuses/curator/",
"/Users/first_author/repos_for_misuses/directory-fortress-core/",
"/Users/first_author/repos_for_misuses/h2database/",
"/Users/first_author/repos_for_misuses/itextpdf/",
"/Users/first_author/repos_for_misuses/jackrabbit/",
"/Users/first_author/repos_for_misuses/jfreechart/",
"/Users/first_author/repos_for_misuses/pdfbox/",
"/Users/first_author/repos_for_misuses/santuario-java/",
"/Users/first_author/repos_for_misuses/swingx/",
"/Users/first_author/repos_for_misuses/wildfly-elytron/",
"/Users/first_author/repos_for_misuses/xmlgraphics-fop/",
"/Users/first_author/repos_for_misuses/commons-lang/",
"/Users/first_author/repos_for_misuses/bigtop/"

Take note to use the right commits:

FOP:  1942336d7
swingx:  820656c
jfreechart:  893f9b15
itext:  2d5b6a212
commons-lang:  0820c4c89
commons-math:   1abe3c769
commons-text:   7d2b511
commons-bcel:   5cc4b163
fortress:   a7ab0c01
santuario:  3832bd83
pdfbox:  72249f6ff
wildfly:  a73bbba0f0
jackrabbit:  da3fd4199
h2database:  0ea0365c2
curator:   2af84b9f
bigtop:  c9cb18fb

The 500 selected instnaces are in to_annotate.csv.
These are the labels that are already discussed and resolved.

To summarize the different tools and their performance on AU500,
here are the outputs of running them:

MUDetect's output (from mudetect_findings.csv):
==== from mudetect_and_test_intersection =====
total guesses by mudetect 123
total correct misuse guesses by output 34
total misuses in test 115
precision =  0.2764227642276423
recall =  0.2956521739130435
F1 =  0.28571428571428575

ALP's output 
==== from output_and_test_intersection =====
total guesses by output 141
total correct misuse guesses by output 63
total misuses in test 115
precision =  0.44680851063829785
recall =  0.5478260869565217
F1 =  0.49218749999999994

ALP's output, but without the reject option
==== from output_without_reject_and_test_intersection =====
total guesses by output_without_reject 200
total correct misuse guesses by output_without_reject 72
total misuses in test 115
precision =  0.36
recall =  0.6260869565217392
F1 =  0.45714285714285713

