package ru.otus.l0121;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.l0121.cache.CacheEngine;
import ru.otus.l0121.cache.CacheInformation;
import ru.otus.l0121.db.DBService;
import ru.otus.l0121.db.DBServiceImpl;
import ru.otus.l0121.models.AddressDataSet;
import ru.otus.l0121.models.PhoneDataSet;
import ru.otus.l0121.models.UserDataSet;
import ru.otus.l0121.servlets.CacheServlet;
import ru.otus.l0121.servlets.LoginServlet;

import java.util.*;

import static ru.otus.l0121.cache.CacheEngineImpl.DEFAULT_CACHE_SIZE;

public class App {
    private static final String PUBLIC_HTML = "public_html";
    private static final int PORT = 8090;

    public static void main(String[] args) throws Exception {

        DBService dbService = new DBServiceImpl(getDBModels());

        dbService.truncateTables(getDBModels());

        addModelsToDataBase(dbService);

        CacheInformation cacheInformation = null;
        CacheEngine cacheEngine = dbService.getCacheEngine();
        if (cacheEngine != null) {
            cacheInformation = cacheEngine.getCacheInformation();
        }

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        context.addServlet(new ServletHolder(new CacheServlet(templateProcessor, cacheInformation)),
                "/cache");
        context.addServlet(LoginServlet.class, "/login");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();

    }

    private static void addModelsToDataBase(DBService dbService) {

        dbService.setCacheEnabled(true);

        int size = (DEFAULT_CACHE_SIZE / 2) + getRandomDelta();
        ArrayList<Long> ids = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            UserDataSet user = getRandomUser();
            dbService.save(user);
            ids.add(user.getId());
        }
        for (Long id : ids) {
            dbService.load(id);
        }
        dbService.shutdown();
    }

    private static int getRandomDelta() {
        Random r = new Random();
        int low = 10;
        int high = 100;
        return r.nextInt(high - low) + low;
    }

    private static Class[] getDBModels() {
        return new Class[]{
                UserDataSet.class,
                PhoneDataSet.class,
                AddressDataSet.class
        };
    }


    private static UserDataSet getRandomUser() {
        String randomString = UUID.randomUUID().toString();
        UserDataSet user = new UserDataSet(
                "Misha " + randomString,
                101,
                new AddressDataSet("Main Street " + randomString));

        Set<PhoneDataSet> phones = new HashSet<>(2);
        phones.add(new PhoneDataSet("12345 " + randomString, user));
        phones.add(new PhoneDataSet("678 " + randomString, user));

        user.setPhones(phones);
        return user;
    }
}
