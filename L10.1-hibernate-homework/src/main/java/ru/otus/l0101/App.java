package ru.otus.l0101;

import ru.otus.l0101.models.AddressDataSet;
import ru.otus.l0101.models.PhoneDataSet;
import ru.otus.l0101.models.UserDataSet;

import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {

        Class[] models = new Class[]{
                UserDataSet.class,
                PhoneDataSet.class,
                AddressDataSet.class
        };

        DBService dbService = new DBServiceImpl(models);

        UserDataSet user = new UserDataSet(
                "Misha",
                101,
                new AddressDataSet("Main Street"));

        Set<PhoneDataSet> phones = new HashSet<>(2);
        phones.add(new PhoneDataSet("12345", user));
        phones.add(new PhoneDataSet("678", user));

        user.setPhones(phones);

        dbService.save(user);
        long id = user.getId();
        UserDataSet userDataSet = dbService.load(id);
        System.out.println(userDataSet.toString());
        dbService.shutdown();
    }
}
