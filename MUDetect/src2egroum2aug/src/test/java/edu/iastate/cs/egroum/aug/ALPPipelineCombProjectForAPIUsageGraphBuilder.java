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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import school.first_author.EnhancedAUG;
import school.first_author.GraphBuildingUtils;
import school.first_author.LiteralsUtils;
import school.first_author.SubgraphMiningFormatter;

/**
 * Used for Experiment P.
 * @author first_author
 *
 */
public class ALPPipelineCombProjectForAPIUsageGraphBuilder {

	@Test
	public void run1() throws IOException {
		List<String> projects = Arrays.asList(
//				"/Users/first_author/repos/MUBench/mubench-checkouts/closure/319/",
//				"/Users/first_author/repos/MUBench/mubench-checkouts/itext/5091/",
//				"/Users/first_author/repos/MUBench/mubench-checkouts/lucene/1918/",
				"/Users/first_author/repos/MUBench/mubench-checkouts/chensun/cf23b99/"
				);

		run(projects);
	}
	
	
	
//	@Test
	public void run3() throws IOException {
		List<String> projects = Arrays.asList(
		"/Users/first_author/repos/MUBench/mubench-checkouts/bcel/24014e5/",
		"/Users/first_author/repos/MUBench/mubench-checkouts/jigsaw/205/",
		"/Users/first_author/repos/MUBench/mubench-checkouts/testng/677302c/");
		
		run(projects);
	}
	
	
	
	public static void run(List<String> projects) throws IOException {
		List<String> APIs = Arrays.asList(
//				"java.io.ObjectOutputStream__writeObject__1", "java.lang.Long__parseLong__1",
//				"java.util.Map__get__1", 
				"java.util.List__get__1", 
//				"java.util.StringTokenizer__nextToken__0",
//				"javax.crypto.Cipher__init__2", "java.io.DataOutputStream__<init>__1",
//				"java.sql.PreparedStatement__execute*__0", 
				"java.util.Iterator__next__0",
//				"org.jfree.data.statistics.StatisticalCategoryDataset__getMeanValue__2", "java.util.Scanner__next__0",
//				"com.itextpdf.text.pdf.PdfArray__getPdfObject__1", "java.sql.ResultSet__next__0",
//				"org.apache.lucene.index.SegmentInfos__info__1", "java.lang.Byte__parseByte__1",
//				"java.lang.Short__parseShort__1", "java.util.Enumeration__nextElement__0",
//				"org.jfree.chart.plot.XYPlot__getRendererForDataset__1",
//				"org.jfree.chart.plot.PlotRenderingInfo__getOwner__0",
//				"org.jfree.chart.plot.CategoryPlot__getDataset__1",
//				"com.itextpdf.text.pdf.PdfDictionary__getAsString__1", "java.nio.ByteBuffer__put__1",
//				"java.util.SortedMap__firstKey__0", "org.kohsuke.args4j.spi.Parameters__getParameter__1",
//				"java.nio.channels.FileChannel__write__1", 
				"java.io.PrintWriter__write__1"
//				"javax.swing.JFrame__setVisible__1", "java.util.Optional__get__0"
				);

		
		for (String project : projects) {
			Map<String, BufferedWriter> writers = new HashMap<>();
			Map<String, BufferedWriter> idMappingWriters = new HashMap<>();
			for (String API : APIs) {
				String[] splittedPath = project.split("/");
				String projectName = splittedPath[splittedPath.length - 2];
//				System.out.println(projectName);

				String outputDirectory = "/Users/first_author/repos/MUDetect/src2egroum2aug/output/" + projectName
						+ "___" + API + "/";
				new File(outputDirectory).mkdirs();

				BufferedWriter writer = new BufferedWriter(
						new FileWriter(outputDirectory + API + "_combing_test_formatted.txt"));
				BufferedWriter idMappingWriter = new BufferedWriter(
						new FileWriter(outputDirectory + API + "_combing_test_graph_id_mapping.txt"));

				writers.put(API, writer);
				idMappingWriters.put(API, idMappingWriter);
			}

			buildGraphs(APIs, project, writers, idMappingWriters);

			fileContents.clear();
			edu.iastate.cs.egroum.utils.JavaASTUtil.astNodeCache.clear();

			for (String API : APIs) {
				writers.get(API).flush();
				writers.get(API).close();
				idMappingWriters.get(API).flush();
				idMappingWriters.get(API).close();
			}

		}

	}

	static Map<File, String> fileContents = new HashMap<>();

	public static void buildGraphs(List<String> APIs, String pathToProject, Map<String, BufferedWriter> writers,
			Map<String, BufferedWriter> idMappingWriters) throws IOException {
		String pathToProjectFiles = pathToProject + "/checkout/";
		String pathToClassPath = pathToProject + "dependencies/";

		Map<String, Integer> APICount = new HashMap<>();
		for (String API : APIs) {
			APICount.put(API, 0);
		}

		Collection<File> pathsToJavaFiles = FileUtils.listFiles(new File(pathToProjectFiles), new String[] { "java" },
				true);

		int fileIndex = 0;
		int lastFileIndex = pathsToJavaFiles.size();
		for (File javaFile : pathsToJavaFiles) {
			System.out.println("\t\t" + fileIndex++ + "/ " + lastFileIndex);
			String pathToJavaFile = javaFile.toString();
			String code;
			if (!fileContents.containsKey(javaFile)) {
				code = new String(Files.readAllBytes(new File(pathToJavaFile).toPath()));
			} else {
				code = fileContents.get(javaFile);
			}

			// get classpath stuff first
			String[] classpath = null;
			List<String> result = new ArrayList<>();
			if (pathToClassPath != null) {
				try (Stream<Path> walk = Files.walk(Paths.get(pathToClassPath))) {

					result = walk.filter(Files::isRegularFile).map(x -> x.toString())
							.filter(name -> name.endsWith("jar")).collect(Collectors.toList());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String[] splittedPath = pathToProject.split("/");
			String projectName = splittedPath[splittedPath.length - 2];

			for (String API : APIs) {
				System.out.println("writing to " + projectName + "___" + API + "_combing_test_formatted.txt");

				String additionalJar = ExtendedAUGTypeUsageExamplePredicate.additionalJar.get(API);
				if (additionalJar != null) {
					result.add(additionalJar);
				}
				classpath = result.toArray(new String[] {});

				// use text to perform early return.
				String[] APIsplitted = API.split("__");
				String textToFind = APIsplitted[1]; // method name
				String[] nameSplitted = APIsplitted[0].split("\\."); 
				String textToFind2 = nameSplitted[nameSplitted.length - 1]; // class name
				String packageName = APIsplitted[0].substring(0, APIsplitted[0].lastIndexOf("."));
				if (!code.contains(textToFind)) {
					continue;
				}
				if (!packageName.contains("java") || !packageName.contains("java") ) {
					if (!code.contains(textToFind2)) {
						continue;
					}
				}

				Collection<EnhancedAUG> eaugs = buildAUGsForClassFromSomewhereElse(code, pathToJavaFile,
						pathToJavaFile.substring(pathToJavaFile.lastIndexOf("/")), new AUGConfiguration() {
							{
								usageExamplePredicate = EAUGUsageExamplesOf(GraphBuildingUtils.APIToMethodName.get(API),
										GraphBuildingUtils.APIToClass.get(API));
								maxStatements = 500;
							}
						}, classpath);

				if (!eaugs.isEmpty()) {
					System.out.println("\tFinished building some eaugs!");
				} else {
					System.out.println("\tNo eaug found.");
				}
				for (EnhancedAUG eaug : eaugs) {
					System.out.println("\t\tFound " + eaug.aug.name);
				}

				String directoryToOriginalLabels = "/Users/first_author/repos/MUDetect/src2egroum2aug/output/" + API
						+ "/";

				// read vertmap
				Map<String, Integer> map1 = new HashMap<>();
				String vertMapDirectory = directoryToOriginalLabels + API + "_vertmap.txt";
				List<String> lines = Files.readAllLines(Paths.get(vertMapDirectory));
				for (String line : lines) {
					int lastIndex = line.lastIndexOf(",");
					String token = line.substring(0, lastIndex);
					String countAsStr = line.substring(lastIndex + 1);
					map1.put(token, Integer.parseInt(countAsStr));
				}

				Map<String, Integer> map2 = new HashMap<>();
				String edgeMapDirectory = directoryToOriginalLabels + API + "_edgemap.txt";
				lines = Files.readAllLines(Paths.get(edgeMapDirectory));
				for (String line : lines) {
					String[] splitted = line.split(",");
					map2.put(splitted[0], Integer.parseInt(splitted[1]));
				}

				System.out.println("\tDone with one file");

				String fileId = pathToJavaFile;
				Map<String, String> labels = new HashMap<>();
				for (EnhancedAUG eaug : eaugs) {
					String labelId = fileId + " - " + eaug.aug.name;
					labels.put(labelId, "U");
				}
				//
				LiteralsUtils.isTestTime = true;
				int newCount = SubgraphMiningFormatter.convert(eaugs, EnhancedAUG.class, APICount.get(API), map1, map2, fileId, labels, 1, "",
						writers.get(API), idMappingWriters.get(API));

				APICount.put(API, newCount);
			}
		}

		System.out.println("Now it is truly done!");
	}
}
