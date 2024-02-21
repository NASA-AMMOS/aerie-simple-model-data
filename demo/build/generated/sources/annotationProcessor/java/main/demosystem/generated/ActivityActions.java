package demosystem.generated;

import demosystem.Mission;
import demosystem.activities.DeleteData;
import demosystem.activities.GenerateData;
import gov.nasa.jpl.aerie.merlin.framework.ModelActions;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;
import javax.annotation.processing.Generated;

@Generated("gov.nasa.jpl.aerie.merlin.processor.MissionModelProcessor")
public class ActivityActions extends ModelActions {
  private ActivityActions() {
  }

  public static void spawn(final Mission model, final DeleteData activity) {
    final var mapper = ActivityTypes.demosystem_generated_activities_DeleteDataMapper;
    ModelActions.spawn(mapper.getTaskFactory(model, activity));
  }

  public static void defer(final Duration duration, final Mission model,
      final DeleteData activity) {
    final var mapper = ActivityTypes.demosystem_generated_activities_DeleteDataMapper;
    ModelActions.defer(duration, mapper.getTaskFactory(model, activity));
  }

  public static void defer(final long quantity, final Duration unit, final Mission model,
      final DeleteData activity) {
    defer(unit.times(quantity), model, activity);
  }

  public static void call(final Mission model, final DeleteData activity) {
    final var mapper = ActivityTypes.demosystem_generated_activities_DeleteDataMapper;
    ModelActions.call(mapper.getTaskFactory(model, activity));
  }

  public static void spawn(final Mission model, final GenerateData activity) {
    final var mapper = ActivityTypes.demosystem_generated_activities_GenerateDataMapper;
    ModelActions.spawn(mapper.getTaskFactory(model, activity));
  }

  public static void defer(final Duration duration, final Mission model,
      final GenerateData activity) {
    final var mapper = ActivityTypes.demosystem_generated_activities_GenerateDataMapper;
    ModelActions.defer(duration, mapper.getTaskFactory(model, activity));
  }

  public static void defer(final long quantity, final Duration unit, final Mission model,
      final GenerateData activity) {
    defer(unit.times(quantity), model, activity);
  }

  public static void call(final Mission model, final GenerateData activity) {
    final var mapper = ActivityTypes.demosystem_generated_activities_GenerateDataMapper;
    ModelActions.call(mapper.getTaskFactory(model, activity));
  }
}
