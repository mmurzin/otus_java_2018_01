package ru.otus.l011;


import org.apache.commons.collections4.ListUtils;


import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);
        List<String> firstList = Arrays.asList("Command Line","Arguments");
        List<String> finalArguments = ListUtils.union(firstList,arguments);
        for (String current:finalArguments){
            System.out.println(current);
        }
    }
}
