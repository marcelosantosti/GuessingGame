package com.marcelosantos.guessinggame.infrastructure.data;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.marcelosantos.guessinggame.domain.interfaces.repository.ITraitRepository;
import com.marcelosantos.guessinggame.domain.model.Trait;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class TraitRepository extends Repository<Trait, Integer> implements ITraitRepository {

    @Inject
    public TraitRepository(Context context) {

        super();
        super.setOrmLiteSqliteOpenHelperClass(DatabaseHelper.class);
        super.setEntityClass(Trait.class);
        super.setContext(context);
    }

    @Override
    public List<Trait> getListTraitOrdered(String field, boolean ascending) throws SQLException {

        QueryBuilder<Trait, Integer> queryBuilder = super.getQueryBuilder();
        queryBuilder.where().isNull(Trait.ID_BASE_TRAIT_FIELD_NAME);
        queryBuilder.orderBy(field, ascending);
        return super.query(queryBuilder.prepare());
    }
}
