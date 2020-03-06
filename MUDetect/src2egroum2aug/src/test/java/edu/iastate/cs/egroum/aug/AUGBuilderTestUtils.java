package edu.iastate.cs.egroum.aug;

import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import school.first_author.EnhancedAUG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AUGBuilderTestUtils {
    /**
     * Use {@link #buildAUGForMethod(String)} instead.
     */
    @Deprecated
    public static APIUsageExample buildAUG(String code) {
        return buildAUGForMethod(code);
    }

    /**
     * Use {@link #buildAUGForMethod(String, AUGConfiguration)} instead.
     */
    @Deprecated
    public static APIUsageExample buildAUG(String code, AUGConfiguration configuration) {
        return buildAUGForMethod(code, configuration);
    }

    public static APIUsageExample buildAUGForMethod(String code) {
        return buildAUGForMethod(code, new AUGConfiguration());
    }

    public static APIUsageExample buildAUGForMethod(String code, AUGConfiguration configuration) {
        String classCode = "class C { " + code + "}";
        Collection<APIUsageExample> groums = buildAUGsForClass(classCode, configuration);
        assertThat(groums.size(), is(1));
        return groums.iterator().next();
    }

    public static Collection<APIUsageExample> buildAUGsForClass(String classCode) {
        return buildAUGsForClass(classCode, new AUGConfiguration());
    }
    
    public static Collection<EnhancedAUG> buildAUGsForClassFromSomewhereElse(String classCode, String elseWhere, String project, AUGConfiguration configuration) {
        AUGBuilder builder = new AUGBuilder(configuration);
        String basePath = elseWhere;
        Collection<APIUsageExample> aug = builder.build(classCode, basePath, project, null);
        return EnhancedAUG.buildEnhancedAugs(new HashSet<>(aug));
    }
    
    public static Collection<EnhancedAUG> buildAUGsForClassFromSomewhereElse(String classCode, String elseWhere, String project, AUGConfiguration configuration, String[] classpath) {
        AUGBuilder builder = new AUGBuilder(configuration);
        String basePath = elseWhere;
        Collection<APIUsageExample> aug = builder.build(classCode, basePath, project, classpath);
        return EnhancedAUG.buildEnhancedAugs(new HashSet<>(aug));
    }

    private static Collection<APIUsageExample> buildAUGsForClass(String classCode, AUGConfiguration configuration) {
        AUGBuilder builder = new AUGBuilder(configuration);
        String projectName = "test";
        String basePath = getTestFilePath("/") + projectName;
        return builder.build(classCode, basePath, projectName, null);
    }

    public static Collection<APIUsageExample> buildAUGsFromFile(String path) {
        return buildAUGsFromFile(path, new AUGConfiguration() {{
            removeImplementationCode = 2;
        }});
    }

    static Collection<APIUsageExample> buildAUGsFromFile(String path, AUGConfiguration configuration) {
        return new AUGBuilder(configuration).build(getTestFilePath(path), null);
    }

    public static ArrayList<APIUsageExample> buildAUGsForClasses(String[] sourceCodes) {
        ArrayList<APIUsageExample> groums = new ArrayList<>();
        for (String sourceCode : sourceCodes) {
            groums.addAll(buildAUGsForClass(sourceCode));
        }
        return groums;
    }

    public static ArrayList<APIUsageExample> buildAUGsForMethods(String... sourceCodes) {
        ArrayList<APIUsageExample> groums = new ArrayList<>();
        for (String sourceCode : sourceCodes) {
            groums.add(buildAUGForMethod(sourceCode));
        }
        return groums;
    }

    private static String getTestFilePath(String relativePath) {
        if (!relativePath.startsWith("/")) {
            relativePath = "/" + relativePath;
        }
        return AUGBuilderTestUtils.class.getResource(relativePath).getFile();
    }
}
