package com.investingsimulator.portfolio;

import com.investingsimulator.common.Money;
import com.investingsimulator.common.Percentage;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentResult;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    private  int id;

    private String name;

    @OneToMany
    private final List<PortfolioInstrument> portfolioInstruments;

    @OneToOne
    private Money deposit;

    public Portfolio(String name, Money deposit) {
        this.name = name;
        this.deposit = deposit;
        this.portfolioInstruments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getDeposit() {
        return deposit;
    }

    public List<PortfolioInstrument> getPortfolioInstruments() {
        return portfolioInstruments;
    }

    public void setDeposit(Money deposit) {
        this.deposit = deposit;
    }

    public void addInstrument(Instrument instrument, Percentage percentage) {
        PortfolioInstrument portfolioInstrument = new PortfolioInstrument(instrument, percentage);

        portfolioInstruments.add(portfolioInstrument);
    }

    public void removeInstrument(PortfolioInstrument portfolioInstrument) {
        portfolioInstruments.remove(portfolioInstrument);
    }

    public PortfolioResult calculateResult(LocalDate startDate, LocalDate endDate) {
        double value = portfolioInstruments.stream().reduce(0.0, (acc, element) -> {
            Money depositFraction = new Money(
                    element.getPercentage().getNormalized() * deposit.toDouble(),
                    deposit.getCurrency()
            );

            InstrumentResult instrumentResult = element.getInstrument().calculateResult(startDate, endDate);

            return acc + instrumentResult.getExpectedResult().toDouble();
        }, Double::sum);

        Money expectedResult = new Money(value, deposit.getCurrency());

        return new PortfolioResult(startDate, endDate, deposit, expectedResult);
    }
}
