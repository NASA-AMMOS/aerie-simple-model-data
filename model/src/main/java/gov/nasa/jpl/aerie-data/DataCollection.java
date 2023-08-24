package gov.nasa.jpl.aerie-data;

import gov.nasa.jpl.aerie.merlin.framework.resources.real.RealResource;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;

public interface DataCollection {
    String name();
    RealResource dataSize();
    void add(long bits, Duration duration);
}
