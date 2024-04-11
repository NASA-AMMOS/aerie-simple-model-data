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
  public int bin = 0;

  @ActivityType.EffectModel
  public void run(DataMissionModel model) {
    Data data = model.getData();
    var binToChange = data.getOnboardBin(bin);
    var groundBin = data.getGroundBin(bin);

    double currentVolume = currentValue(binToChange.volume);
    double MAX = Double.MAX_VALUE;
    double volumeNotYetDownlinked = groundBin == null ? MAX : (currentValue(binToChange.received) - currentValue(groundBin.received));
    double actualVolumeDeleted =
      Math.min(volume, Math.min(currentVolume, limitToUnsentData ? volumeNotYetDownlinked : MAX));
    System.out.println("DeleteData(" + currentTime() + "): actualVolumeDeleted = " + actualVolumeDeleted);

    binToChange.remove(actualVolumeDeleted);

  }


}
