package ru.otus.l081.json.models;

public class Person {
    private final Point point;
    private int age;
    private String name;

    public Person() {
        this.age = 12;
        this.name = "Alex";
        this.point = new Point();
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
        this.point = new Point();
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Person that = (Person) obj;
        if ((this.name == null && that.name != null) ||
                (this.name != null && that.name == null)) {
            return false;
        }
        if (that.age != this.age) {
            return false;
        }

        if (!that.point.equals(this.point)) {
            return false;
        }
        if (!this.point.equals(that.point)) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return "Name " + this.name + " Age " + String.valueOf(age) + " Point " + point.toString();
    }
}
