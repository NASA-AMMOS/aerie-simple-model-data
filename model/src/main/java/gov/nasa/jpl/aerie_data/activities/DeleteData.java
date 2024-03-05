package gov.nasa.jpl.aerie_data.activities;

import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.DataMissionModel;

import static gov.nasa.jpl.aerie.contrib.streamline.core.Resources.*;

@ActivityType("DeleteData")
public class DeleteData {
  /**
   * The maximum volume to delete depending on {@link #limitToUnsentData} and the volume of the bin
   */
  @Export.Parameter
  public double volume; // bits

  /**
   * Whether to limit the amount deleted to that which has been downlinked
   */
  @Export.Parameter
  public boolean limitToUnsentData = true;

  /**
   * The bin whose data is to be deleted
   */
  @Export.Parameter
  public Data.Bin bin = Data.Bin.scBin1;

  @ActivityType.EffectModel
  public void run(DataMissionModel model) {
    Data data = model.getData();
    var binToChange = data.getBin(bin);
    var groundBin = switch (bin) {
      case scBin1 -> data.gBin1;
      case scBin2 -> data.gBin2;
      case gBin1, gBin2 -> null;
    };

    // TODO -- should we forget about limiting and just track lost data in Data and data that couldn't be deleted in Bucket.
    // TODO -- we could have a DeleteAllSentData activity
    double currentVolume = currentValue(binToChange.volume);
    double MAX = Double.MAX_VALUE;
    double volumeNotYetDownlinked = groundBin == null ? MAX : (currentValue(binToChange.received) - currentValue(groundBin.received));
    double actualVolumeDeleted =
      Math.min(volume, Math.min(currentVolume, limitToUnsentData ? volumeNotYetDownlinked : MAX));
    System.out.println("DeleteData(" + currentTime() + "): actualVolumeDeleted = " + actualVolumeDeleted);

    binToChange.remove(actualVolumeDeleted);

    //return new ComputedAttributes(actualVolumeDeleted);
  }

//    @AutoValueMapper.Record
//    public record ComputedAttributes(double actualVolumeDeleted) {}

}
