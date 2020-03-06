package edu.iastate.cs.egroum.aug;

import java.io.IOException;

import org.junit.Test;

import school.first_author.ALPConstants;

/**
 * A convenience script, disguised as a test, that exists just to run the graph builder.
 * Configure things in ALPConstants.APIUnderMiner in ALPConstants.java.
 * 
 *
 */
public class ALPPipelineGraphBuilder {


	@Test
	public void build() throws IOException {

		ALPGraphBuilder.buildGraphs();

	}


}
