package gov.nasa.jpl.aerie_data;

import gov.nasa.jpl.aerie.contrib.models.Accumulator;
import gov.nasa.jpl.aerie.merlin.framework.resources.real.RealResource;
import gov.nasa.jpl.aerie.merlin.protocol.types.RealDynamics;

public interface DataAllocation extends DataCollection {
    RealResource allocatedVolume = new Accumulator(0.0, 0.0);
    default RealResource allocatedVolume() {
        return allocatedVolume;
    }
    default RealResource unused() {
        return allocatedVolume().minus(this.dataSize());
    }
}
