package com.marcelosantos.guessinggame.domain.interfaces.service;

import java.sql.SQLException;

/**
 * Created by Marcelo on 17/10/2015.
 */
public interface IAnimalService {

    void createDefaultAnimal() throws SQLException;
}
