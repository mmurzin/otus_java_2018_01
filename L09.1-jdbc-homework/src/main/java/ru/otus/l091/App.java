package ru.otus.l091;

import ru.otus.l091.interfaces.DBService;
import ru.otus.l091.interfaces.DataSetExecutor;
import ru.otus.l091.models.UserDataSet;


public class App {
    public static void main(String[] args) {
        DataSetExecutor service = DBServiceImpl.getInstance();
        UserDataSet userDataSet = new UserDataSet(13,"Alex",13);
        service.save(userDataSet);
    }
}
