package ru.otus.l0101.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hibernate_users")
public class UserDataSet extends DataSet {

    @Column(name = "age")
    private int age;

    @Column(name = "name")
    private String name;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        super.setId(-1);
        this.name = name;
        this.age = age;
    }
}
