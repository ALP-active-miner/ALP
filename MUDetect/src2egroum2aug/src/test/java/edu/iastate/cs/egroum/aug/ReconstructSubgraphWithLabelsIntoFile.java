package edu.iastate.cs.egroum.aug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
 * After running GSPAN, can use this class to figure out what exactly the subgraphs contan
 * @author first_author
 *
 */
public class ReconstructSubgraphWithLabelsIntoFile {

	private Path guessPathToBestSubgraphFile(String API) {
		String baseDir = "/Users/first_author/repos/MUDetect/src2egroum2aug/output/" + API +"/";
		String fileName = API + "_formatted_result_best_subgraphs.txt";
		
		return Paths.get(baseDir + fileName);
	}
	
	private String pathToVertMapFile(String API) {
		String baseDir = "/Users/first_author/repos/MUDetect/src2egroum2aug/output/" + API +"/";
		String fileName = API + "_vertmap.txt";
		
		return baseDir + fileName;
	}
	
	private String pathToEdgeMapFile(String API) {
		String baseDir = "/Users/first_author/repos/MUDetect/src2egroum2aug/output/" + API +"/";
		String fileName = API + "_edgemap.txt";
		
		return baseDir + fileName;
	}
	
	private String pathToFrequentSubgraphFile(String API) {
		String baseDir = "/Users/first_author/repos/MUDetect/src2egroum2aug/output/" + API +"/";
		String fileName = API + "_formatted_result";
		
		return baseDir + fileName;
	}
	
	/**
	 * For testing the ability to reconstruct the best subgraphs 
	 * @throws IOException
	 */
	@Test
	public void debug2() throws IOException {


		List<String> APIsUnderTest = Arrays.asList(
				"java.sql.PreparedStatement__execute*__0", 
				"java.util.Iterator__next__0", 
				"javax.crypto.Cipher__init__2", 
				"java.io.ObjectOutputStream__writeObject__1", 
				"java.io.DataOutputStream__<init>__1", 
				"java.util.List__get__1", 
				"java.lang.Long__parseLong__1", 
				"java.lang.Short__parseShort__1", 
				"org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2", 
				"java.util.Scanner__next__0", 
				"com.itextpdf.text.pdf.PdfArray__getPdfObject__1", 
				"java.sql.ResultSet__next__0", 
				"java.util.StringTokenizer__nextToken__0", 
				"org.apache.lucene.index.SegmentInfos__info__1", 
				"java.lang.Byte__parseByte__1", 
				"java.util.Map__get__1", 
				"java.util.Enumeration__nextElement__0", 
				"org.jfree.chart.plot.XYPlot__getRendererForDataset__1", 
				"org.jfree.chart.plot.PlotRenderingInfo__getOwner__0", 
				"com.itextpdf.text.pdf.PdfDictionary__getAsString__1", 
				"org.jfree.chart.plot.CategoryPlot__getDataset__1", 
				"java.util.SortedMap__firstKey__0", 
				"java.nio.ByteBuffer__put__1", 
				"org.kohsuke.args4j.spi.Parameters__getParameter__1", 
				"java.nio.channels.FileChannel__write__1", 
				"java.io.PrintWriter__write__1", 
				"javax.swing.JFrame__setVisible__1", 
				"java.util.Optional__get__0",
				"java.lang.String__charAt__1",
				"java.awt.Shape__getPathIterator__1");
		// unfortunately, no output for "org.jfree.chart.plot.XYPlot__getRendererForDataset__1, javax.crypto.spec.SecretKeySpec", "org.apache.commons.lang.text.StrBuilder__getNullText__0", among others.
		
		APIsUnderTest.stream().forEach(APIunderTest -> {
		
			Map<Integer, Float> bestSubgraphs = new HashMap<>();
			System.out.println("APIUnder Test=" + APIunderTest);
			if (APIunderTest.endsWith("true")) {
				throw new RuntimeException("accidentally ended API's name with true");
			}
			
			Path path = guessPathToBestSubgraphFile(APIunderTest);
			
			try {
				Files.lines(path).map(s -> s.trim())
						.filter(s -> !s.isEmpty()).forEach(line -> {
							String[] splitted = line.split(",");
							
							bestSubgraphs.put(Integer.parseInt(splitted[0]), Float.parseFloat(splitted[1]));
						});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			List<Integer> sorted = bestSubgraphs.entrySet().stream()
			   .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
			   .map(Entry::getKey)
			   .collect(Collectors.toList());
	
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(APIunderTest + "_subgraph_patterns.txt"))) {
				convert(pathToVertMapFile(APIunderTest),
						pathToEdgeMapFile(APIunderTest),
						pathToFrequentSubgraphFile(APIunderTest),
						sorted,
						writer);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});


	}



	private void convert(String vertmapPath, String edgeMapPath, String graphPath, List<Integer> onlyShow, BufferedWriter writer)
			throws IOException, FileNotFoundException {
		Map<Integer, String> vertexMap = new HashMap<>();
		int line = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(vertmapPath))) {
			String st;
			while ((st = reader.readLine()) != null) {
				int splitAt = st.lastIndexOf(",");

				try {
					vertexMap.put(Integer.parseInt(st.substring(splitAt + 1)), st.substring(0, splitAt ));
				} catch (NumberFormatException nfe) {
					System.out.println("weird input format. string=" + st + " at line " + line);
					throw new RuntimeException(nfe);
					
				}
				line++;
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
			boolean alwaysShow = onlyShow.isEmpty();
			while ((st = reader.readLine()) != null) {
				
				if (st.startsWith("t")) {
					String[] splitted = st.split(" ");
					currentId = Integer.parseInt(splitted[2]);
					
					if (alwaysShow || onlyShow.contains(currentId)) {
//						System.out.println(st);
						content.putIfAbsent(currentId, new StringBuilder());
						
						content.get(currentId).append(st + "\n");
					}

					
				} else if (st.startsWith("v")) {
					String[] splitted = st.split(" ");
					if (alwaysShow || onlyShow.contains(currentId)) {
//						System.out.println("v " + splitted[1] + " " + vertexMap.get(Integer.parseInt(splitted[2])));
						content.get(currentId).append("v " + splitted[1] + " " + vertexMap.get(Integer.parseInt(splitted[2])) + "\n");
					}

				} else if (st.startsWith("e")) {
					String[] splitted = st.split(" ");

					if (alwaysShow || onlyShow.contains(currentId)) {
//						System.out.println(
//							"e " + splitted[1] + " " + splitted[2] + " " + edgeMap.get(Integer.parseInt(splitted[3])));
						content.get(currentId).append(
								"e " + splitted[1] + " " + splitted[2] + " " + edgeMap.get(Integer.parseInt(splitted[3]))+ "\n");
					}
				} else {
					throw new RuntimeException("what just happened? no other line type was expected " + st );
				}
			}
			
			for (Integer item : onlyShow) {
				System.out.println(content.get(item));
				if (content.get(item) == null ) {
					continue;
				}
				writer.write(content.get(item) == null ? "-" : content.get(item).toString());
				writer.newLine();
			}
		}
	}

}
