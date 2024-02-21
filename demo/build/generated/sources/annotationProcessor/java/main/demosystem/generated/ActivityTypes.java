package demosystem.generated;

import demosystem.Mission;
import demosystem.generated.activities.DeleteDataMapper;
import demosystem.generated.activities.GenerateDataMapper;
import gov.nasa.jpl.aerie.merlin.framework.ActivityMapper;
import gov.nasa.jpl.aerie.merlin.protocol.driver.Initializer;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated("gov.nasa.jpl.aerie.merlin.processor.MissionModelProcessor")
public final class ActivityTypes {
  public static final DeleteDataMapper demosystem_generated_activities_DeleteDataMapper = new DeleteDataMapper();

  public static final GenerateDataMapper demosystem_generated_activities_GenerateDataMapper = new GenerateDataMapper();

  public static final Map<String, ActivityMapper<Mission, ?, ?>> directiveTypes = Map.ofEntries(
      Map.entry("DeleteData", demosystem_generated_activities_DeleteDataMapper),
      Map.entry("GenerateData", demosystem_generated_activities_GenerateDataMapper));

  public static void registerTopics(final Initializer initializer) {
    directiveTypes.forEach((name, mapper) -> registerDirectiveType(initializer, name, mapper));
  }

  private static <Input, Output> void registerDirectiveType(final Initializer initializer,
      final String name, final ActivityMapper<Mission, Input, Output> mapper) {
    initializer.topic("ActivityType.Input." + name, mapper.getInputTopic(), mapper.getInputAsOutput());
    initializer.topic("ActivityType.Output." + name, mapper.getOutputTopic(), mapper.getOutputType());
  }
}
