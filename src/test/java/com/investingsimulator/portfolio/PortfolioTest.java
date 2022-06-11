package com.investingsimulator.portfolio;

import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import com.investingsimulator.common.Percentage;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.PriceRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortfolioTest {
    private final Instrument instrument1 = new Instrument(
            "InstrumentName1",
            "Index1",
            "Issuer1",
            LocalDate.of(2020, 1, 1),
            List.of(
                    new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                    new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2021, 1, 1))
            )
    );

    private final Instrument instrument2 = new Instrument(
            "InstrumentName2",
            "Index2",
            "Issuer2",
            LocalDate.of(2020, 1, 1),
            List.of(
                    new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                    new PriceRecord(new Money(100, Currency.USD), LocalDate.of(2021, 1, 1))
            )
    );

    @Test
    void addInstrument() {
        final Portfolio portfolio = new Portfolio("PortfolioName", new Money(1000, Currency.USD));

        assertEquals(portfolio.getPortfolioInstruments().size(), 0);

        portfolio.addInstrument(instrument1, new Percentage(100));

        assertEquals(portfolio.getPortfolioInstruments().size(), 1);
    }

    @Test
    void removeInstrument() {
        final Portfolio portfolio = new Portfolio("PortfolioName", new Money(1000, Currency.USD));

        portfolio.addInstrument(instrument1, new Percentage(100));

        assertEquals(portfolio.getPortfolioInstruments().size(), 1);

        PortfolioInstrument portfolioInstrument = portfolio.getPortfolioInstruments().get(0);

        portfolio.removeInstrument(portfolioInstrument);

        assertEquals(portfolio.getPortfolioInstruments().size(), 0);
    }

    @Test
    void properlyCalculateResultForOneInstrument() {
        final Portfolio portfolio = new Portfolio("PortfolioName", new Money(1000, Currency.USD));

        portfolio.addInstrument(instrument1, new Percentage(100));

        PortfolioResult portfolioResult = portfolio.calculateResult(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertEquals(portfolioResult.getExpectedResult().toDouble(), 2000);
    }

    @Test
    void properlyCalculateResultForManyInstruments() {
        final Portfolio portfolio = new Portfolio("PortfolioName", new Money(1000, Currency.USD));

        portfolio.addInstrument(instrument1, new Percentage(50));
        portfolio.addInstrument(instrument2, new Percentage(50));

        PortfolioResult portfolioResult = portfolio.calculateResult(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertEquals(portfolioResult.getExpectedResult().toDouble(), 6000);
    }
}