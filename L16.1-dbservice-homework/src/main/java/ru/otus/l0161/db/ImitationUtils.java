package ru.otus.l0161.db;




import ru.otus.l0161.models.AddressDataSet;
import ru.otus.l0161.models.PhoneDataSet;
import ru.otus.l0161.models.UserDataSet;

import java.util.*;

import static ru.otus.l0161.cache.CacheEngineImpl.DEFAULT_CACHE_SIZE;


public class ImitationUtils {

    public static void imitateActions(DBService dbService) {
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
