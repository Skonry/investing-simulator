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

    @Convert(converter = PercentageAttributeConverter.class)
    private Percentage percentage;

    public PortfolioInstrument(Instrument instrument, Percentage percentage) {
        this.instrument = instrument;
        this.percentage = percentage;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Percentage getPercentage() {
        return percentage;
    }

    public void setPercentage(Percentage percentage) {
        this.percentage = percentage;
    }
}
