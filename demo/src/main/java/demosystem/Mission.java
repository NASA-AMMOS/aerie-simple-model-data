package demosystem;

import gov.nasa.jpl.aerie.contrib.streamline.core.MutableResource;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.Registrar;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.discrete.Discrete;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie_data.Bucket;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.DataMissionModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static gov.nasa.jpl.aerie.contrib.streamline.core.Resources.forward;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.discrete.DiscreteResources.discreteResource;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources.*;

/**
 * Mission is the Aerie mission model that is instantiated at the start of simulation.  This is where
 * resources are created and registered to be included in the simulation results.
 * <p/>
 * The DataMissionModel interface enables activities to access the Data object with the bins and resources.
 */
public class Mission implements DataMissionModel {

  /**
   * A resource specifying the spacecraft's data rate for playback to ground in bits per second.
   */
  public final MutableResource<Discrete<Double>> landerDataRate; // bps

  /**
   * This is a cap on the spacecraft's data storage.
   */
  public final MutableResource<Discrete<Double>> landerMaxVolune; // bits

  /**
   * This is the interface to data Buckets and corresponding resources from the model package.
   */
  public Data landerData;

  /**
   * A resource specifying the spacecraft's data rate for playback to ground in bits per second.
   */
  public final MutableResource<Discrete<Double>> roverDataRate; // bps

  /**
   * This is a cap on the spacecraft's data storage.
   */
  public final MutableResource<Discrete<Double>> roverMaxVolune; // bits

  /**
   * This is the interface to data Buckets and corresponding resources from the model package.
   */
  public Data roverData;
  /**
   * A resource specifying the spacecraft's data rate for playback to ground in bits per second.
   */
  public final MutableResource<Discrete<Double>> baseStationDataRate; // bps

  /**
   * This is a cap on the spacecraft's data storage.
   */
  public final MutableResource<Discrete<Double>> baseStationMaxVolune; // bits

  /**
   * This is the interface to data Buckets and corresponding resources from the model package.
   */
  public Data baseStationData;

  public Mission(gov.nasa.jpl.aerie.merlin.framework.Registrar registrar, Configuration config) {
    Registrar newRegistrar = new Registrar(registrar, Registrar.ErrorBehavior.Throw);

    this.landerDataRate = discreteResource(config.initialLanderDatarate()); // bps
    this.landerMaxVolune = discreteResource(config.initialLanderMaxVolume()); // bits
    this.roverDataRate = discreteResource(config.initialLanderDatarate()); // bps
    this.roverMaxVolune = discreteResource(config.initialLanderMaxVolume()); // bits
    this.baseStationDataRate = discreteResource(config.initialLanderDatarate()); // bps
    this.baseStationMaxVolune = discreteResource(config.initialLanderMaxVolume()); // bits

    // Two buckets/bins for the spacecraft and two for ground are created here by passing in 2 below.
    // The ground bins track how much data has been played back/downloaded from the spacecraft.
    // bin0 is higher priority than bin 1.
    // The parent bucket has a limit of 10Gb (by default from the Configuration).
    this.landerData = new Data(Optional.of(asPolynomial(landerDataRate)), 2,   // bin 0 is from base station, bin 1 is any other data
      asPolynomial(landerMaxVolune));
    landerData.registerStates("lander.", newRegistrar);
    this.roverData = new Data(Optional.of(asPolynomial(roverDataRate)), 3, constant(Double.MAX_VALUE), asPolynomial(roverMaxVolune));
    roverData.registerStates("rover.", newRegistrar);
    this.baseStationData = new Data(Optional.of(asPolynomial(baseStationDataRate)), 2,   // bin 0 is from rovers, bin 1 is engineering data
      asPolynomial(baseStationMaxVolune));
    baseStationData.registerStates("baseStation.", newRegistrar);

    // Transfer of rover data to the base station is done via Playback
    forward(roverData.ground.actualRate, (MutableResource<Polynomial>) baseStationData.getOnboardBin(0).desiredReceiveRate);
    // Transfer of base station data to the lander is done via Playback
    forward(baseStationData.ground.actualRate, (MutableResource<Polynomial>) landerData.getOnboardBin(0).desiredReceiveRate);
  }

  @Override
  public Data getData() {
    return landerData;
  }
  @Override
  public Data getData(String dataIdentifier) {
    if (dataIdentifier.equalsIgnoreCase("rover") || dataIdentifier.equalsIgnoreCase("rovers"))
      return roverData;
    if (dataIdentifier.equalsIgnoreCase("baseStation") || dataIdentifier.equalsIgnoreCase("base"))
      return baseStationData;
    if (dataIdentifier.equals("") || dataIdentifier.equalsIgnoreCase("lander"))
      return landerData;
    throw new RuntimeException("Unknown data identifier: " + dataIdentifier + ".  Expecting one of (lander, baseStation, or rover)");
  }
}

