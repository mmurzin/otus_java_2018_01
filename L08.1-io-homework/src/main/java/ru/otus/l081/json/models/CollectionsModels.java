package ru.otus.l081.json.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsModels extends PrimitivesModel {
    private final List<String> listStringValue;
    private final Map<String, Integer> mapStringValue;
    private List<Person> listPersons;
    private Map<Integer, Person> mapPersons;

    public CollectionsModels(List<String> listStringValue,
                             Map<String, Integer> mapStringValue) {
        super();
        this.listStringValue = listStringValue;
        this.mapStringValue = mapStringValue;
    }

    public CollectionsModels(int valueInt,
                             String valueString,
                             boolean valueBoolean,
                             List<String> listStringValue,
                             Map<String, Integer> mapStringValue) {
        super(valueInt, valueString, valueBoolean);
        this.listStringValue = listStringValue;
        this.mapStringValue = mapStringValue;
    }

    public void setListPersons(List<Person> listPersons) {
        this.listPersons = listPersons;
    }

    public void setMapPersons(Map<Integer, Person> mapPersons) {
        this.mapPersons = mapPersons;
    }
}
