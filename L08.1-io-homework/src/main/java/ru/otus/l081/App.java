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
    private static Gson gson;
    private static IUserGson userGson;

    static {
        gson = new Gson();
        userGson = new UserGson();
    }

    public static void main(String[] args) {
        printJson(123.6);
        printJson(12);
        printJson("String value");
        printJson(true);
        printJson(new Person());
        printJson(new PrimitivesModel());


        List<String> list = new ArrayList<>(1);
        list.add("Michael");
        Map<String, Integer> map = new HashMap<>();
        map.put("Abc", 1);
        CollectionsModels collectionsModels = new CollectionsModels(list, map);
        printJson(collectionsModels);

        Person rowling = new Person(52, "Joanne Rowling");
        List<Person> personList = new ArrayList<>(1);
        personList.add(rowling);
        Map<Integer, Person> personMap = new HashMap<>(1);
        personMap.put(rowling.getAge(), rowling);
        collectionsModels.setMapPersons(personMap);
        collectionsModels.setListPersons(personList);
        printJson(collectionsModels);
    }

    private static void printJson(Object object) {
        System.out.println("Gson output " + gson.toJson(object));
        System.out.println("UserGson output " + userGson.toJson(object));
        System.out.println("------------////------------");
    }
}
