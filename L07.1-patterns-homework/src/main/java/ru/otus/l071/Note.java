package ru.otus.l071;


import java.util.Set;
import java.util.TreeSet;

public class Note {

    public enum NominalEnum {
        NOMINAL_100(100),
        NOMINAL_200(200),
        NOMINAL_500(500),
        NOMINAL_1000(1000),
        NOMINAL_2000(2000),
        NOMINAL_5000(5000);

        private final int nominal;

        NominalEnum(int nominal) {
            this.nominal = nominal;
        }

        public int getNominal() {
            return nominal;
        }
    }

    private static Set<Integer> AVAILABLE_NOMINALS = new TreeSet<>();
    private int nominal;

    static {
        for (NominalEnum nominalEnum : NominalEnum.values()) {
            AVAILABLE_NOMINALS.add(nominalEnum.getNominal());
        }
    }

    public static Integer[] getAvialableNominals() {
        return AVAILABLE_NOMINALS.toArray(new Integer[AVAILABLE_NOMINALS.size()]);
    }

    public Note(int nominal) {
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
