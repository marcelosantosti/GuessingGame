package com.marcelosantos.guessinggame.userinterface.model;

import com.marcelosantos.guessinggame.domain.model.Animal;

/**
 * Created by Marcelo on 20/10/2015.
 */
public class AskTraitQuestion extends Question {

    private Animal previusAnimal;

    public Animal getPreviusAnimal() {
        return previusAnimal;
    }

    public void setPreviusAnimal(Animal previusAnimal) {
        this.previusAnimal = previusAnimal;
    }
}
