README
============


This repository, https://github.com/ALP-active-miner/ALP, accompanies the paper Active Learning of Discriminative Subgraph Patterns for API Misuse Detection.

The document optim.pdf is an accompanying report to describe some optimizations and design decisions made in the subgraph mining procedure.


Apart from that, this repository contains several major components:
1. github-code-search: we modify the code from https://github.com/mhilmiasyrofi/AUsearch ([1]) in order to perform deduplication of code-clones.
	This is used to search for examples on Github to mine patterns from.

2. MUDetect: modified from https://github.com/stg-tud/MUDetect ([2] ). 
3. gSpan.Java: modified from https://github.com/TonyZZX/gSpan.Java (which is a port of earlier implementations of the well-known gSpan algorithm [3]).
	This is modified to incorporate labels on the graphs, and while performing frequent subgraph mining, it mines discriminative subgraphs together.
4. networkx
	we include modifications we performed on the networkx[4] package (https://github.com/networkx), to fix a bug that has been reported to the project maintainers.
5. graph-classification
	misc scripts, including the scripts for running the evaluation
6. patterns-mined
	the subgraph patterns mined for the APIs


We thank the developers of the AUSearch, MUDetect, gSpan.Java, and networkx for their excellent work and ease-of-use of their projects.



Searching for API Usage examples
=================

Instructions are similar to the original software released by the authors of [1],
however,
we made several modifications to speed up performance and to get around Github's search limitations of only 1000 results.
To speed up performance, code clones are deduplicated.
To get around the 1000 results limit, we partition the search results by their size. i.e. first, we restrict our search to files of size [0,n-1], then we search for files of size [n, 2n-1], then [2n, 3n-1], and so on...

Run the following command, replacing <API token> with your API token created on https://github.com/settings/tokens.
```
java -cp target/github-code-search-1.0-SNAPSHOT-jar-with-dependencies.jar com.project.githubsearch.App "java.awt.Shape#getPathIterator(0)" 2500 <API token> true
```
This example command searches for 2500 unique examples of Shape.getPathIterator.



Labeling
================

Randomly select examples to label:
Run ALPFilesPreprocessor.java. This creates a text file with "?" for the examples you have to label.

After labeling, 
run ALPPipelineGraphBuilder.java to convert them in the graph format.
The graphs, node labels, edge labels will be output to src2egroum2aug/output.

Mining discriminative subgraphs
=================
Next, run the modified gSpan on the graph file (they are formatted similar to the example written below in Reading the graphs and subgraphs).

```
java -Xmx64G -jar GSpanMiner.jar -d <API>_formatted.txt -b <API>_vertmap.txt -s <min_sup> -a <max_size>
```

where <API>_formatted.txt and <API>_vertmap.txt are the outputs of the ALPGraphBuilder.

The subgraph mining process will return multiple output files, including:
<API>_result
<API>_result_best_subgraphs.txt
<API>_result_features.txt

The number of covered graphs is printed to standard output. If more labels are required, use clingo to 
solve the logic program in <API>_result_interesting_subgraphs_coverage.lp.
We used the docker image `kdrakon/clingo:latest` to do this, but one could imagine that there are other means to solve the lp file.
The output of solving this are the graph ids requiring labeling.
One would have to map the graph id back to the actual file id on your file system: use the bookkeeping file `<API>_graph_id_mapping.txt` that was created earlier when we ran the source code -> graph conversion (ALPPipelineGraphBuilder.java) 

Reading the graphs and subgraphs
=================

Below is an example of a text file containing 2 graphs. Each line denodes a vertex (v) or edge (e) with a given label (end of line).
t denotes the start of the graph. # <id> just denotes an id of the subgraph, mainly used for bookkeeping internally. "* <count>" indicates the number of the times the subgraph appears.

t # 0 * 10
v 0 2
v 1 2
v 2 2
v 3 3
v 4 2
e 0 1 2
e 0 2 2
e 2 3 3
e 2 4 2
t # 1 * 50
v 0 2
v 1 2
v 2 6
e 0 1 2
e 0 2 2


Running on a new input
==============

ALPPipelineTestDataGraphBuilder.java should be executed to convert a new input project into EAUGS, and into the expected format.

```
python get_probaility.py <output_dir>/<project_name>___<API>/ <API> 
```

Should return the outlier score, as well as the judgement of the classifier of each usage instance of this API in the project.
We have provided models for the APIs in the `models` directory




[1] Muhammad Hilmi Asyrofi, Ferdian Thung, David Lo, and Lingxiao Jiang. 2020. AUSearch: Accurate API Usage Search in GitHub Repositories with Type Resolution. In IEEE International Conference on Software Analysis, Evolution and Reengineering.
[2] Sven Amann, Hoan Anh Nguyen, Sarah Nadi, Tien N Nguyen, and Mira Mezini. 2019. Investigating next steps in static API-misuse detection. In 2019 IEEE/ACM 16th International Conference on Mining Software Repositories (MSR)
[3] Xifeng Yan and Jiawei Han. 2002. gspan: Graph-based substructure pattern mining. In 2002 IEEE International Conference on Data Mining, 2002.
[4] Aric Hagberg, Pieter Swart, and Daniel S Chult. 2008. Exploring network structure, dynamics, and function using NetworkX.
