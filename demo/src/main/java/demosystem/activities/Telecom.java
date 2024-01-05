package demosystem.activities;

import demosystem.Buckets;
import demosystem.Mission;
import demosystem.generated.ActivityActions;
import gov.nasa.jpl.aerie.contrib.streamline.core.CellResource;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie.merlin.framework.ModelActions;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType.EffectModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export.Parameter;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;

import javax.annotation.ParametersAreNonnullByDefault;

import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;

@ActivityType("Telecom")
public class Telecom {
  @Parameter public Duration duration;

  @Parameter public double bits;


  public Telecom(Duration duration, double bits) {
    this.duration = duration;
    this.bits = bits;
  }

  public Telecom(){}

  @EffectModel
  public void run(Mission model) {
    //model.ground.addVolume(bits, duration);
    ActivityActions.call(model, new AddData(bits, duration, Buckets.ground));
    ActivityActions.delay(duration);
    ActivityActions.call(model, new DeleteData(bits, duration, Buckets.onboard));
  }
}
