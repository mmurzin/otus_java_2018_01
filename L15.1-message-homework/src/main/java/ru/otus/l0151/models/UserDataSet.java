package ru.otus.l0151.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hibernate_users")
public class UserDataSet extends DataSet {

    @Column(name = "age")
    private int age;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet addressDataSet;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PhoneDataSet> phones = new HashSet<>();

    public UserDataSet() {
    }

    public UserDataSet(String name, int age,
                       AddressDataSet addressDataSet) {
        super.setId(-1);
        this.name = name;
        this.age = age;
        this.addressDataSet = addressDataSet;
    }


    public Set<PhoneDataSet> getPhoneSet() {
        return this.phones;
    }

    @Override
    public String toString() {
        String address = "null";
        if (addressDataSet != null) {
            address = addressDataSet.toString();
        }
        return "Name " + name +
                " Age " + String.valueOf(age) +
                " Address " + address +
                " Phones " + getPhonesToString();
    }

    private String getPhonesToString() {
        StringBuilder builder = new StringBuilder();
        if (phones != null && phones.size() > 0) {
            for (PhoneDataSet current : phones) {
                builder.append(current.toString());
            }
        } else {
            builder.append("null");
        }
        return builder.toString();
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }
}
