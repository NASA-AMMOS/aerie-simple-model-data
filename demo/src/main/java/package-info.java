@MissionModel(model = Mission.class)
@MissionModel.WithMappers(CommonValueMappers.class)
@MissionModel.WithMappers(BasicValueMappers.class)
@MissionModel.WithConfiguration(Configuration.class)

@MissionModel.WithActivityType(ChangeDataGenerationRate.class)
@MissionModel.WithActivityType(DeleteData.class)
@MissionModel.WithActivityType(GenerateData.class)
@MissionModel.WithActivityType(PlaybackData.class)
@MissionModel.WithActivityType(ReprioritizeData.class)

@MissionModel.WithActivityType(SetDataRate.class)
@MissionModel.WithActivityType(SetMaxVolume.class)

package demosystem;

import gov.nasa.jpl.aerie.contrib.serialization.rulesets.BasicValueMappers;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel;
import demosystem.activities.*;
import gov.nasa.jpl.aerie_data.activities.*;
import gov.nasa.jpl.aerie_data.mappers.CommonValueMappers;
