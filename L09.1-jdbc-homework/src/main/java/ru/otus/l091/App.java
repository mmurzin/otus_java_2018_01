package ru.otus.l091;

import ru.otus.l091.interfaces.DBService;
import ru.otus.l091.models.UserDataSet;


public class App {
    public static void main(String[] args) throws Exception {
        DBService service = DBServiceImpl.getInstance();
        service.prepareTables();
        UserDataSet userDataSet = new UserDataSet("Alex", 13);
        //INSERT
        service.save(userDataSet);
        userDataSet.setAge(45);
        userDataSet.setName("AlexNew");
        //UPDATE
        service.save(userDataSet);
        System.out.println("--->My model \n" + userDataSet.toString());
        //SELECT
        UserDataSet userDataSetDb = service.load(userDataSet.getId(), UserDataSet.class);
        System.out.println("--->Model from DB \n" + userDataSetDb.toString());
        System.out.println("--->My model and model from DB equals? " +
                String.valueOf(userDataSet.equals(userDataSetDb)));
        service.deleteTables();
    }
}
