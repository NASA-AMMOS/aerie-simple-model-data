@MissionModel(model = Mission.class)
@MissionModel.WithMappers(BasicValueMappers.class)
@MissionModel.WithConfiguration(Configuration.class)

@MissionModel.WithActivityType(RandomActOfViolence.class)
@MissionModel.WithActivityType(ChangeVolume.class)
@MissionModel.WithActivityType(AddData.class)
@MissionModel.WithActivityType(ChangeRate.class)
@MissionModel.WithActivityType(DeleteData.class)
@MissionModel.WithActivityType(Telecom.class)

package demosystem;

import demosystem.activities.*;
import gov.nasa.jpl.aerie.contrib.serialization.rulesets.BasicValueMappers;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel.WithActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel.WithConfiguration;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel.WithMappers;
