package ru.otus.l081;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l081.json.IUserGson;
import ru.otus.l081.json.UserGson;
import ru.otus.l081.json.models.Person;
import ru.otus.l081.json.models.PrimitivesModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TextUserGson {

    private Gson gson;
    private IUserGson userGson;
    private String json;

    private PrimitivesModel primitivesModel;
    private Person person;
    private Person[] persons = new Person[3];
    private int[] primitives = new int[5];

    private List<Person> personList = new ArrayList<>(3);

    @Before
    public void setUp() {
        gson = new Gson();
        userGson = new UserGson();
        primitivesModel = new PrimitivesModel(1,
                "Test",
                true);
        person = new Person(52, "Joanne Rowling");

        persons[0] = new Person();
        persons[1] = person;
        persons[2] = new Person(3, "Noah");

        primitives[0] = 1;
        primitives[1] = 11;
        primitives[2] = 111;
        primitives[3] = 2;
        primitives[4] = 22;

        personList.add(person);
        personList.add(new Person(15, "Emma"));
        personList.add(new Person());
    }

    @Test
    public void checkPrimitives() {
        json = userGson.toJson(primitivesModel);
        PrimitivesModel fromJson = gson.fromJson(json, PrimitivesModel.class);
        Assert.assertEquals(primitivesModel, fromJson);
    }

    @Test
    public void checkSimpleObject() {
        json = userGson.toJson(person);
        Person fromJson = gson.fromJson(json, Person.class);
        Assert.assertEquals(person, fromJson);
    }

    @Test
    public void checkArrayOfObject() {
        json = userGson.toJson(persons);
        Person[] fromJson = gson.fromJson(json, Person[].class);
        Assert.assertArrayEquals(persons, fromJson);
    }

    @Test
    public void checkArrayOfPrimitives() {
        json = userGson.toJson(primitives);
        int[] fromJson = gson.fromJson(json, int[].class);
        Assert.assertArrayEquals(primitives, fromJson);
    }

    @Test
    public void checkListOfObjects() {
        json = userGson.toJson(personList);
        Type listType = new TypeToken<List<Person>>() {
        }.getType();
        List<Person> fromJson = gson.fromJson(json, listType);
        Assert.assertTrue(personList.equals(fromJson));
    }
}
