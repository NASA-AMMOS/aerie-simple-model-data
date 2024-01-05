package gov.nasa.jpl.aerie_data;

import com.google.common.collect.Table;
import gov.nasa.jpl.aerie.contrib.models.Accumulator;
import gov.nasa.jpl.aerie.contrib.streamline.core.*;
import gov.nasa.jpl.aerie.contrib.streamline.core.monads.DynamicsMonad;
import gov.nasa.jpl.aerie.contrib.streamline.core.monads.ResourceMonad;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.discrete.Discrete;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.discrete.monads.DiscreteResourceMonad;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.Polynomial;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialEffects;
import gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources;
import gov.nasa.jpl.aerie.merlin.framework.Condition;
import gov.nasa.jpl.aerie.merlin.framework.ModelActions;
import gov.nasa.jpl.aerie.merlin.framework.Registrar;
import gov.nasa.jpl.aerie.merlin.protocol.types.Duration;

import java.util.List;
import java.util.Optional;
import java.util.function.DoublePredicate;
import java.util.stream.Collectors;

import static gov.nasa.jpl.aerie.contrib.streamline.core.Reactions.whenever;
import static gov.nasa.jpl.aerie.contrib.streamline.core.Resources.*;
//import static gov.nasa.jpl.aerie.contrib.streamline.core.monads.ExpiringMonad.effect;
//import static gov.nasa.jpl.aerie.contrib.streamline.core.monads.ResourceMonadTransformer.bind;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.discrete.DiscreteResources.when;
import static gov.nasa.jpl.aerie.contrib.streamline.modeling.polynomial.PolynomialResources.*;


public class Bucket {

    String name;
    CellResource<Polynomial> volume;


    public Bucket(String name) {
        this.name = name;
        volume = CellResource.cellResource(Polynomial.polynomial(0));
    }

    public String name() {
        return name;
    }

    public CellResource<Polynomial> volume() {
        return volume;
    }

    public void changeVolume(double new_volume) {
      this.volume.emit(DynamicsMonad.effect(($) -> {
        Double old_volume = $.extract();
        return $.add(Polynomial.polynomial(new double[]{new_volume - old_volume}));
      }));
    }

    public void changeRate(double newRate) {
      this.volume.emit(DynamicsMonad.effect(($) -> {
        Double old_rate = $.derivative().extract();
        Double change_in_rate = newRate - old_rate;
        return $.add(Polynomial.polynomial(new double[]{0.0, change_in_rate}));
      }));
    }

    public void addVolume(double bits, Duration duration) {
        PolynomialEffects.restore(this.volume, bits, duration);
    }

    public void deleteVolume(double bits, Duration duration) {
        PolynomialEffects.consume(this.volume, bits, duration);
    }

    // public void registerStates(Registrar registrar) {
    //     registrar.real("volume", linearize(volume));
    //     registrar.clearTrace();
    // }
}
