package demosystem.activities;

import demosystem.Mission;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType.EffectModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Parameter;
import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;

@ActivityType("DeleteData")
public class DeleteData {
  @Parameter public double newVolume = 0.0;

  @EffectModel
  public void run(Mission model) {
    model.bucket.changeVolume(newVolume);
  }
}
