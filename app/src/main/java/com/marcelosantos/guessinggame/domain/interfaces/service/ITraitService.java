package com.marcelosantos.guessinggame.domain.interfaces.service;

import com.marcelosantos.guessinggame.domain.model.Trait;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
public interface ITraitService {

    void createDefaultTrait() throws SQLException;
    void createTrait(Trait trait) throws SQLException;
    List<Trait> getOrderedTrait() throws SQLException;
}
