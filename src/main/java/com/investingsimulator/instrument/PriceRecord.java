package com.investingsimulator.instrument;

import com.investingsimulator.common.Money;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "price_records")
public class PriceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Money amount;
    private LocalDate date;

    public PriceRecord(Money amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public PriceRecord() {}

    public Money getValue() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
