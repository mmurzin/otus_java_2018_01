package ru.otus.l091.models;

public class UserDataSet extends DataSet {

    private int age;
    private String name;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDataSet(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserDataSet that = (UserDataSet) obj;
        if ((this.name == null && that.name != null) ||
                (this.name != null && that.name == null)) {
            return false;
        }
        if (that.age != this.age) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id " + String.valueOf(this.id) + "\n" +
                "Age " + String.valueOf(this.age) + "\n" +
                "Name " + this.name;
    }
}
