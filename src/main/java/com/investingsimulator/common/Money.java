package com.investingsimulator.common;

import javax.persistence.*;

@Embeddable
public class Money {
    private double amount;
    private Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money() {}

    public double toDouble() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
