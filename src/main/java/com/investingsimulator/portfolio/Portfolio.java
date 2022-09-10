package com.investingsimulator.portfolio;

import com.investingsimulator.common.Money;
import com.investingsimulator.common.Percentage;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentResult;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Table(name = "portfolios")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;

    private String name;

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PortfolioInstrument> portfolioInstruments = new ArrayList<>();

    private Money deposit;

    public Portfolio(String name, Money deposit) {
        this.name = name;
        this.deposit = deposit;
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

    public List<PortfolioInstrument> getPortfolioInstruments() { return portfolioInstruments; }

    public void setDeposit(Money deposit) {
        this.deposit = deposit;
    }

    public void addInstrument(Instrument instrument, double percentage) {
        PortfolioInstrument portfolioInstrument = new PortfolioInstrument(percentage, instrument);

        portfolioInstruments.add(portfolioInstrument);

        portfolioInstrument.setPortfolio(this);
    }

    public void removeInstrument(int id) {
        PortfolioInstrument portfolioInstrument = portfolioInstruments.stream()
                .filter(element -> element.getId() ==id)
                .findAny()
                .orElseThrow();

        portfolioInstruments.remove(portfolioInstrument);

        portfolioInstrument.setPortfolio(null);
        portfolioInstrument.setInstrument(null);
    }

    public PortfolioResult calculateResult(LocalDate startDate, LocalDate endDate) {
        List<PortfolioInstrumentResult> portfolioInstrumentResults = portfolioInstruments
                .stream()
                .map((portfolioInstrument -> {
                    InstrumentResult instrumentResult = portfolioInstrument
                            .getInstrument()
                            .calculateResult(startDate, endDate);

                    return new PortfolioInstrumentResult(
                            instrumentResult.returnOnInvestment(),
                            instrumentResult.rateOfReturn(),
                            portfolioInstrument.getPercentage()
                    );
                }))
                .toList();



        double returnOnInvestment = portfolioInstrumentResults
                .stream()
                .reduce(0.0, (acc, element) -> {

                    double resultModifiedByDepositFraction =
                            (element.returnOnInvestment() / 100 + 1) * deposit.toDouble() * (element.percentage() / 100);

                    return acc + resultModifiedByDepositFraction;
                }, Double::sum);

        double rateOfReturn =  returnOnInvestment / ChronoUnit.YEARS.between(startDate, endDate);

        return new PortfolioResult(this, portfolioInstrumentResults, returnOnInvestment, rateOfReturn);
    }
}
