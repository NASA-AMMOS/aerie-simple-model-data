package demosystem;

import gov.nasa.jpl.aerie.contrib.streamline.core.Resource;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.Registrar;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.DataMissionModel;

import java.util.Optional;

import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources.*;

/**
 * Mission is the Aerie mission model that is instantiated at the start of simulation.  This is where
 * resources are created and registered to be included in the simulation results.
 * <p/>
 * The DataMissionModel interface enables activities to access the Data object with the bins and resources.
 */
public class Mission implements DataMissionModel {

  // A resource specifying the spacecraft's data rate for playback to ground.
  private final Resource<Polynomial> dataRate = polynomialResource(100); // 100 bps

  // Two buckets/bins for the spacecraft and two for ground are created here by passing in 2.  The ground
  // bins track how much data has been played back/downloaded from the spacecraft.  The parent
  // bucket has a limit of 10Gb.  bin0 is higher priority than bin 1.
  public Data data = new Data(Optional.of(dataRate), 2, constant(1e10));

  public Mission(gov.nasa.jpl.aerie.merlin.framework.Registrar registrar, Configuration config) {
    Registrar newRegistrar = new Registrar(registrar, Registrar.ErrorBehavior.Throw);
    data.registerStates(newRegistrar);
  }

  @Override
  public Data getData() {
    return data;
  }
}

