package ru.otus.l0101;

import ru.otus.l0101.models.UserDataSet;

public class App {
    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl(UserDataSet.class);
        UserDataSet set1 = new UserDataSet("Misha",101);
        dbService.save(set1);
    }
}
