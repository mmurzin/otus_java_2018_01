package ru.otus.l021;

import java.lang.instrument.Instrumentation;

public class InstrumentationApp {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    static void printObjectSize(Object o) {
        if (instrumentation != null) {
            System.out.println(o.getClass().getSimpleName() + instrumentation.getObjectSize(o) + " bytes");
        }
    }
}
