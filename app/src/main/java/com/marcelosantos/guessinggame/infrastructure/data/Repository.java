package com.marcelosantos.guessinggame.infrastructure.data;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.marcelosantos.guessinggame.domain.interfaces.repository.IRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcelo on 17/10/2015.
 */
public class Repository<T, ID> implements IRepository<T, ID> {

    private BaseDaoImpl<T, ID> dao;
    private OrmLiteSqliteOpenHelper ormLiteSqliteOpenHelper;
    private Class ormLiteSqliteOpenHelperClass;
    private Class<T> entityClass;

    private Context context;

    public Repository() {


    }

    public Dao<T, ID> getDao() throws SQLException {

        this.instantiateDao();

        return this.dao;
    }

    private void instantiateDao() throws SQLException {

        if (this.ormLiteSqliteOpenHelper == null)
            this.ormLiteSqliteOpenHelper = OpenHelperManager.getHelper(this.context, ormLiteSqliteOpenHelperClass);

        if (dao == null)
            dao = DaoManager.createDao(this.ormLiteSqliteOpenHelper.getConnectionSource(), this.entityClass);
    }

    public <TI, ID1> QueryBuilder<TI, ID1> getQueryBuilder(Class type) throws SQLException {

        return DaoManager.createDao(ormLiteSqliteOpenHelper.getConnectionSource(), type).queryBuilder();
    }

    @Override
    public void createOrUpdate(T object) throws SQLException {

        this.getDao().createOrUpdate(object);
    }

    @Override
    public void createIfNotExists(T object) throws SQLException {

        this.getDao().createIfNotExists(object);
    }

    @Override
    public void create(T object) throws SQLException {

        this.getDao().create(object);
    }

    @Override
    public void update(T object) throws SQLException {

        this.getDao().update(object);
    }

    @Override
    public void delete(T object)throws SQLException{
        this.getDao().delete(object);
    }

    @Override
    public T queryForId(ID id) throws SQLException {

        return this.getDao().queryForId(id);
    }

    @Override
    public List<T> queryForAll() throws SQLException {

        return this.getDao().queryForAll();
    }

    @Override
    public void refresh(T object) throws SQLException {

        this.getDao().refresh(object);
    }

    protected List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {

        return this.dao.query(preparedQuery);
    }

    protected QueryBuilder<T, ID> getQueryBuilder() throws SQLException {

        this.instantiateDao();

        return this.dao.queryBuilder();
    }

    public Class getOrmLiteSqliteOpenHelperClass() {

        return ormLiteSqliteOpenHelperClass;
    }

    public void setOrmLiteSqliteOpenHelperClass(Class ormLiteSqliteOpenHelperClass) {

        this.ormLiteSqliteOpenHelperClass = ormLiteSqliteOpenHelperClass;
    }

    public Class getEntityClass() {

        return entityClass;
    }

    public void setEntityClass(Class entityClass) {

        this.entityClass = entityClass;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
