package com.marcelosantos.guessinggame.domain.interfaces.repository;

import com.marcelosantos.guessinggame.domain.model.Trait;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
public interface ITraitRepository extends IRepository<Trait, Integer> {

    List<Trait> getListTraitOrdered(String field, boolean ascending) throws SQLException;
}
