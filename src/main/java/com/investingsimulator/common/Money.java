package com.investingsimulator.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name =  "moneys")
public class Money {

    @Id
    private int id;
    private double value;
    private Currency currency;

    public Money(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public double toDouble() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }
}
