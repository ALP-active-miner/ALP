package edu.iastate.cs.egroum.aug;

import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import edu.iastate.cs.egroum.dot.DotGraph;
import school.first_author.EnhancedAUG;
import school.first_author.LiteralsUtils;
import school.first_author.SubgraphMiningFormatter;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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

public class Debug3 {
    @Test
    public void debug() {
//        String code = "interface CI {\n" + 
//        		"\n" + 
//        		"	void get(InputStream is) throws IOException;\n" + 
//        		"	\n" + 
//        		"}\n" + 
//        		"\n" + 
//        		"class C implements CI {\n" + 
//        		"   private final String HMM = \"1\";\n" + 
//        		"//	public List<String> hellos = Arrays.asList(\"1\",\"2\",\"3\");\n" + 
//        		"//	\n" + 
//        		"	public C() {\n" + 
//        		"//		hellos = Arrays.asList(\"5\",\"6\",\"7\");\n" + 
//        		"	}\n" + 
//        		"	\n" + 
//        		"	@Override\n" + 
//        		"    public void get(java.io.InputStream is) throws IOException {\n" + 
//        		"		java.io.BufferedReader reader = null;\n" + 
//        		"        try {\n" + 
//        		"        	reader = new java.io.BufferedReader(new java.io.FileReader(HMM));\n" + 
//        		"//        	if (hellos.isEmpty()) return;\n" + 
//        		"            is.read();\n" + 
//        		"            int i = 0;\n" + 
//        		"            System.out.println(i);\n" + 
//        		"        } catch (IOException e) {\n" + 
//        		"            // ignore\n" + 
//        		"        	return;\n" + 
//        		"        } finally {\n" + 
//        		"        	reader.close();\n" + 
//        		"        }\n" + 
//        		"        is.close();\n" + 
//        		"    }\n" + 
//        		"}";
//    	String code ="public class A {\n" + 
//    			"	\n" + 
//    			"	String x = \"1\";\n" + 
//    			"\n" + 
//    			"	public long lol() {x=getString();x=getString1();return java.lang.Long.parseLong(x);\n" + 
//    			"\n" + 
//    			"	}\n" + 
//    			"\n" + 
//    			"}";
//    	String code = "" + 
//    			"import java.io.File;\n" + 
//    			"import java.io.PrintWriter;\n" + 
//    			"\n" + 
//    			"public class Scratch {\n" + 
//    			"\n" + 
//    			"	public static void main() {\n" + 
//    			"		File f = new File(\"hello_world\"); \n" + 
//    			"		try (PrintWriter pw = new PrintWriter(f)) {\n" + 
//    			"			\n" + 
//    			"			int i = hello();\n" + 
//    			"		} catch(Exception e) {\n" + 
//    			"			\n" + 
//    			"			\n" + 
//    			"		} finally {\n" + 
//    			"			System.out.println(\"hello world\");\n" + 
//    			"		}\n" + 
//    			"			\n" + 
//    			"	}\n" + 
//    			"	\n" + 
//    			"}";

    	String code ="V existing = delegate.get(key);\n" + 
    			"if(existing != null) {\n" + 
    			"    return existing;\n" + 
    			"}";
        LiteralsUtils.isTestTime = true;
        ArrayList<APIUsageExample> augs = buildAUGsForClasses(new String[]{code});
        exportAUGsAsPNG(augs, "./output/", "Debug-aug");
        
        
        Set<EnhancedAUG> enhanced = EnhancedAUG.buildEnhancedAugs(new HashSet<>(augs));
        Map<String, Integer> map1 = new HashMap<>();
		Map<String, Integer> map2 = new HashMap<>();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("./output/" + "debug" + "_formatted.txt"));
				BufferedWriter mappingwriter = new BufferedWriter(new FileWriter("./output/" + "debug" + "_mapping.txt"))) {
			Map<String, String> labels = new HashMap<>();
			for (EnhancedAUG eaug : enhanced) {
				String labelId = "id-" + " - " + eaug.aug.name;
				labels.put(labelId, "U");
			}
			
			SubgraphMiningFormatter.convert(enhanced, EnhancedAUG.class, 0, map1, map2, "id-", labels, 1, "", writer, mappingwriter);
			
			
			
			System.out.println("will write to \"./output/\" + API + \"_vertmap.txt\""+ " = ./output/" + "debug" +  "_vertmap.txt");
			try (BufferedWriter writerVertex = new BufferedWriter(new FileWriter("./output/" + "debug" +  "_vertmap.txt"))) {
				for (Entry<String, Integer> entry1 : map1.entrySet()) {
					writerVertex.write(entry1.getKey() + "," + entry1.getValue() + "\n");
				}
			}
			System.out.println("will write to \"./output/\" + API + \"_edgemap.txt\"");
			try (BufferedWriter writerEdge = new BufferedWriter(new FileWriter("./output/" + "debug" +  "_edgemap.txt"))) {
				for (Entry<String, Integer> entry1 : map2.entrySet()) {
					writerEdge.write(entry1.getKey() + "," + entry1.getValue() + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

        Collection<EGroumGraph> egroums = buildEGroumsForClasses(new String[] {code});
        exportEGroumsAsPNG(egroums, "./output", "Debug-egroum");
    }

    private Collection<EGroumGraph> buildEGroumsForClasses(String[] sources) {
        return Arrays.stream(sources)
                .flatMap(source -> buildEGroumsForClass(source).stream())
                .collect(Collectors.toList());
    }

    private ArrayList<EGroumGraph> buildEGroumsForClass(String source) {
        String projectName = "test";
        String basePath = AUGBuilderTestUtils.class.getResource("/").getFile() + projectName;
        return new EGroumBuilder(new AUGConfiguration()).buildGroums(source, basePath, projectName, null);
    }

    private void exportEGroumsAsPNG(Collection<EGroumGraph> egroums, String pathname, String name) {
        Iterator<EGroumGraph> it = egroums.iterator();
        for (int i = 0; it.hasNext(); i++) {
            EGroumGraph egroum = it.next();
            new DotGraph(egroum).toPNG(new File(pathname), name + "-" + i);
        }
    }
}
