package com.marcelosantos.guessinggame.domain.service;

import android.content.Context;

import com.google.inject.Inject;
import com.marcelosantos.guessinggame.R;
import com.marcelosantos.guessinggame.domain.interfaces.repository.IAnimalRepository;
import com.marcelosantos.guessinggame.domain.interfaces.service.IAnimalService;
import com.marcelosantos.guessinggame.domain.model.Animal;

import java.sql.SQLException;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class AnimalService implements IAnimalService {

    private IAnimalRepository animalRepository;
    private Context context;

    @Inject
    public AnimalService(IAnimalRepository animalRepository, Context context) {

        this.animalRepository = animalRepository;
        this.context = context;
    }

    @Override
    public void createDefaultAnimal() throws SQLException {

        Animal animalMonkey = new Animal(this.context.getString(R.string.monkey));
        animalMonkey.setDefaultAnimal(true);

        this.animalRepository.createIfNotExists(animalMonkey);
    }
}
