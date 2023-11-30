package demosystem.activities;

import demosystem.Mission;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType.EffectModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Parameter;
import gov.nasa.jpl.aerie_data.Bucket;

import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;

@ActivityType("ChangeVolume")
public class ChangeVolume {
    @Parameter public double newVolume = 0.0;
    @Parameter Bucket bucket;

    @EffectModel
    public void run(Mission model) {
        bucket.changeVolume(newVolume);
    }
}
