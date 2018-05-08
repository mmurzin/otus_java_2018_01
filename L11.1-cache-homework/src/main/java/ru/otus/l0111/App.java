package ru.otus.l0111;

import ru.otus.l0111.db.DBService;
import ru.otus.l0111.db.DBServiceImpl;
import ru.otus.l0111.models.AddressDataSet;
import ru.otus.l0111.models.PhoneDataSet;
import ru.otus.l0111.models.UserDataSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static ru.otus.l0111.cache.CacheEngineImpl.DEFAULT_CACHE_SIZE;

public class App {
    public static void main(String[] args) {

        Class[] models = new Class[]{
                UserDataSet.class,
                PhoneDataSet.class,
                AddressDataSet.class
        };

        DBService dbService = new DBServiceImpl(models);

        dbService.setCacheEnabled(true);

        int size = DEFAULT_CACHE_SIZE + 10;
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
