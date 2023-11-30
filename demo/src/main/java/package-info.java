@MissionModel(model = Mission.class)
@MissionModel.WithMappers(BasicValueMappers.class)
@MissionModel.WithConfiguration(Configuration.class)

@MissionModel.WithActivityType(RandomActOfViolence.class)
@MissionModel.WithActivityType(ChangeVolume.class)

package demosystem;

import demosystem.activities.RandomActOfViolence;
import gov.nasa.jpl.aerie.contrib.serialization.rulesets.BasicValueMappers;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel.WithActivityType;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel.WithConfiguration;
import gov.nasa.jpl.aerie.merlin.framework.annotations.MissionModel.WithMappers;
