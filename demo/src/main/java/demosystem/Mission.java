package demosystem;

import gov.nasa.jpl.aerie.contrib.streamline.core.Resource;
import gov.nasa.jpl.aerie.contrib.streamline.core.Resources;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.Registrar;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.linear.Linear;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.DataMissionModel;

import java.util.Optional;

import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources.*;

public class Mission implements DataMissionModel {

  private final Resource<Polynomial> dataRate = polynomialResource(100); // 100 bps
  public Data data = new Data(Optional.of(dataRate));

  public Mission(gov.nasa.jpl.aerie.merlin.framework.Registrar registrar, Configuration config) {
    Registrar newRegistrar = new Registrar(registrar, Registrar.ErrorBehavior.Throw);
    data.registerStates(newRegistrar);
  }

  @Override
  public Data getData() {
    return data;
  }
}

