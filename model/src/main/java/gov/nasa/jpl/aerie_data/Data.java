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
  public static LinearBoundaryConsistencySolver rateSolver = new LinearBoundaryConsistencySolver("DataModel Rate Solver");

  /**
   * Specifies onboard and ground buckets
   */

  public Bucket onboard;

  public Bucket ground;

  public Resource<Polynomial> dataRate;  // bps
  public MutableResource<Polynomial> volumeRequestedToDownlink = polynomialResource(0.0);
  public MutableResource<Polynomial> durationRequestedToDownlink = polynomialResource(0.0);

  public ArrayList<Bucket> onboardBuckets = new ArrayList<>();

  public ArrayList<Bucket> groundBuckets = new ArrayList<>();

  public Bucket getOnboardBin(int bin) {
    return onboardBuckets.get(bin);
  }

  public Bucket getGroundBin(int bin) {
    return groundBuckets.get(bin);
  }

  public Data(Optional<Resource<Polynomial>> dataRate, int numBuckets, Resource<Polynomial> upperBound) {

    for (int i = 0; i < numBuckets; ++i) {
      Bucket scBin = new Bucket("scBin" + i, true, Collections.emptyList());
      onboardBuckets.add(scBin);
      Bucket gBin = new Bucket("gndBin" + i, true, Collections.emptyList());
      groundBuckets.add(gBin);
    }

    onboard = new Bucket("onboard", false, onboardBuckets, upperBound); // 10Gb

    ground = new Bucket("ground", false, groundBuckets);

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

}
