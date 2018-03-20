package ru.otus.l061;


import java.util.Set;
import java.util.TreeSet;

public class Bill {
    public static int NOMINAL_100 = 100;
    public static int NOMINAL_200 = 200;
    public static int NOMINAL_500 = 500;
    public static int NOMINAL_1000 = 1000;
    public static int NOMINAL_2000 = 2000;
    public static int NOMINAL_5000 = 5000;

    private static Set<Integer> AVAILABLE_NOMINALS = new TreeSet<>();
    private int nominal;

    static {
        AVAILABLE_NOMINALS.add(NOMINAL_100);
        AVAILABLE_NOMINALS.add(NOMINAL_200);
        AVAILABLE_NOMINALS.add(NOMINAL_500);
        AVAILABLE_NOMINALS.add(NOMINAL_1000);
        AVAILABLE_NOMINALS.add(NOMINAL_2000);
        AVAILABLE_NOMINALS.add(NOMINAL_5000);
    }

    Bill(int nominal) {
        if (isValidNominal(nominal)) {
            this.nominal = nominal;
        } else {
            throw new IllegalArgumentException("Not valid nominal "
                    + String.valueOf(nominal));
        }
    }

    private boolean isValidNominal(int nominal) {
        if (nominal <= 0) {
            return false;
        }
        for (int currentNominal : AVAILABLE_NOMINALS) {
            if (currentNominal == nominal) {
                return true;
            }
        }
        return false;
    }

    public int getNominal() {
        return nominal;
    }
}
