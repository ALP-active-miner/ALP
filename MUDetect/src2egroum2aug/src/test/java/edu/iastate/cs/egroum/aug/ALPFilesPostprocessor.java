package edu.iastate.cs.egroum.aug;

import static edu.iastate.cs.egroum.aug.ExtendedAUGTypeUsageExamplePredicate.EAUGUsageExamplesOf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.internal.core.dom.NaiveASTFlattener;
import org.junit.Test;

import com.google.common.collect.Sets;

import edu.iastate.cs.egroum.utils.JavaASTUtil;
import school.first_author.GraphBuildingUtils;
import school.first_author.ALPConstants;

/**
 * After mining significant subgraphs, it is likely that there are more graphs
 * that contain useful information for us.
 * 
 * From the uncovered, unlabeled data, find an overlap of clones to label s.t.
 * we maximise the coverage
 * 
 * @author first_author
 *
 */
public class ALPFilesPostprocessor {

	static Map<String, Set<String>> pathsAndMethodToTokens = new HashMap<>();
	static Map<String, Integer> pathsAndMethodToCounts = new LinkedHashMap<>();
	static Map<String, List<Integer>> pathsAndMethodToGraphIds = new LinkedHashMap<>();

	@Test
	public void debug() throws IOException {

		postprocess();
	}

	public static void postprocess() throws IOException {
		
		for (String API : ALPConstants.APIUnderMiner) {
			Set<String> directories = ALPConstants.directoriesToExamplesOfAPI.get(API);

			List<String> graphIdsToRead = new ArrayList<>();
			List<String> allLines = Files.readAllLines(
					Paths.get("./output/" + API + "/" + API + "_formatted_result_interesting_unlabeled.txt"));
			for (String line : allLines) {
				graphIdsToRead.add(line);
			}

			Set<String> fileIdsToRead = new HashSet<>();
			allLines = Files.readAllLines(Paths.get("./output/" + API + "/" + API + "_graph_id_mapping.txt"));

			Map<String, List<Integer>> fileIdToGraphIds = new HashMap<>();
			for (String line : allLines) {
				String[] splitted = line.split(",");
				String graphId = splitted[1];

				if (!graphIdsToRead.contains(graphId)) {
					continue;
				}

				fileIdsToRead.add(splitted[0]);

				String labelId = splitted[0];

				fileIdToGraphIds.putIfAbsent(labelId, new ArrayList<>());
				fileIdToGraphIds.get(labelId).add(Integer.parseInt(graphId));
			}

			System.out.println("Selecting");
			for (String directory : directories) {
				try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
					paths.filter(Files::isRegularFile).forEach(path -> {
						if (!ALPGraphBuilder.isExpectedJavaSourceFileFromRightSubdirectory(path)) {
							return;
						}

						System.out.println("path is " + path);

						String after = path.toAbsolutePath().toString().substring(directory.length());
						String id = after.split("/")[0];

						String[] splitted = directory.split("/");
						String subIdentifier = splitted[splitted.length - 1];

						if (!fileIdsToRead.contains(subIdentifier + id) && !fileIdsToRead.contains(id)) {
							return;
						}
						id = subIdentifier + id;

						String code;
						try {
							code = new String(Files.readAllBytes(path));

							String filePath = path.toFile().toString();
							CompilationUnit cu = (CompilationUnit) JavaASTUtil.parseSource(code, filePath,
									filePath.substring(filePath.lastIndexOf("/")), null);

							for (int i = 0; i < cu.types().size(); i++) {
								if (cu.types().get(i) instanceof TypeDeclaration) {
									TypeDeclaration typ = (TypeDeclaration) cu.types().get(i);

									for (MethodDeclaration md : typ.getMethods()) {
										boolean hasMstachingMethodName = false;
										for (String methodName : GraphBuildingUtils.APIToMethodName.get(API)) {
											if (EAUGUsageExamplesOf(
													methodName,
													GraphBuildingUtils.APIToClass.get(API)).matches(md)) {
												hasMstachingMethodName = true;
											}											
										}
										if (!hasMstachingMethodName) continue;
										

										if (ALPFilesPreprocessor.isTooBig(md)) {
											continue;
										}

										boolean isClone = checkCloneOfPreviousAndDoStuff(filePath, md,
												fileIdToGraphIds.get(id));

										if (!isClone) {
										}
									}
								}
							}

						} catch (IOException e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						} catch (IllegalStateException ise) {
							ise.printStackTrace();
							System.out.println("Unable to parse java file");
							return;
						}

					});
				}
			}
			Optional<Integer> total = pathsAndMethodToCounts.values().stream()
					.reduce((accumulated, current) -> accumulated + current);
			if (!total.isPresent()) {
				throw new RuntimeException("Can't count?");
			}

			for (Entry<String, Integer> labelCandidates : pathsAndMethodToCounts.entrySet()) {
				Integer counts = labelCandidates.getValue();

				System.out.println("label this ->" + labelCandidates.getKey());
				System.out.println("\tIt covers " + counts + " / " + total.get());
				System.out.println("\t graph id might be ->" + pathsAndMethodToGraphIds.get(labelCandidates.getKey()));
			}

		}
	}

	private static boolean checkCloneOfPreviousAndDoStuff(String path, MethodDeclaration md, List<Integer> graphIds) {

		Set<String> tokens = new HashSet<>();

		NaiveASTFlattener printer = new NaiveASTFlattener();
		md.accept(printer);
		String body = printer.getResult();
		tokens = Sets.newHashSet(body.split(" "));
		tokens.remove(""); // if there's empty string, just remove it.

		String isCloneOf = null;

		for (Map.Entry<String, Set<String>> entry : pathsAndMethodToTokens.entrySet()) {
			Set<String> tokensInCloneType = entry.getValue();
			float ratio = intersectionRatio(tokensInCloneType, tokens);
			if (ratio > 0.8) { // is clone
				isCloneOf = entry.getKey();
			}
		}

		if (isCloneOf != null) {
			pathsAndMethodToCounts.put(isCloneOf, pathsAndMethodToCounts.get(isCloneOf) + 1);
			return true;
		} else {
			pathsAndMethodToCounts.put(path + "::" + md.getName(), 1);
			pathsAndMethodToTokens.put(path + "::" + md.getName(), tokens);
			pathsAndMethodToGraphIds.put(path + "::" + md.getName(), graphIds);
			return false;
		}
	}

	private static float intersectionRatio(Set<String> canonicalCopy, Set<String> tokens) {
		Set<String> intersection = new HashSet<>();
		intersection.addAll(canonicalCopy);
		intersection.retainAll(tokens);

		int numerator = intersection.size();
		float denominator = Math.min(canonicalCopy.size(), tokens.size());
		return numerator / denominator;
	}
}
