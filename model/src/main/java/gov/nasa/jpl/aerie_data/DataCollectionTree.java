package gov.nasa.jpl.aerie_data;

import java.util.Map;

public interface DataCollectionTree extends DataCollection {
    DataCollection parentCollecton();
    Map<String, DataCollection> subCollections();
    boolean isPartOfParent();
}
