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
 * After running GSPAN, can use this class to figure out what exactly the subgraphs contan
 * @author first_author
 *
 */
public class ReconstructSubgraphWithLabels {

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

		Map<Integer, Float> bestSubgraphs = new HashMap<>();

//		String APIunderTest = "java.sql.PreparedStatement__execute*__0";
		
//		String APIunderTest = "java.util.Iterator__next__0";
//		String APIunderTest = "javax.crypto.Cipher__init__2";
//		String APIunderTest = "java.io.ObjectOutputStream__writeObject__1";
//		String APIunderTest = "java.io.DataOutputStream__<init>__1";
//		String APIunderTest = "java.util.List__get__1";
//		String APIunderTest = "java.lang.Long__parseLong__1";
//		String APIunderTest = "java.lang.Short__parseShort__1";
//		String APIunderTest = "org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2";
//		String APIunderTest = "java.util.Scanner__next__0";	
//		String APIunderTest = "com.itextpdf.text.pdf.PdfArray__getPdfObject__1";
//		String APIunderTest = "java.sql.ResultSet__next__0";
//		String APIunderTest = "java.util.StringTokenizer__nextToken__0";
//		String APIunderTest = "org.apache.lucene.index.SegmentInfos__info__1";
//		String APIunderTest = "java.lang.Byte__parseByte__1";
//		String APIunderTest = "java.util.Map__get__1";
		String APIunderTest = "java.util.Enumeration__nextElement__0";
//		String APIunderTest = "org.jfree.chart.plot.XYPlot__getRendererForDataset__1";
//		String APIunderTest = "org.jfree.chart.plot.PlotRenderingInfo__getOwner__0";
//		String APIunderTest = "com.itextpdf.text.pdf.PdfDictionary__getAsString__1";
//		String APIunderTest = "org.jfree.chart.plot.CategoryPlot__getDataset__1";
//		String APIunderTest = "java.util.SortedMap__firstKey__0";
//		String APIunderTest = "java.nio.ByteBuffer__put__1";
//		String APIunderTest = "org.kohsuke.args4j.spi.Parameters__getParameter__1";
//		String APIunderTest = "java.nio.channels.FileChannel__write__1";
//		String APIunderTest = "java.io.PrintWriter__write__1";
//		String APIunderTest = "javax.swing.JFrame__setVisible__1";
//		String APIunderTest = "java.util.Optional__get__0";
		
		
		if (APIunderTest.endsWith("true")) {
			throw new RuntimeException("accidentally ended API's name with true");
		}
		
		Path path = guessPathToBestSubgraphFile(APIunderTest);
		
		Files.lines(path).map(s -> s.trim())
				.filter(s -> !s.isEmpty()).forEach(line -> {
					String[] splitted = line.split(",");
					
					bestSubgraphs.put(Integer.parseInt(splitted[0]), Float.parseFloat(splitted[1]));
				});
		List<Integer> sorted = bestSubgraphs.entrySet().stream()
		   .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		   .map(Entry::getKey)
		   .collect(Collectors.toList());

		convert(pathToVertMapFile(APIunderTest),
				pathToEdgeMapFile(APIunderTest),
				pathToFrequentSubgraphFile(APIunderTest),
				sorted);
		
//		System.out.println(sorted);
//		convert("ByteOutputStream",
//				"/Users/first_author/repos/MUDetect/src2egroum2aug/output/java.io.ByteArrayOutputStream__toByteArray__0_vertmap.txt",
//				"/Users/first_author/repos/MUDetect/src2egroum2aug/output/java.io.ByteArrayOutputStream__toByteArray__0_edgemap.txt",
//				"/Users/first_author/repos/MUDetect/src2egroum2aug/output/java.io.ByteArrayOutputStream__toByteArray__0_formatted.txt_result",
//				bestSubgraphs);


	}



	private void convert(String vertmapPath, String edgeMapPath, String graphPath, List<Integer> onlyShow)
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
				System.out.println(content.get(item).toString());
			}
		}
	}

}
