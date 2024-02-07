package gov.nasa.jpl.aerie_data;

import gov.nasa.jpl.aerie.contrib.streamline.core.MutableResource;
import gov.nasa.jpl.aerie.contrib.streamline.core.Resource;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.Registrar;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.LinearBoundaryConsistencySolver;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources;

import java.util.*;

import static gov.nasa.jpl.aerie.contrib.streamline.core.MutableResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.core.Reactions.wheneverDynamicsChange;
import static gov.nasa.jpl.aerie.contrib.streamline.core.Resources.*;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.discrete.DiscreteResources.*;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources.*;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources.min;
import static gov.nasa.jpl.aerie.merlin.framework.ModelActions.spawn;


public class Data {
  private LinearBoundaryConsistencySolver rateSolver = new LinearBoundaryConsistencySolver("DataModel Rate Solver");

  public Bucket scBin1 = new Bucket(rateSolver, "sc bin 1", true, Collections.emptyList());
  public Bucket scBin2 = new Bucket(rateSolver, "sc bin 2", true, Collections.emptyList());
  public Bucket onboard = new Bucket(rateSolver, "onboard", false, List.of(scBin1, scBin2), constant(1e10)); // 10Gb

  public Bucket gBin1 = new Bucket(rateSolver, "gnd bin 1", true, Collections.emptyList());
  public Bucket gBin2 = new Bucket(rateSolver, "gnd bin 2", true, Collections.emptyList());
  public Bucket ground = new Bucket(rateSolver, "ground", false, List.of(gBin1, gBin2));;

  public Resource<Polynomial> dataRate;  // bps
  public MutableResource<Polynomial> volumeRequestedToDownlink = polynomialResource(0.0);
  public MutableResource<Polynomial> durationRequestedToDownlink = polynomialResource(0.0);

  public Bucket getBin(Bin bin) {
    var bucket = switch (bin) {
      case scBin1 -> scBin1;
      case scBin2 -> scBin2;
      case gBin1 -> gBin1;
      case gBin2 -> gBin2;
    };
    return bucket;
  }

  public Data(Optional<Resource<Polynomial>> dataRate) {
    if (dataRate.isPresent()) {
      this.dataRate = dataRate.get();
    } else {
      this.dataRate = polynomialResource(1.0);
    }
    var done = and(lessThanOrEquals(volumeRequestedToDownlink, 0),
      lessThanOrEquals(durationRequestedToDownlink, 0));
    Resource<Polynomial> downlinkRateLeft = choose(done, constant(0), this.dataRate);
    ArrayList<Resource<Polynomial>> actualDownlinkRates = new ArrayList<>();//(model.getData().onboard.children.size());
    for (int i = 0; i < onboard.children.size(); ++i) {
      Bucket scBin = onboard.children.get(i);
      Bucket gBin = ground.children.get(i);
      var availableVolumeToDownlink = subtract(scBin.received, gBin.received);
      var isEmpty = or(lessThanOrEquals(scBin.volume, 0),
        or(lessThanOrEquals(availableVolumeToDownlink, 0),
          and(lessThanOrEquals(volumeRequestedToDownlink, 0),
            lessThanOrEquals(durationRequestedToDownlink, 0))));
      var actualDownlinkRate =
        choose(isEmpty, max(constant(0), min(scBin.actualRate, downlinkRateLeft)),
          downlinkRateLeft);
      actualDownlinkRates.add(actualDownlinkRate);
      downlinkRateLeft = PolynomialResources.subtract(downlinkRateLeft, actualDownlinkRate);
      forward(eraseExpiry(actualDownlinkRate), (MutableResource<Polynomial>)gBin.desiredReceiveRate);
    }
    wheneverDynamicsChange(ground.actualRate, r -> {
      if (currentValue(volumeRequestedToDownlink) > 0)
        set(volumeRequestedToDownlink, Polynomial.polynomial(currentValue(volumeRequestedToDownlink), -data(r).extract()));
    });
    spawn(() -> {
      for (int i = 0; i < onboard.children.size(); ++i) {
        Bucket scBin = onboard.children.get(i);
        Bucket gBin = ground.children.get(i);
        set((MutableResource<Polynomial>) gBin.desiredReceiveRate, actualDownlinkRates.get(i).getDynamics().getOrThrow().data());
      }
    });
  }

  public void registerStates(Registrar registrar) {
    onboard.registerStates(registrar);
    ground.registerStates(registrar);
    registrar.real("volumeRequestedToDownlink", assumeLinear(volumeRequestedToDownlink));
    registrar.real("durationRequestedToDownlink", assumeLinear(durationRequestedToDownlink));
    registrar.real("playbackDataRate", assumeLinear(dataRate));
  }

  public enum Bin { scBin1, scBin2, gBin1, gBin2 }
}
