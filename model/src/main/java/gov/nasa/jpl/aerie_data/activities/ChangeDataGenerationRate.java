package gov.nasa.jpl.aerie_data.activities;

import gov.nasa.jpl.aerie.contrib.streamline.core.MutableResource;
import gov.nasa.jpl.aerie.contrib.streamline.core.Resources;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie.merlin.framework.annotations.ActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.Export;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.DataMissionModel;
import gov.nasa.jpl.aerie_data.Module;

import static gov.nasa.jpl.aerie.contrib.streamline.core.MutableResource.set;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial.polynomial;

@ActivityType("ChangeDataGenerationRate")
public class ChangeDataGenerationRate {

  @Export.Parameter
  public Module module = Module.LANDER;
  /**
   * The bin whose rate is changed
   */
  @Export.Parameter
  public int bin = 0;

  /**
   * The rate to instantly change
   */
  @Export.Parameter
  public double rate = 0.0;

  /**
   * Eliminates incoming and outgoing flows, only the new data rate is kept
   */
  @ActivityType.EffectModel
  public void run(DataMissionModel model) {
    Data data = model.getData();
    if (rate == 0.0) return;
    var binToChange = data.getOnboardBin(bin);

    if (rate > 0) {
      set((MutableResource<Polynomial>)binToChange.desiredReceiveRate, polynomial(rate));
      set((MutableResource<Polynomial>)binToChange.desiredRemoveRate, polynomial(0));
    } else {
      set((MutableResource<Polynomial>) binToChange.desiredReceiveRate, polynomial(0));
      set((MutableResource<Polynomial>) binToChange.desiredRemoveRate, polynomial(-rate));
    }
  }

}
