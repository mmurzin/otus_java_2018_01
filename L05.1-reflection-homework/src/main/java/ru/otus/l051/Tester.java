package ru.otus.l051;

import ru.otus.l051.annotations.After;
import ru.otus.l051.annotations.Before;
import ru.otus.l051.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

class Tester {

    private static ArrayList<Method> beforeMethods;
    private static ArrayList<Method> testMethods;
    private static ArrayList<Method> afterMethods;

    private Tester() {
        throw new UnsupportedOperationException();
    }

    static void testClass(String className) {
        resetData();
        Class<?> clazz = getClassFromName(className);

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }

        if (beforeMethods.size() +
                testMethods.size() +
                afterMethods.size() == 0) {
            System.out.println("No annotations found in class: " + className);
            return;
        }

        try {
            for (Method testMethod : testMethods) {
                Object o = ReflectionHelper.instantiate(clazz);
                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(o);
                }
                testMethod.invoke(o);
                for (Method afterMethod : afterMethods) {
                    afterMethod.invoke(o);
                }
                System.out.println("---------------------------------");
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    static void testPackage(String packageName) throws IOException {
        String[] names =
                ReflectionHelper.getNamesByPackage(packageName);
        for (String className : names) {
            Tester.testClass(className);
        }
    }


    private static Class<?> getClassFromName(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private static void resetData() {
        beforeMethods = new ArrayList<>();
        testMethods = new ArrayList<>();
        afterMethods = new ArrayList<>();
    }
}
