package com.investingsimulator.portfolio;

import com.investingsimulator.instrument.Instrument;

import javax.persistence.*;

@Entity
@Table(name = "portfolio_instruments")
public class PortfolioInstrument {
    @Id
    private int id;

    private double percentage;

    @OneToOne
    private Instrument instrument;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public PortfolioInstrument(double percentage, Instrument instrument) {
        this.percentage = percentage;
        this.instrument = instrument;
    }

    public PortfolioInstrument() {}

    public int getId() { return id; }

    public double getPercentage() {
        return percentage;
    }

    public Instrument getInstrument() { return instrument; }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }

    public void setInstrument(Instrument instrument) { this.instrument = instrument; }
}
