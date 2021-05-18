The data in the AU500 can be found in annotation_prep/to_annotate.csv


To summarize the different tools and their performance on AU500,
here are the outputs of running them:


ALP's output:

precision =  0.4152046783625731
recall =  0.6173913043478261
F1 =  0.4965034965034965

ALP's output, but without the reject option:

precision =  0.36
recall =  0.6260869565217392
F1 =  0.45714285714285713



To construct the graphs again,
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
These are the labels that were already discussed and resolved.

