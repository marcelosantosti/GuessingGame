package com.marcelosantos.guessinggame.domain.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Marcelo on 17/10/2015.
 */
@DatabaseTable
public class Trait {

    public static final String ID_FIELD_NAME = "id";
    public static final String ID_ANIMAL_FIELD_NAME = "idAnimal";
    public static final String ID_BASE_TRAIT_FIELD_NAME = "idBaseTrait";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String description;

    @DatabaseField(canBeNull = true, foreign = true, columnName = Trait.ID_ANIMAL_FIELD_NAME, foreignAutoRefresh = true)
    private Animal animal;

    @DatabaseField(canBeNull = true, foreign = true, columnName = Trait.ID_BASE_TRAIT_FIELD_NAME, foreignAutoRefresh = true)
    private Trait baseTrait;

    @ForeignCollectionField(eager = true)
    private Collection<Trait> childTraits;

    public Trait() {


    }

    public Trait(String description) {

        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Trait getBaseTrait() {
        return baseTrait;
    }

    public void setBaseTrait(Trait baseTrait) {
        this.baseTrait = baseTrait;
    }

    public Collection<Trait> getChildTraits() {

        if (this.childTraits == null)
            this.childTraits = new ArrayList<>();

        return childTraits;
    }

    public void setChildTraits(Collection<Trait> childTraits) {
        this.childTraits = childTraits;
    }
}
