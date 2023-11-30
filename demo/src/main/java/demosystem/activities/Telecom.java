package demosystem.activities;

import demosystem.Mission;
import gov.nasa.jpl.aerie.contrib.streamline.core.CellResource;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
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
  @Parameter public double rate;

  @EffectModel
  public void run(Mission model) {
    CellResource<Polynomial> maxVolume = model.onboard.volume();

  }
}
