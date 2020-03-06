package de.tu_darmstadt.stg.mudetect.aug;

import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageGraph;
import de.tu_darmstadt.stg.mudetect.aug.model.dot.DisplayAUGDotExporter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class AUGTestUtils {
    public static <T extends APIUsageGraph> void exportAUGsAsPNG(Collection<T> augs, String filePath, String baseName) {
        Iterator<? extends APIUsageGraph> it = augs.iterator();
        for (int i = 0; it.hasNext() ; i++) {
            APIUsageGraph aug = it.next();
            if (aug instanceof APIUsageExample) {
            	exportAUGasPNG(aug, new File(filePath, baseName + "-" + i + "-" 
            + ((APIUsageExample)aug).getLocation().getMethodSignature() + ".png").getPath());
            }
            exportAUGasPNG(aug, new File(filePath, baseName + "-" + i + ".png").getPath());
        }
    }
    
    
 

    public static void exportAUGasPNG(APIUsageGraph aug, String filePath) {
        try {
        	if (aug.edgeSet().size() > 250) {
        		System.out.println("Not exporting because its too big");
        		return;
        	}
        	
            new DisplayAUGDotExporter().toPNGFile(aug, new File(filePath));
            System.out.println("Exported");
        } catch (Exception e) {
            throw new RuntimeException("failed to export DOT as PNG", e);
        }
    }
}
