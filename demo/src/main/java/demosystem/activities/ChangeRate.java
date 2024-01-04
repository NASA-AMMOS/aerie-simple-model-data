package demosystem.activities;

import demosystem.Buckets;
import demosystem.Mission;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType.EffectModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Parameter;
import gov.nasa.jpl.aerie_data.Bucket;

import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;

@ActivityType("ChangeRate")
public class ChangeRate {
  @Parameter public double newRate = 0.0;
  @Parameter
  public Buckets bucket = Buckets.onboard;

  @EffectModel
  public void run(Mission model) {
    Bucket b = bucket == Buckets.ground ? model.ground : model.onboard;
    b.changeRate(newRate);
  }
}