package ru.otus.l0101;

import ru.otus.l0101.models.AddressDataSet;
import ru.otus.l0101.models.PhoneDataSet;
import ru.otus.l0101.models.UserDataSet;

public class App {
    public static void main(String[] args) {

        Class[] models = new Class[]{
                UserDataSet.class,
                PhoneDataSet.class,
                AddressDataSet.class
        };

        DBService dbService = new DBServiceImpl(models);

        UserDataSet set1 = new UserDataSet(
                "Misha",
                101,
                new PhoneDataSet("1123456"));
        
        dbService.save(set1);
    }
}
