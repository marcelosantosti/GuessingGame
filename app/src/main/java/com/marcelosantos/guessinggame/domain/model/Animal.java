package com.marcelosantos.guessinggame.domain.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
@DatabaseTable
public class Animal {

    public static final String DEFAULT_ANIMAL_FIELD_NAME = "defaultAnimal";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private boolean defaultAnimal;

    public Animal() {

    }

    public Animal(String name) {

        this.name = name;
    }

    private List<Trait> traits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }

    public Boolean getDefaultAnimal() {
        return defaultAnimal;
    }

    public void setDefaultAnimal(Boolean defaultAnimal) {
        this.defaultAnimal = defaultAnimal;
    }
}
