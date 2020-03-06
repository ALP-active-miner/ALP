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
 * @author first_author
 *
 */
public class ReconstructSubgraph {

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

		try (BufferedReader reader = new BufferedReader(new FileReader(graphPath))) {
			String st;
			int currentId = -1;
			boolean currentSubgraphHasWHatIWant = false;
			boolean currentSubgraphHasWHatIWant2 = false;
			
			int numVertexIWant = 2;
			
			int numVertex = 0;
			while ((st = reader.readLine()) != null) {
				
				if (st.startsWith("t")) {
			
					if (!currentSubgraphHasWHatIWant || !currentSubgraphHasWHatIWant2 || numVertex != numVertexIWant) {
						
						content.remove(currentId);
					}
					// reset
					currentSubgraphHasWHatIWant = false;
					currentSubgraphHasWHatIWant2 = false;
					numVertex = 0;
					
					String[] splitted = st.split(" ");
					currentId = Integer.parseInt(splitted[2]);
					
					content.putIfAbsent(currentId, new StringBuilder());
					
					content.get(currentId).append(st + "\n");

				} else if (st.startsWith("v")) {
					String[] splitted = st.split(" ");
					
					int nodeId = Integer.parseInt(splitted[2]);
					content.get(currentId).append("v " + splitted[1] + " " + vertexMap.get(nodeId) + "\n");
					
					if (nodeId == 41) {
//						System.out.println("HERE!" + currentId);
						currentSubgraphHasWHatIWant = true;
					}
					if (nodeId == 5) {
//						System.out.println("HERE!" + currentId);
						currentSubgraphHasWHatIWant2 = true;
					}
					
					numVertex += 1;
		
				} else if (st.startsWith("e")) {
					String[] splitted = st.split(" ");

					content.get(currentId).append(
							"e " + splitted[1] + " " + splitted[2] + " " + edgeMap.get(Integer.parseInt(splitted[3]))+ "\n");
				
				} else {
					throw new RuntimeException("what just happened? no other line type was expected " + st );
				}
			}
			
			
			for (Map.Entry<Integer, StringBuilder>  item : content.entrySet()) {
				System.out.println(item.getValue().toString());
			}
			
		
		}
	}

}
