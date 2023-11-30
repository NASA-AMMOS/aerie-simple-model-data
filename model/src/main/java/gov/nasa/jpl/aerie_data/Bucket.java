package gov.nasa.jpl.aerie_data;

import com.google.common.collect.Table;
import gov.nasa.jpl.aerie.contrib.models.Accumulator;
import gov.nasa.jpl.aerie.contrib.streamline.core.*;
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
    CellResource<Polynomial> rate;


    public Bucket(String name) {
        this.name = name;
        volume = CellResource.cellResource(Polynomial.polynomial(0));
        rate = CellResource.cellResource(Polynomial.polynomial(0));
    }

    public String name() {
        return name;
    }

    public CellResource<Polynomial> volume() {
        return volume;
    }

    public void changeVolume(double newVolume) {
        CellResource.set(this.volume, Polynomial.polynomial(newVolume));
    }

    public void changeRate(double newRate) {
        CellResource.set(this.rate, Polynomial.polynomial(newRate));
    }

    // public void registerStates(Registrar registrar) {
    //     registrar.real("volume", linearize(volume));
    //     registrar.clearTrace();
    // }
}
