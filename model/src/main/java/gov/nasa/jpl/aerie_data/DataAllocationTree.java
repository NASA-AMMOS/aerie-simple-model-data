package gov.nasa.jpl.aerie_data;

import gov.nasa.jpl.aerie.contrib.models.Accumulator;
import gov.nasa.jpl.aerie.merlin.framework.ModelActions;
import gov.nasa.jpl.aerie.merlin.framework.resources.real.RealResource;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;

import java.util.Map;

public class DataAllocationTree implements DataAllocation, DataCollectionTree {

  public String name;
  public DataAllocationTree(String name) {
    this.name = name;
  }

  @Override
    public RealResource allocatedVolume() {
        return allocatedVolume;
    }

    @Override
    public RealResource unused() {
        return DataAllocation.super.unused();
    }

    @Override
    public String name() {
        return name();
    }

    @Override
    public RealResource dataSize() {
        return allocatedVolume;
    }

    @Override
    public void add(long bits, Duration duration) {
      double rate = bits/(Math.min(1.0, duration.in(Duration.MICROSECONDS)/1E6));
      ((Accumulator)allocatedVolume).rate.add(rate);
      ModelActions.delay(duration);
      ((Accumulator)allocatedVolume).rate.add(-rate);
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
