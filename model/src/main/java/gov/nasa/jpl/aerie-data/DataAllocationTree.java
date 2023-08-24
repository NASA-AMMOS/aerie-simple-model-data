package gov.nasa.jpl.aerie-data;

import gov.nasa.jpl.aerie.merlin.framework.resources.real.RealResource;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;

import java.util.Map;

public class DataAllocationTree implements DataAllocation, DataCollectionTree {
    @Override
    public RealResource allocatedVolume() {
        return null;
    }

    @Override
    public RealResource unused() {
        return DataAllocation.super.unused();
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public RealResource dataSize() {
        return null;
    }

    @Override
    public void add(long bits, Duration duration) {

    }

    @Override
    public DataCollection parentCollecton() {
        return null;
    }

    @Override
    public Map<String, DataCollection> subCollections() {
        return null;
    }

    @Override
    public boolean isPartOfParent() {
        return false;
    }
}
