package school.first_author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageGraph;
import de.tu_darmstadt.stg.mudetect.aug.model.Node;

public class EnhancedAUG {

	public APIUsageGraph aug;
	
	Set<APIUsageGraph> related = new HashSet<>();
	Map<APIUsageGraph, Node> relatedJoinPoint = new HashMap<>();
	Set<String> interfaces = new HashSet<>();
	
	public EnhancedAUG(APIUsageGraph aug, Set<APIUsageGraph>  related, Map<APIUsageGraph, Node> relatedJoinPoint, Set<String> interfaces) {
		this.aug = aug;
		this.related = related;
		this.relatedJoinPoint = relatedJoinPoint;
		this.interfaces = interfaces;
	}
	
	public static Set<EnhancedAUG> buildEnhancedAugs(Set<APIUsageGraph> augs) {
		
		
		
		
		Map<String, APIUsageGraph> fieldInit = new HashMap<>();
		
		for (APIUsageGraph aug : augs) {
			if (aug instanceof APIUsageExample) {
				boolean isFieldInit = ((APIUsageExample)aug).getLocation().getMethodSignature().contains("__FieldOfClass__");
				if (!isFieldInit) continue;
				
				String name = ((APIUsageExample)aug).getLocation().getMethodSignature().split("#")[0];
				name = name.split("\\(__FieldOfClass")[0];
				fieldInit.put(name, aug);
			}
			
		}
		
		for (APIUsageGraph aug : augs) {
			if (aug instanceof APIUsageExample) {
				boolean isCtor = aug.isCtor;
				if (!isCtor) continue;
				
				for (Entry<String, Node> entry : aug.fieldsUsed.entrySet()) {
					String field = entry.getKey();
					
					fieldInit.put(field, aug);
				}
			}
		}
		
		Set<EnhancedAUG> result = new HashSet<>();
		Iterator<APIUsageGraph> iter = augs.iterator();
		
		
		while (iter.hasNext()) {
			Map<APIUsageGraph, Node> relatJoinPoint = new HashMap<>();
			APIUsageGraph aug = iter.next();
			
			Map<String, Node> fieldsUsed = aug.fieldsUsed;
			Set<APIUsageGraph> relat = new HashSet<>();
			for ( Entry<String, Node> entry : fieldsUsed.entrySet()) {
				String field = entry.getKey();
				if (!fieldInit.containsKey(field)) continue;
				
				relat.add(fieldInit.get(field));
				relatJoinPoint.put(fieldInit.get(field), fieldsUsed.get(field));
			}
			boolean isFieldInit = ((APIUsageExample)aug).getLocation().getMethodSignature().contains("__FieldOfClass__");
			if (isFieldInit) {
				continue;
			}
			
			
			result.add(new EnhancedAUG(aug, relat, relatJoinPoint, aug.interfaces));
		}
		
		return result;
	}
	
}
