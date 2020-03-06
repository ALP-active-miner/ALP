package school.first_author;

import java.util.HashMap;
import java.util.Map;

public class LiteralsUtils {
	
	public static boolean isTestTime = false;

	public static Map<String, Integer> counter = new HashMap<>();
	
	public static void increaseFreq(String literalString) {
		counter.putIfAbsent(literalString, 0);
		counter.put(literalString, counter.get(literalString) + 1);
		
	}
	
	public static int getFreq(String literalString) {
		if (!counter.containsKey(literalString)) {
			if (!isTestTime) {
				throw new RuntimeException("Odd. Found a literal string we did not see in the first pass");
			} else {
				return 0;
			}
		}
		return counter.containsKey(literalString) ? counter.get(literalString) : 0;
	}
}
