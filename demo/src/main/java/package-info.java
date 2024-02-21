@MissionModel(model = Mission.class)
@MissionModel.WithMappers(CommonValueMappers.class)
@MissionModel.WithMappers(BasicValueMappers.class)
@MissionModel.WithConfiguration(Configuration.class)

@MissionModel.WithActivityType(DeleteData.class)
@MissionModel.WithActivityType(GenerateData.class)

package demosystem;

import gov.nasa.jpl.aerie.contrib.serialization.rulesets.BasicValueMappers;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel;
import gov.nasa.jpl.aerie_data.mappers.CommonValueMappers;
import demosystem.activities.DeleteData;
import demosystem.activities.GenerateData;
