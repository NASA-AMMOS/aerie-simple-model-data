package demosystem.generated;

import gov.nasa.jpl.aerie.contrib.serialization.mappers.NullableValueMapper;
import gov.nasa.jpl.aerie.contrib.serialization.rulesets.BasicValueMappers;
import gov.nasa.jpl.aerie.merlin.protocol.model.SchedulerModel;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;
import gov.nasa.jpl.aerie.merlin.protocol.types.DurationType;
import gov.nasa.jpl.aerie.merlin.protocol.types.SerializedValue;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated("gov.nasa.jpl.aerie.merlin.processor.MissionModelProcessor")
public final class GeneratedSchedulerModel implements SchedulerModel {
  @Override
  public Map<String, DurationType> getDurationTypes() {
    final var result = new HashMap<String, DurationType>();
    result.put("DeleteData", DurationType.uncontrollable());
    result.put("GenerateData", DurationType.uncontrollable());
    return result;
  }

  @Override
  public SerializedValue serializeDuration(final Duration duration) {
    return new NullableValueMapper<>(
            BasicValueMappers.duration()).serializeValue(duration);
  }

  @Override
  public Duration deserializeDuration(final SerializedValue serializedDuration) {
    return new NullableValueMapper<>(
            BasicValueMappers.duration()).deserializeValue(serializedDuration).getSuccessOrThrow();
  }
}
