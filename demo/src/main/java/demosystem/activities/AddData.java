package demosystem.activities;

import demosystem.Buckets;
import demosystem.Mission;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType.EffectModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Parameter;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;

import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialEffects;
import gov.nasa.jpl.aerie_data.Bucket;

@ActivityType("AddData")
public class AddData {
  @Parameter public double bits = 1000;
  @Parameter public Duration duration = Duration.HOUR;
  @Parameter public Buckets bucket = Buckets.onboard;

  public AddData(double bits, Duration duration, Buckets bucket) {
    this.bits = bits;
    this.duration = duration;
    this.bucket = bucket;
  }

  public AddData(){}

  @EffectModel
  public void run(Mission model) {
    Bucket b = bucket == Buckets.ground ? model.ground : model.onboard;
    b.addVolume(bits, duration);
  }
}
