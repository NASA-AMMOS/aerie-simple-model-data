package gov.nasa.jpl.aerie_data.activities;

import gov.nasa.jpl.aerie.contrib.streamline.core.Resource;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie.merlin.framework.Condition;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;
import gov.nasa.jpl.aerie.merlin.protocol.types.RealDynamics;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.DataMissionModel;
import gov.nasa.jpl.aerie_data.Module;

import java.util.Optional;

import static gov.nasa.jpl.aerie.contrib.streamline.core.Resources.*;
import static gov.nasa.jpl.aerie.contrib.streamline.core.MutableResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialEffects.restore;
import static gov.nasa.jpl.aerie.merlin.framework.ModelActions.waitUntil;

@ActivityType("PlaybackData")
public class PlaybackData {
  @Export.Parameter
  public Module module = Module.LANDER;
  /**
   * Desired volume of data to downlink.  May not be achieved if data is not present.
   */
  @Export.Parameter
  public Optional<Double> volume = Optional.empty(); // bits
  @Export.Parameter
  public Optional<Duration> duration = Optional.empty();

  @ActivityType.EffectModel
  public void run(DataMissionModel model) {
    Data data = model.getData(module.name());
    var ground = data.ground;

    if (volume.isPresent() && volume.get() == 0.0) return;
    if (duration.isPresent() && duration.get().isEqualTo(Duration.ZERO)) return;

    if (currentValue(ground.receiveRate) > 0) {
      throw new RuntimeException("Only one PlaybackData activity at a time!");
    }

    final var targetGroundReceivedValue = volume.isEmpty() ? Double.MAX_VALUE : currentValue(ground.received) + volume.get();
    if (volume.isPresent()) {
      restore(data.volumeRequestedToDownlink, volume.get());
    }
    if (duration.isPresent()) {
      set(data.durationRequestedToDownlink, Polynomial.polynomial(duration.get().in(Duration.SECONDS), -1));
    }
    waitUntil(Condition.and(
      volume.isEmpty() ? Condition.TRUE : isBetween(ground.received, targetGroundReceivedValue, targetGroundReceivedValue * 2),
      duration.isEmpty() ? Condition.TRUE : isBetween(data.durationRequestedToDownlink, -2.0, 0)));
    if (volume.isPresent()) {
      set(data.volumeRequestedToDownlink, Polynomial.polynomial(0, 0));
    }
    if (duration.isPresent()) {
      set(data.durationRequestedToDownlink, Polynomial.polynomial(0, 0));
    }
  }

  private static Condition isBetween(Resource<Polynomial> r, final double lower, final double upper) {
    return (positive, atEarliest, atLatest) -> {
      final var p = r.getDynamics().getOrThrow().data();

      if (p.coefficients().length > 2) throw new RuntimeException("isBetween condition only for linear polynomials: resource = " + r);
      RealDynamics dynamics = RealDynamics.linear(p.getCoefficient(0), p.getCoefficient(1));

      return (positive)
        ? dynamics.whenBetween(lower, upper, atEarliest, atLatest)
        : dynamics.whenNotBetween(lower, upper, atEarliest, atLatest);
    };
  }

}
