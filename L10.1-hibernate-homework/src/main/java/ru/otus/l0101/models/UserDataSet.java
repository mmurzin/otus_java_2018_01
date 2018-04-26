package ru.otus.l0101.models;


import javax.persistence.*;

@Entity
@Table(name = "hibernate_users")
public class UserDataSet extends DataSet {

    @Column(name = "age")
    private int age;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private PhoneDataSet phoneDataSet;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age, PhoneDataSet phoneDataSet) {
        super.setId(-1);
        this.name = name;
        this.age = age;
        this.phoneDataSet = phoneDataSet;
    }
}
