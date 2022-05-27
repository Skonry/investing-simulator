package com.investingsimulator.instrument;

import com.investingsimulator.common.Money;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "price_records")
public class PriceRecord {

    @Id
    private int id;

    @OneToOne
    private Money value;
    private LocalDate date;

    public PriceRecord(Money value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    public Money getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }
}
