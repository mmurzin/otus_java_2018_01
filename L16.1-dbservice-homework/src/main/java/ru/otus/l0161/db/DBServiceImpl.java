package ru.otus.l0161.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.l0161.UserCredentials;
import ru.otus.l0161.cache.CacheElement;
import ru.otus.l0161.cache.CacheEngine;
import ru.otus.l0161.cache.CacheEngineImpl;
import ru.otus.l0161.models.AddressDataSet;
import ru.otus.l0161.models.PhoneDataSet;
import ru.otus.l0161.models.UserDataSet;

import javax.persistence.Table;
import java.util.List;

import static ru.otus.l0161.App.DEFAULT_LOGIN;
import static ru.otus.l0161.App.DEFAULT_PASSWORD;


public class DBServiceImpl implements DBService {


    private SessionFactory sessionFactory;
    private CacheEngine<Long, UserDataSet> cacheEngine;

    public DBServiceImpl(CacheEngine cacheEngine) {
        this.cacheEngine = cacheEngine;
        initService();
    }

    public static Class[] getDBModels() {
        return new Class[]{
                UserDataSet.class,
                PhoneDataSet.class,
                AddressDataSet.class
        };
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private void initService() {
        Configuration configuration = new Configuration();


        for (Class current : getDBModels()) {
            configuration.addAnnotatedClass(current);
        }

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/dbexample");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    @Override
    public void save(UserDataSet entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }
        if (cacheEngine != null) {
            cacheEngine.put(new CacheElement<>(entity.getId(), entity));
        }

    }

    @Override
    public void setCacheEnabled(boolean enabled) {
        if (enabled && cacheEngine == null) {
            cacheEngine = new CacheEngineImpl<>();
        }

        if (!enabled) {
            if (cacheEngine != null) {
                cacheEngine.dispose();
            }
            cacheEngine = null;
        }
    }

    @Override
    public CacheEngine getCacheEngine() {
        return cacheEngine;
    }


    @Override
    public boolean isSuccessfulLogin(UserCredentials credentials) {
        return DEFAULT_LOGIN.equals(credentials.getLogin()) &&
                DEFAULT_PASSWORD.equals(credentials.getPassword());

    }

    @Override
    public UserDataSet load(long id) {
        if (cacheEngine == null) {
            return loadFromDb(id);
        } else {
            UserDataSet cachedValue = cacheEngine.get(id);
            if (cachedValue != null) {
                return cachedValue;
            } else {
                return loadFromDb(id);
            }
        }


    }

    private UserDataSet loadFromDb(long id) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(id);
        }
    }

    @Override
    public List<UserDataSet> readAll() {
        List<UserDataSet> users;
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            users = dao.readAll();
        }
        return users;
    }

    @Override
    public void truncateTables(Class... items) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (Class current : items) {
                Object object = current.getAnnotation(Table.class);
                if (object != null &&
                        object instanceof Table) {
                    String tableName = ((Table) object).name();
                    session.createNativeQuery("TRUNCATE " + tableName).executeUpdate();
                }
            }
            transaction.commit();
        }
    }


    @Override
    public void shutdown() {
        sessionFactory.close();
        cacheEngine.dispose();
    }
}
