package demosystem;

//import gov.nasa.jpl.aerie.contrib.streamline.modeling.*;
import gov.nasa.jpl.aerie.contrib.models.Accumulator;
import gov.nasa.jpl.aerie.contrib.streamline.core.CellResource;
import gov.nasa.jpl.aerie.contrib.streamline.core.Resource;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.linear.Linear;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie.merlin.framework.ModelActions;
import gov.nasa.jpl.aerie.merlin.framework.Registrar;
import gov.nasa.jpl.aerie_data.DataAllocationTree;

import static gov.nasa.jpl.aerie.contrib.streamline.core.monads.ResourceMonad.map;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.linear.Linear.linear;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;
import static gov.nasa.jpl.aerie.contrib.streamline.core.CellResource.cellResource;
//import gov.nasa.jpl.aerie.contrib.streamline.modeling.Registrar;

public class Mission {

  public CellResource<Polynomial> linearRes =  cellResource(polynomial(0));

  public Mission(final Registrar registrar, final Configuration config) {
    //CellResource<Polynomial> linearRes = cellResource(polynomial());
    //registrar.discrete("area", area, new DoubleValueMappper());
    var registrarStreamlined = new gov.nasa.jpl.aerie.contrib.streamline.modeling.Registrar(registrar);
    registrarStreamlined.real("linearRes", linearize(linearRes));

    DataAllocationTree dat = new DataAllocationTree("data volume");
    registrar.real(dat.name(), dat.allocatedVolume());
  }

  private static Resource<Linear> linearize(Resource<Polynomial> p) {
    return map(p, p$ -> {
      if (p$.degree() <= 1) {
        return linear(p$.getCoefficient(0), p$.getCoefficient(1));
      } else {
        throw new IllegalStateException("Resource was super-linear");
      }
    });
  }
}

