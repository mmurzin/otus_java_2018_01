package ru.otus.l0101.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "hibernate_user_streets")
public class AddressDataSet extends DataSet {
    @Column(name = "street")
    private String street;
}
