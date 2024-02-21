package demosystem.generated.activities;

import demosystem.Mission;
import demosystem.activities.DeleteData;
import gov.nasa.jpl.aerie.contrib.serialization.mappers.NullableValueMapper;
import gov.nasa.jpl.aerie.contrib.serialization.rulesets.BasicValueMappers;
import gov.nasa.jpl.aerie.merlin.framework.ActivityMapper;
import gov.nasa.jpl.aerie.merlin.framework.ModelActions;
import gov.nasa.jpl.aerie.merlin.framework.ValueMapper;
import gov.nasa.jpl.aerie.merlin.protocol.driver.Topic;
import gov.nasa.jpl.aerie.merlin.protocol.model.InputType;
import gov.nasa.jpl.aerie.merlin.protocol.model.OutputType;
import gov.nasa.jpl.aerie.merlin.protocol.model.TaskFactory;
import gov.nasa.jpl.aerie.merlin.protocol.types.InstantiationException;
import gov.nasa.jpl.aerie.merlin.protocol.types.SerializedValue;
import gov.nasa.jpl.aerie.merlin.protocol.types.UnconstructableArgumentException;
import gov.nasa.jpl.aerie.merlin.protocol.types.Unit;
import gov.nasa.jpl.aerie.merlin.protocol.types.ValueSchema;
import gov.nasa.jpl.aerie_data.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated("gov.nasa.jpl.aerie.merlin.processor.MissionModelProcessor")
public final class DeleteDataMapper implements ActivityMapper<Mission, DeleteData, Unit> {
  private final Topic<DeleteData> inputTopic = new Topic<>();

  private final Topic<Unit> outputTopic = new Topic<>();

  @Override
  public InputType<DeleteData> getInputType() {
    return new InputMapper();
  }

  @Override
  public OutputType<Unit> getOutputType() {
    return new OutputMapper();
  }

  @Override
  public Topic<DeleteData> getInputTopic() {
    return this.inputTopic;
  }

  @Override
  public Topic<Unit> getOutputTopic() {
    return this.outputTopic;
  }

  @Override
  public TaskFactory<Unit> getTaskFactory(final Mission model, final DeleteData activity) {
    return ModelActions.threaded(() -> {
      ModelActions.emit(activity, this.inputTopic);
      activity.run(model);
      ModelActions.emit(Unit.UNIT, this.outputTopic);
      return Unit.UNIT;
    });
  }

  @Generated("gov.nasa.jpl.aerie.merlin.processor.MissionModelProcessor")
  public final class InputMapper implements InputType<DeleteData> {
    private final ValueMapper<Double> mapper_volume;

    private final ValueMapper<Boolean> mapper_limitToUnsentData;

    private final ValueMapper<Data.Bin> mapper_bin;

    @SuppressWarnings("unchecked")
    public InputMapper() {
      this.mapper_volume =
          BasicValueMappers.$double();
      this.mapper_limitToUnsentData =
          BasicValueMappers.$boolean();
      this.mapper_bin =
          new NullableValueMapper<>(
              BasicValueMappers.$enum(
                  Data.Bin.class));
    }

    @Override
    public List<String> getRequiredParameters() {
      return List.of();
    }

    @Override
    public ArrayList<InputType.Parameter> getParameters() {
      final var parameters = new ArrayList<InputType.Parameter>();
      parameters.add(new InputType.Parameter("volume", this.mapper_volume.getValueSchema()));
      parameters.add(new InputType.Parameter("limitToUnsentData", this.mapper_limitToUnsentData.getValueSchema()));
      parameters.add(new InputType.Parameter("bin", this.mapper_bin.getValueSchema()));
      return parameters;
    }

    @Override
    public Map<String, SerializedValue> getArguments(final DeleteData input) {
      final var arguments = new HashMap<String, SerializedValue>();
      arguments.put("volume", this.mapper_volume.serializeValue(input.volume));
      arguments.put("limitToUnsentData", this.mapper_limitToUnsentData.serializeValue(input.limitToUnsentData));
      arguments.put("bin", this.mapper_bin.serializeValue(input.bin));
      return arguments;
    }

    @Override
    public DeleteData instantiate(final Map<String, SerializedValue> arguments) throws
        InstantiationException {
      final var template = new DeleteData();
      Optional<Double> volume = Optional.ofNullable(template.volume);
      Optional<Boolean> limitToUnsentData = Optional.ofNullable(template.limitToUnsentData);
      Optional<Data.Bin> bin = Optional.ofNullable(template.bin);

      final var instantiationExBuilder = new InstantiationException.Builder("DeleteData");

      for (final var entry : arguments.entrySet()) {
        try {
          switch (entry.getKey()) {
            case "volume":
              volume = Optional.ofNullable(template.volume = this.mapper_volume.deserializeValue(entry.getValue())
                  .getSuccessOrThrow(failure -> new UnconstructableArgumentException("volume", failure)));
              break;
            case "limitToUnsentData":
              limitToUnsentData = Optional.ofNullable(template.limitToUnsentData = this.mapper_limitToUnsentData.deserializeValue(entry.getValue())
                  .getSuccessOrThrow(failure -> new UnconstructableArgumentException("limitToUnsentData", failure)));
              break;
            case "bin":
              bin = Optional.ofNullable(template.bin = this.mapper_bin.deserializeValue(entry.getValue())
                  .getSuccessOrThrow(failure -> new UnconstructableArgumentException("bin", failure)));
              break;
            default:
              instantiationExBuilder.withExtraneousArgument(entry.getKey());
          }
        } catch (final UnconstructableArgumentException e) {
          instantiationExBuilder.withUnconstructableArgument(e.parameterName, e.failure);
        }
      }

      volume.ifPresentOrElse(
          value -> instantiationExBuilder.withValidArgument("volume", this.mapper_volume.serializeValue(value)),
          () -> instantiationExBuilder.withMissingArgument("volume", this.mapper_volume.getValueSchema()));
      limitToUnsentData.ifPresentOrElse(
          value -> instantiationExBuilder.withValidArgument("limitToUnsentData", this.mapper_limitToUnsentData.serializeValue(value)),
          () -> instantiationExBuilder.withMissingArgument("limitToUnsentData", this.mapper_limitToUnsentData.getValueSchema()));
      bin.ifPresentOrElse(
          value -> instantiationExBuilder.withValidArgument("bin", this.mapper_bin.serializeValue(value)),
          () -> instantiationExBuilder.withMissingArgument("bin", this.mapper_bin.getValueSchema()));

      instantiationExBuilder.throwIfAny();
      return template;
    }

    @Override
    public List<InputType.ValidationNotice> getValidationFailures(final DeleteData input) {
      final var notices = new ArrayList<InputType.ValidationNotice>();
      return notices;
    }
  }

  public static final class OutputMapper implements OutputType<Unit> {
    private final ValueMapper<Unit> computedAttributesValueMapper = BasicValueMappers.$unit();

    @Override
    public ValueSchema getSchema() {
      return this.computedAttributesValueMapper.getValueSchema();
    }

    @Override
    public SerializedValue serialize(final Unit returnValue) {
      return this.computedAttributesValueMapper.serializeValue(returnValue);
    }
  }
}
