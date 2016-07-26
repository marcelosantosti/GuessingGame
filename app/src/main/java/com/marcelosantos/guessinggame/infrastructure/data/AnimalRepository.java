package com.marcelosantos.guessinggame.infrastructure.data;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.stmt.QueryBuilder;
import com.marcelosantos.guessinggame.domain.interfaces.repository.IAnimalRepository;
import com.marcelosantos.guessinggame.domain.model.Animal;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class AnimalRepository extends Repository<Animal, Integer> implements IAnimalRepository {

    @Inject
    public AnimalRepository(Context context) {

        super();
        super.setOrmLiteSqliteOpenHelperClass(DatabaseHelper.class);
        super.setEntityClass(Animal.class);
        super.setContext(context);
    }

    public List<Animal> getAnimalByDefault(boolean defaultAnimal) throws SQLException {

        QueryBuilder<Animal, Integer> queryBuilder = super.getDao().queryBuilder();
        queryBuilder.where().eq(Animal.DEFAULT_ANIMAL_FIELD_NAME, defaultAnimal);
        return super.getDao().query(queryBuilder.prepare());
    }
}
