package demosystem.generated.activities;

import demosystem.Mission;
import demosystem.activities.GenerateData;
import gov.nasa.jpl.aerie.contrib.serialization.mappers.NullableValueMapper;
import gov.nasa.jpl.aerie.contrib.serialization.rulesets.BasicValueMappers;
import gov.nasa.jpl.aerie.merlin.framework.ActivityMapper;
import gov.nasa.jpl.aerie.merlin.framework.ModelActions;
import gov.nasa.jpl.aerie.merlin.framework.ValueMapper;
import gov.nasa.jpl.aerie.merlin.protocol.driver.Topic;
import gov.nasa.jpl.aerie.merlin.protocol.model.InputType;
import gov.nasa.jpl.aerie.merlin.protocol.model.OutputType;
import gov.nasa.jpl.aerie.merlin.protocol.model.TaskFactory;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;
import gov.nasa.jpl.aerie.merlin.protocol.types.InstantiationException;
import gov.nasa.jpl.aerie.merlin.protocol.types.SerializedValue;
import gov.nasa.jpl.aerie.merlin.protocol.types.UnconstructableArgumentException;
import gov.nasa.jpl.aerie.merlin.protocol.types.Unit;
import gov.nasa.jpl.aerie.merlin.protocol.types.ValueSchema;
import gov.nasa.jpl.aerie_data.Data;
import gov.nasa.jpl.aerie_data.mappers.CommonValueMappers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated("gov.nasa.jpl.aerie.merlin.processor.MissionModelProcessor")
public final class GenerateDataMapper implements ActivityMapper<Mission, GenerateData, Unit> {
  private final Topic<GenerateData> inputTopic = new Topic<>();

  private final Topic<Unit> outputTopic = new Topic<>();

  @Override
  public InputType<GenerateData> getInputType() {
    return new InputMapper();
  }

  @Override
  public OutputType<Unit> getOutputType() {
    return new OutputMapper();
  }

  @Override
  public Topic<GenerateData> getInputTopic() {
    return this.inputTopic;
  }

  @Override
  public Topic<Unit> getOutputTopic() {
    return this.outputTopic;
  }

  @Override
  public TaskFactory<Unit> getTaskFactory(final Mission model, final GenerateData activity) {
    return ModelActions.threaded(() -> {
      ModelActions.emit(activity, this.inputTopic);
      activity.run(model);
      ModelActions.emit(Unit.UNIT, this.outputTopic);
      return Unit.UNIT;
    });
  }

  @Generated("gov.nasa.jpl.aerie.merlin.processor.MissionModelProcessor")
  public final class InputMapper implements InputType<GenerateData> {
    private final ValueMapper<Data.Bin> mapper_bin;

    private final ValueMapper<Optional<Double>> mapper_rate;

    private final ValueMapper<Optional<Double>> mapper_volume;

    private final ValueMapper<Optional<Duration>> mapper_duration;

    @SuppressWarnings("unchecked")
    public InputMapper() {
      this.mapper_bin =
          new NullableValueMapper<>(
              BasicValueMappers.$enum(
                  Data.Bin.class));
      this.mapper_rate =
          new NullableValueMapper<>(
              CommonValueMappers.optional(
                  BasicValueMappers.$double()));
      this.mapper_volume =
          new NullableValueMapper<>(
              CommonValueMappers.optional(
                  BasicValueMappers.$double()));
      this.mapper_duration =
          new NullableValueMapper<>(
              CommonValueMappers.optional(
                  BasicValueMappers.duration()));
    }

    @Override
    public List<String> getRequiredParameters() {
      return List.of();
    }

    @Override
    public ArrayList<InputType.Parameter> getParameters() {
      final var parameters = new ArrayList<InputType.Parameter>();
      parameters.add(new InputType.Parameter("bin", this.mapper_bin.getValueSchema()));
      parameters.add(new InputType.Parameter("rate", this.mapper_rate.getValueSchema()));
      parameters.add(new InputType.Parameter("volume", this.mapper_volume.getValueSchema()));
      parameters.add(new InputType.Parameter("duration", this.mapper_duration.getValueSchema()));
      return parameters;
    }

    @Override
    public Map<String, SerializedValue> getArguments(final GenerateData input) {
      final var arguments = new HashMap<String, SerializedValue>();
      arguments.put("bin", this.mapper_bin.serializeValue(input.bin));
      arguments.put("rate", this.mapper_rate.serializeValue(input.rate));
      arguments.put("volume", this.mapper_volume.serializeValue(input.volume));
      arguments.put("duration", this.mapper_duration.serializeValue(input.duration));
      return arguments;
    }

    @Override
    public GenerateData instantiate(final Map<String, SerializedValue> arguments) throws
        InstantiationException {
      final var template = new GenerateData();
      Optional<Data.Bin> bin = Optional.ofNullable(template.bin);
      Optional<Optional<Double>> rate = Optional.ofNullable(template.rate);
      Optional<Optional<Double>> volume = Optional.ofNullable(template.volume);
      Optional<Optional<Duration>> duration = Optional.ofNullable(template.duration);

      final var instantiationExBuilder = new InstantiationException.Builder("GenerateData");

      for (final var entry : arguments.entrySet()) {
        try {
          switch (entry.getKey()) {
            case "bin":
              bin = Optional.ofNullable(template.bin = this.mapper_bin.deserializeValue(entry.getValue())
                  .getSuccessOrThrow(failure -> new UnconstructableArgumentException("bin", failure)));
              break;
            case "rate":
              rate = Optional.ofNullable(template.rate = this.mapper_rate.deserializeValue(entry.getValue())
                  .getSuccessOrThrow(failure -> new UnconstructableArgumentException("rate", failure)));
              break;
            case "volume":
              volume = Optional.ofNullable(template.volume = this.mapper_volume.deserializeValue(entry.getValue())
                  .getSuccessOrThrow(failure -> new UnconstructableArgumentException("volume", failure)));
              break;
            case "duration":
              duration = Optional.ofNullable(template.duration = this.mapper_duration.deserializeValue(entry.getValue())
                  .getSuccessOrThrow(failure -> new UnconstructableArgumentException("duration", failure)));
              break;
            default:
              instantiationExBuilder.withExtraneousArgument(entry.getKey());
          }
        } catch (final UnconstructableArgumentException e) {
          instantiationExBuilder.withUnconstructableArgument(e.parameterName, e.failure);
        }
      }

      bin.ifPresentOrElse(
          value -> instantiationExBuilder.withValidArgument("bin", this.mapper_bin.serializeValue(value)),
          () -> instantiationExBuilder.withMissingArgument("bin", this.mapper_bin.getValueSchema()));
      rate.ifPresentOrElse(
          value -> instantiationExBuilder.withValidArgument("rate", this.mapper_rate.serializeValue(value)),
          () -> instantiationExBuilder.withMissingArgument("rate", this.mapper_rate.getValueSchema()));
      volume.ifPresentOrElse(
          value -> instantiationExBuilder.withValidArgument("volume", this.mapper_volume.serializeValue(value)),
          () -> instantiationExBuilder.withMissingArgument("volume", this.mapper_volume.getValueSchema()));
      duration.ifPresentOrElse(
          value -> instantiationExBuilder.withValidArgument("duration", this.mapper_duration.serializeValue(value)),
          () -> instantiationExBuilder.withMissingArgument("duration", this.mapper_duration.getValueSchema()));

      instantiationExBuilder.throwIfAny();
      return template;
    }

    @Override
    public List<InputType.ValidationNotice> getValidationFailures(final GenerateData input) {
      final var notices = new ArrayList<InputType.ValidationNotice>();
      try {
        if (!input.validateNonEmptyGoal()) notices.add(new InputType.ValidationNotice(List.of("rate", "volume", "duration"), "Two or three downlink goals must be specified: rate, volume, and/or duration."));
      } catch (Throwable ex) {
        notices.add(new InputType.ValidationNotice(List.of("rate", "volume", "duration"), ex.getMessage()));
      }
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
