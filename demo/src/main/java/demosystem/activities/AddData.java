package demosystem.activities;

import demosystem.Mission;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType.EffectModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Parameter;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;

import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;

@ActivityType("AddData")
public class AddData {
  @Parameter public double bits;
  @Parameter public Duration duration;

  @EffectModel
  public void run(Mission model) {
    double rate = bits/(Math.min(1.0, duration.in(Duration.MICROSECONDS)/1E6));
  }
}
