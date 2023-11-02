package demosystem.activities;

import demosystem.Mission;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType.EffectModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Parameter;
import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;

@ActivityType("RandomActOfViolence")
public class RandomActOfViolence {
    @Parameter public double newVal = 0.0;

    @EffectModel
    public void run(Mission model) {
        set(model.linearRes, polynomial(newVal));
    }
}
