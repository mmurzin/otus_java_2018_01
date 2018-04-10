package ru.otus.l081.json.models;

public class PrimitivesModel {

    private final int valueInt;
    private final String valueString;
    private final boolean valueBoolean;

    public PrimitivesModel() {
        valueInt = 12;
        valueString = "Hello World";
        valueBoolean = true;
    }

    public PrimitivesModel(int valueInt,
                           String valueString,
                           boolean valueBoolean) {
        this.valueInt = valueInt;
        this.valueString = valueString;
        this.valueBoolean = valueBoolean;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        PrimitivesModel model = (PrimitivesModel) obj;

        if (valueInt != model.valueInt) {
            return false;
        }

        if (valueString != null && !valueString.equals(model.valueString)) {
            return false;
        }

        if (valueString == null && model.valueString != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " \n" +
                "valueInt: " + String.valueOf(this.valueInt) + "\n" +
                "valueString: " + this.valueString;
    }
}
