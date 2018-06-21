package ru.otus.l0151.models;

import javax.persistence.*;

@Entity
@Table(name = "hibernate_phones")
public class PhoneDataSet extends DataSet {


    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number, UserDataSet user) {
        this.number = number;
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
