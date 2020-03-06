package edu.iastate.cs.egroum.aug;

import static edu.iastate.cs.egroum.aug.AUGBuilderTestUtils.buildAUGsForClassFromSomewhereElse;
import static edu.iastate.cs.egroum.aug.ExtendedAUGTypeUsageExamplePredicate.EAUGUsageExamplesOf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import school.first_author.EnhancedAUG;
import school.first_author.GraphBuildingUtils;
import school.first_author.LiteralsUtils;
import school.first_author.SubgraphMiningFormatter;

public class ALPPipelineTestDataGraphBuilder {

	@Test
	public void debug() throws IOException {

//		String API = "java.io.ObjectOutputStream__writeObject__1";
//		String API = "java.lang.Long__parseLong__1";
//		String API = "java.util.Map__get__1";
//		String API = "java.util.List__get__1";
//		String API = "java.sql.PreparedStatement__executeUpdate__0";
//		String API = "java.util.StringTokenizer__nextToken__0";
//		String API = "javax.crypto.Cipher__init__2";
//		String API = "java.io.DataOutputStream__<init>__1";
//		String API = "java.sql.PreparedStatement__execute*__0";

//		String API = "java.util.Iterator__next__0";
//		String API = "java.io.DataOutputStream__<init>__1";
		
//		String API = "org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2";
//		String API = "java.util.Scanner__next__0";
//		String API = "com.itextpdf.text.pdf.PdfArray__getPdfObject__1";
//		String API = "java.sql.ResultSet__next__0";
//		String API ="org.apache.lucene.index.SegmentInfos__info__1";
//		String API ="java.lang.Byte__parseByte__1";
//		String API ="java.lang.Short__parseShort__1";
		String API ="java.util.Enumeration__nextElement__0";
//		String API = "org.jfree.chart.plot.XYPlot__getRendererForDataset__1";
//		String API = "org.jfree.chart.plot.PlotRenderingInfo__getOwner__0";
		
//		String API = "org.jfree.chart.plot.CategoryPlot__getDataset__1";
		
//		String API = "com.itextpdf.text.pdf.PdfDictionary__getAsString__1";
//		String API = "java.nio.ByteBuffer__put__1";
//		String API =  "java.util.SortedMap__firstKey__0";
//		String API = "org.kohsuke.args4j.spi.Parameters__getParameter__1";
//		String API = "java.nio.channels.FileChannel__write__1";
//		String API = "java.io.PrintWriter__write__1";
//		String API = "javax.swing.JFrame__setVisible__1";
//		String API = "java.util.Optional__get__0";
		
		buildGraphs(API);

	}

	public static void buildGraphs(String API) {
		List<String> pathsToJavaFiles = ALPRegressionTestConstants.javaFilesForApi.get(API);
		List<String> pathsToClassPath = ALPRegressionTestConstants.javaClassPathForApi.get(API);
		
		if (pathsToJavaFiles.size() != pathsToClassPath.size()) {
			throw new RuntimeException("wrong size");
		}
		

		String directoryToLabels = "/Users/first_author/repos/MUDetect/src2egroum2aug/output/" + API + "/";
		
		System.out.println("writing graph IDS to ");
		System.out.println(directoryToLabels + API +"_test_graph_id_mapping.txt");
		
		int i = 0;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryToLabels + API + "_test_formatted.txt"));
				BufferedWriter idMappingWriter = new BufferedWriter(new FileWriter(directoryToLabels + API +"_test_graph_id_mapping.txt" ))) {
			
			Iterator<String> classPathIter = pathsToClassPath.iterator();
			for (String pathToJavaFile : pathsToJavaFiles) {
				System.out.println("writing to " + directoryToLabels + API + "_test_formatted.txt");
				String code = new String(Files.readAllBytes(new File(pathToJavaFile).toPath()));
	
				// get classpath stuff first
				String classpathDirectory = classPathIter.next();
				String[] classpath = null;
				if (classpathDirectory != null) {
					try (Stream<Path> walk = Files.walk(Paths.get(classpathDirectory))) {
	
						List<String> result = walk.filter(Files::isRegularFile)
								.map(x -> x.toString()).collect(Collectors.toList());
	
						classpath = result.toArray(new String[] {});
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				
				Collection<EnhancedAUG> eaugs = buildAUGsForClassFromSomewhereElse(code, pathToJavaFile,
						pathToJavaFile.substring(pathToJavaFile.lastIndexOf("/")),
						new AUGConfiguration() {
							{
								usageExamplePredicate = EAUGUsageExamplesOf(
										GraphBuildingUtils.APIToMethodName.get(API),
										GraphBuildingUtils.APIToClass.get(API));
								maxStatements = 500;
							}
						},
						classpath);
	
	
				System.out.println("\tFinished building some eaugs!");
				for (EnhancedAUG eaug : eaugs) {
					System.out.println("\t\tFound " + eaug.aug.name);
				}
				
				// read vertmap
				Map<String, Integer> map1 = new HashMap<>();
				String vertMapDirectory = directoryToLabels + API + "_vertmap.txt";
				List<String> lines = Files.readAllLines(Paths.get(vertMapDirectory));
				for (String line : lines) {
					int lastIndex = line.lastIndexOf(",");
					String token = line.substring(0, lastIndex);
					String countAsStr = line.substring(lastIndex + 1);
					map1.put(token, Integer.parseInt(countAsStr));
				}
					
				
				Map<String, Integer> map2 = new HashMap<>();
				String edgeMapDirectory = directoryToLabels + API + "_edgemap.txt";
				lines = Files.readAllLines(Paths.get(edgeMapDirectory));
				for (String line : lines) {
					String[] splitted = line.split(",");
					map2.put(splitted[0], Integer.parseInt(splitted[1]));
				}
				
				System.out.println("\tDone with one file");
	
				String fileId = "testFile";
				Map<String, String> labels = new HashMap<>();
				for (EnhancedAUG eaug : eaugs) {
					String labelId = fileId + " - " + eaug.aug.name;
					labels.put(labelId, "U");
				}
	//			
				LiteralsUtils.isTestTime = true;
				i = SubgraphMiningFormatter.convert(eaugs, EnhancedAUG.class, i, map1, map2, fileId, labels, 1,
						"" ,writer, idMappingWriter);
//				i += eaugs.size();

			}
		} catch (Exception e) {
			System.out.println("err!!");
			throw new RuntimeException(e);
		}
		System.out.println("Now it is truly done!");
	}
}
