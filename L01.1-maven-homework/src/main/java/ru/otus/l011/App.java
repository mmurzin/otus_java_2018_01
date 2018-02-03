package ru.otus.l011;

import com.google.common.collect.Lists;


import java.util.List;

public class App {

    public static void main(String... args) {
        List<String> list = Lists.asList("Arguments:", args);
        for (String aList : list) {
            System.out.println(aList);
        }
    }
}
