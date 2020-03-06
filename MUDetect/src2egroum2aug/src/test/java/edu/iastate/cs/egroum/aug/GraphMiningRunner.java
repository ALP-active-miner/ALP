package edu.iastate.cs.egroum.aug;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import school.first_author.ALPConstants;

public class GraphMiningRunner {

	public static void main(String... args) throws FileNotFoundException, IOException {
		if  (args.length < 2) {
			throw new RuntimeException("Please supply the running type and API name to the runner");
		}
		
		ALPConstants.APIUnderMiner = Arrays.asList(args[1]); 
		if (args[0].equals("preprocess")) {
			ALPFilesPreprocessor.preprocess();
		} else if (args[0].equals("mine")) {
			ALPGraphBuilder.buildGraphs();
		} else if (args[0].equals("buildTestGraphs")) {
			ALPPipelineTestDataGraphBuilder.buildGraphs(args[1]);
		} else if (args[0].equals("postprocess")) {
			ALPFilesPostprocessor.postprocess();
		}
		
	}
}
