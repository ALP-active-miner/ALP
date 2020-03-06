package edu.iastate.cs.egroum.aug;

import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import edu.iastate.cs.egroum.dot.DotGraph;
import edu.iastate.cs.egroum.utils.JavaASTUtil;
import school.first_author.EnhancedAUG;
import school.first_author.GraphBuildingUtils;
import school.first_author.LiteralsUtils;
import school.first_author.SubgraphMiningFormatter;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static de.tu_darmstadt.stg.mudetect.aug.AUGTestUtils.exportAUGsAsPNG;
import static edu.iastate.cs.egroum.aug.AUGBuilderTestUtils.buildAUGsForClasses;
import static edu.iastate.cs.egroum.aug.ExtendedAUGTypeUsageExamplePredicate.EAUGUsageExamplesOf;

public class DebugFindUsageExamples {
	@Test
	public void debug() throws IOException {
		String code = new String(Files.readAllBytes(path()));

		String filePath = path().toFile().toString();
		CompilationUnit cu = (CompilationUnit) JavaASTUtil.parseSource(code, filePath,
				filePath.substring(filePath.lastIndexOf("/")), null);

		for (int i = 0; i < cu.types().size(); i++)
			if (cu.types().get(i) instanceof TypeDeclaration) {
				TypeDeclaration typ = (TypeDeclaration) cu.types().get(i);

				for (MethodDeclaration md : typ.getMethods()) {
					if (EAUGUsageExamplesOf("toByteArray",
							GraphBuildingUtils.APIToClass.get("java.io.ByteArrayOutputStream__toByteArray__0"))
									.matches(md)) {

						String sig = JavaASTUtil.buildSignature(md);
						System.out.println("matched - " + typ.getName().getIdentifier() + "." + sig);
					}
				}
			}

		LiteralsUtils.isTestTime = true;

		ArrayList<APIUsageExample> augs = buildAUGsForClasses(new String[] { code });

	}

	private Path path() {
		return Paths.get(
				"/Users/first_author/Downloads/github-code-search/java.io.ByteArrayOutputStream__toByteArray__0/755/com/ftkj/util/ByteUtil.java");
	}

}
