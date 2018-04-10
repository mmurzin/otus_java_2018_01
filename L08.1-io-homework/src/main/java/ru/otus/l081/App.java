package ru.otus.l081;

import com.google.gson.Gson;
import ru.otus.l081.json.IUserGson;
import ru.otus.l081.json.UserGson;
import ru.otus.l081.json.models.CollectionsModels;
import ru.otus.l081.json.models.Person;
import ru.otus.l081.json.models.PrimitivesModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        Gson gson = new Gson();
        IUserGson userGson = new UserGson();

        //PrimitiveModels
        PrimitivesModel primitivesModel = new PrimitivesModel(100,
                "Hi",
                false);

        String gsonModel = gson.toJson(primitivesModel);
        String userModel = gson.toJson(primitivesModel);

        System.out.println("Gson model " + gsonModel);
        System.out.println("User json model " + userModel);

        //CollectionsModel

        Map<String, Integer> dummyMap = new HashMap<>(2);
        dummyMap.put("Hello", 11);
        dummyMap.put("World", 12);

        List<String> dummyList = new ArrayList<>(2);
        dummyList.add("Hello");
        dummyList.add("World");

        CollectionsModels collectionsModels = new CollectionsModels(dummyList, dummyMap);
        gsonModel = gson.toJson(collectionsModels);
        userModel = userGson.toJson(collectionsModels);

        System.out.println("Gson model " + gsonModel);
        System.out.println("User json model " + userModel);

        Person person1 = new Person();
        Person person2 = new Person(23,"Emma");
        Person person3 = new Person(34, "Olivia");
        Person person4 = new Person(58, "William");

        List<Person> personList = new ArrayList<>(4);
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        Map<Integer,Person> integerPersonMap = new HashMap<>(4);
        integerPersonMap.put(person1.getAge(),person1);
        integerPersonMap.put(person2.getAge(),person2);
        integerPersonMap.put(person3.getAge(),person3);
        integerPersonMap.put(person4.getAge(),person4);

        collectionsModels.setListPersons(personList);
        collectionsModels.setMapPersons(integerPersonMap);

        gsonModel = gson.toJson(collectionsModels);
        userModel = userGson.toJson(collectionsModels);

        System.out.println("Gson model " + gsonModel);
        System.out.println("User json model " + userModel);


    }
}
