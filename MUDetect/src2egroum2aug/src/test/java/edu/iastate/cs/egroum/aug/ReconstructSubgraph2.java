package edu.iastate.cs.egroum.aug;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * For misc debugging. Use ReconstructSubgraphWithLabels.java to see the discrimnative subgraphs
 * 
 * Given A -> B is contained in a subgraph, is there another sub graph with B -> A?
 * @author first_author
 *
 */
public class ReconstructSubgraph2 {

	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public void debug() throws IOException {

		convert("Map",
		"/Users/first_author/repos/MUDetect/src2egroum2aug/output/java.util.Map__get__1_vertmap.txt",
		"/Users/first_author/repos/MUDetect/src2egroum2aug/output/java.util.Map__get__1_edgemap.txt",
		"/Users/first_author/repos/MUDetect/src2egroum2aug/output/java.util.Map__get__1_formatted_result"
		);

	}



	private void convert(String API, String vertmapPath, String edgeMapPath, String graphPath)
			throws IOException, FileNotFoundException {
		Map<Integer, String> vertexMap = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(vertmapPath))) {
			String st;
			while ((st = reader.readLine()) != null) {
				int splitAt = st.lastIndexOf(",");

				vertexMap.put(Integer.parseInt(st.substring(splitAt + 1)), st.substring(0, splitAt ));
			}
		}

		Map<Integer, String> edgeMap = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(edgeMapPath))) {
			String st;
			while ((st = reader.readLine()) != null) {
				int splitAt = st.lastIndexOf(",");
				edgeMap.put(Integer.parseInt(st.substring(splitAt + 1)), st.substring(0, splitAt ));
			}
		}
		
		Map<Integer, StringBuilder> content = new HashMap<>();
		
		Map<Integer, Set<Integer>> edges = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(graphPath))) {
			String st;
			int currentId = -1;

			while ((st = reader.readLine()) != null) {
				
				if (st.startsWith("t")) {
			
				
					String[] splitted = st.split(" ");
					currentId = Integer.parseInt(splitted[2]);
					
				
				} else if (st.startsWith("v")) {
					String[] splitted = st.split(" ");
					
					int nodeId = Integer.parseInt(splitted[2]);
//					content.get(currentId).append("v " + splitted[1] + " " + vertexMap.get(nodeId) + "\n");
					edges.putIfAbsent(nodeId, new HashSet<>());
		
				} else if (st.startsWith("e")) {
					String[] splitted = st.split(" ");

//					content.get(currentId).append(
//							"e " + splitted[1] + " " + splitted[2] + " " + edgeMap.get(Integer.parseInt(splitted[3]))+ "\n");
				
					int nodeId1 = Integer.parseInt(splitted[1]);
					int nodeId2 = Integer.parseInt(splitted[2]);
					edges.get(nodeId1).add(nodeId2);
				} else {
					throw new RuntimeException("what just happened? no other line type was expected " + st );
				}
			}
			
			int count = 0;
			for (Integer key : edges.keySet()) {
				for (Integer linksTo : edges.get(key)) {
					boolean isContainedInOtherDirection = edges.get(linksTo).contains(key);
					if (isContainedInOtherDirection) {
						count += 1;
					}
				}
			}
			System.out.println(count);
			
		
		}
	}

}
