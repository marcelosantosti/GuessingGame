package com.marcelosantos.guessinggame.domain.interfaces.repository;

import com.marcelosantos.guessinggame.domain.model.Animal;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
public interface IAnimalRepository extends IRepository<Animal, Integer> {

    List<Animal> getAnimalByDefault(boolean defaultAnimal) throws SQLException;
}
