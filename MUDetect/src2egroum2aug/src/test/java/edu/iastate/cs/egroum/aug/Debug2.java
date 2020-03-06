package edu.iastate.cs.egroum.aug;

import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import edu.iastate.cs.egroum.dot.DotGraph;
import school.first_author.LiteralsUtils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

import static de.tu_darmstadt.stg.mudetect.aug.AUGTestUtils.exportAUGsAsPNG;
import static edu.iastate.cs.egroum.aug.AUGBuilderTestUtils.buildAUGsForClasses;

public class Debug2 {
    @Test
    public void debug() {
//        String code = "import java.util.ArrayList;\n" + 
//        		"import java.util.HashMap;\n" + 
//        		"import java.util.List;\n" + 
//        		"import java.util.Map;\n" + 
//        		"\n" + 
//        		"public class Test implements java.util.Map {\n" + 
//        		"	public static Long main(Long ll) {\n" + 
//        		"		Map<String, Integer> map = new HashMap<>();\n" + 
//        		"		map.entrySet().iterator().next();\n" + 
//        		"		map.entrySet().iterator().next();\n" + 
//        		"		\n" + 
//        		"		List<String> hellos = new ArrayList<>();\n" + 
//        		"		List<String> hellos1 = new ArrayList<>();\n" + 
//        		"		hellos.add(\"hihi\");\n" + 
//        		"		for (int i = 0; i < hellos.size(); i++) {\n" + 
//        		"			hellos1.add(\"hihi\" + ll);\n" + 
//        		"		}\n" + 
//        		"		return ll;\n" +
//        		"	}\n" + 
//        		"\n" + 
//        		"}\n" + 
//        		"";

    String code =
    		"import java.util.Collection;\n" + 
    		"import java.util.Collections;\n" + 
    		"import java.util.HashMap;\n" + 
    		"import java.util.Map;\n" + 
    		"import java.util.Set;\n" + 
    		"import java.util.concurrent.ConcurrentMap;\n" + 
    		"public class CopyOnWriteMap<K,V> implements ConcurrentMap<K, V> {\n" + 
    		"	private volatile Map<K, V> delegate = Collections.emptyMap();\n" + 
    		"\n" + 
    		"\n" + 
    		"	@Override\n" + 
    		"    public synchronized V putIfAbsent(K key, V value) {\n" + 
    		"        final Map<K, V> delegate = this.delegate;\n" + 
    		"        V existing = delegate.get(key);\n" + 
    		"        if(existing != null) {\n" + 
    		"            return existing;\n" + 
    		"        }\n" + 
    		"        putInternal(key, value);\n" + 
    		"        return null;\n" + 
    		"    }\n" + 
    		"    //must be called under lock\n" + 
    		"    private V putInternal(final K key, final V value) {\n" + 
    		"        final Map<K, V> delegate = new HashMap<>(this.delegate);\n" + 
    		"        V existing = delegate.put(key, value);\n" + 
    		"        this.delegate = delegate;\n" + 
    		"        return existing;\n" + 
    		"    }\n" + 
    		"}";
        ArrayList<APIUsageExample> augs = buildAUGsForClasses(new String[]{code, code});
        LiteralsUtils.isTestTime=true;
        exportAUGsAsPNG(augs, "./output/", "Debug-aug");

        Collection<EGroumGraph> egroums = buildEGroumsForClasses(new String[] {code, code});
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

