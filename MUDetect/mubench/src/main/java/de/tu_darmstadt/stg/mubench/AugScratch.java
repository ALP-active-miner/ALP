package de.tu_darmstadt.stg.mubench;

import java.nio.file.Path;
import java.util.Collection;
import java.util.logging.Logger;

import de.tu_darmstadt.stg.mubench.cli.MuBenchRunner;
import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import de.tu_darmstadt.stg.mudetect.aug.model.dot.AUGDotExporter;
import de.tu_darmstadt.stg.mudetect.aug.model.dot.AUGEdgeAttributeProvider;
import de.tu_darmstadt.stg.mudetect.aug.model.dot.AUGNodeAttributeProvider;
import de.tu_darmstadt.stg.mudetect.aug.persistence.PersistenceAUGDotExporter;
import de.tu_darmstadt.stg.mudetect.aug.visitors.BaseAUGLabelProvider;
import edu.iastate.cs.egroum.aug.AUGBuilder;

public class AugScratch {
	private final static Logger LOGGER = Logger.getLogger(AugScratch.class.getSimpleName());

    public static void main(String[] args) throws Exception {
        new MuBenchRunner().withMineAndDetectStrategy((detectorArgs, builder) -> {
            Collection<APIUsageExample> augs = new AUGBuilder(new DefaultAUGConfiguration())
                    .build(detectorArgs.getTargetSrcPaths(), detectorArgs.getDependencyClassPath());

            PersistenceAUGDotExporter exporter = new PersistenceAUGDotExporter();
            Path exportDest = detectorArgs.getAdditionalOutputPath().resolve("export");
            AUGDotExporter prettyPrinter = new AUGDotExporter(new BaseAUGLabelProvider(), new AUGNodeAttributeProvider(), new AUGEdgeAttributeProvider());
           
            
            
            return null;
        }).run(new String[]{"", ""});
    }

}
