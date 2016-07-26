package com.marcelosantos.guessinggame.infrastructure.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.marcelosantos.guessinggame.domain.model.Animal;
import com.marcelosantos.guessinggame.domain.model.Trait;

import java.sql.SQLException;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, "guessinggame.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

        try {

            this.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {

            this.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTables() throws SQLException {

        TableUtils.createTableIfNotExists(connectionSource, Animal.class);
        TableUtils.createTableIfNotExists(connectionSource, Trait.class);
    }

    @Override
    public void close() {
        super.close();
    }
}
