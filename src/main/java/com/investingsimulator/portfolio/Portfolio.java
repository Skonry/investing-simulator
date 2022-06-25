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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;

    private String name;

    @OneToMany
    private List<PortfolioInstrument> portfolioInstruments;

    private Money deposit;

    public Portfolio(String name, Money deposit) {
        this.name = name;
        this.deposit = deposit;
        this.portfolioInstruments = new ArrayList<>();
    }

    public Portfolio() {}

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
            InstrumentResult instrumentResult = element.getInstrument().calculateResult(startDate, endDate);

            double resultModifiedByDepositFraction = element.getPercentage().getNormalized() * deposit.toDouble();

            return acc + resultModifiedByDepositFraction;
        }, Double::sum);

        Money expectedResult = new Money(value, deposit.getCurrency());

        return new PortfolioResult(startDate, endDate, deposit, expectedResult);
    }
}
