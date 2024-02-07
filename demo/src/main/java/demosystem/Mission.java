package demosystem;

//import gov.nasa.jpl.aerie.contrib.streamline.modeling.*;
import gov.nasa.jpl.aerie.contrib.models.Accumulator;
import gov.nasa.jpl.aerie.contrib.streamline.core.Resource;
import gov.nasa.jpl.aerie.contrib.streamline.core.Resources;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.linear.Linear;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie.merlin.framework.Registrar;
import gov.nasa.jpl.aerie_data.Bucket;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.DataMissionModel;

import static gov.nasa.jpl.aerie.contrib.streamline.core.monads.ResourceMonad.map;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.linear.Linear.linear;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;
//import gov.nasa.jpl.aerie.contrib.streamline.modeling.Registrar;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources.*;

public class Mission implements DataMissionModel {

  public Resource<Polynomial> linearRes = polynomialResource(0.0);
  public Mission(final Registrar registrar, final Configuration config) {
    Resources.init();
    //CellResource<Polynomial> linearRes = cellResource(polynomial());
    //registrar.discrete("area", area, new DoubleValueMappper());
    //registrarStreamlined.real("linearRes", linearize(linearRes));
  }

  @Override
  public Data getData() {
    return null;
  }
}

