package com.investingsimulator.portfolio;


import com.investingsimulator.common.Percentage;
import com.investingsimulator.common.PercentageAttributeConverter;
import com.investingsimulator.instrument.Instrument;

import javax.persistence.*;

@Entity
@Table(name = "portfolio_instruments")
public class PortfolioInstrument {

    @Id
    private int id;

    @OneToOne
    private final Instrument instrument;

    @OneToOne
    private  final  Portfolio portfolio;

    private double percentage;

    public PortfolioInstrument(Portfolio portfolio, Instrument instrument, double percentage) {
        this.portfolio = portfolio;
        this.instrument = instrument;
        this.percentage = percentage;
    }

    public int getId() { return id; }

    public Instrument getInstrument() {
        return instrument;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
