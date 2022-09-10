package com.investingsimulator.portfolio;

import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.PriceRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortfolioTest {
    @Test
    void properlyCalculateResultForEqualInstrumentProportions() {
        final Instrument instrument1 = new Instrument(
                "InstrumentName1",
                "IndexName1",
                "Issuer",
                LocalDate.of(2020, 1, 1),
                List.of(
                        new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                        new PriceRecord(new Money(15, Currency.USD), LocalDate.of(2021, 1, 1)),
                        new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2022, 1, 1))
                )
        );

        final Instrument instrument2 = new Instrument(
                "InstrumentName2",
                "IndexName2",
                "Issuer",
                LocalDate.of(2020, 1, 1),
                List.of(
                        new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                        new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2021, 1, 1)),
                        new PriceRecord(new Money(30, Currency.USD), LocalDate.of(2022, 1, 1))
                )
        );

        final Portfolio portfolio = new Portfolio(
                "Portfolio Name",
                new Money(100, Currency.USD)
        );

        portfolio.addInstrument(instrument1, 50);
        portfolio.addInstrument(instrument2, 50);

        PortfolioResult portfolioResult = portfolio.calculateResult(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1)
        );

        assertEquals(250, portfolioResult.returnOnInvestment());
        assertEquals(125, portfolioResult.rateOfReturn());
    }

    @Test
    void properlyCalculateResultForDifferentInstrumentProportions() {
        final Instrument instrument1 = new Instrument(
                "InstrumentName1",
                "IndexName1",
                "Issuer",
                LocalDate.of(2020, 1, 1),
                List.of(
                        new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                        new PriceRecord(new Money(15, Currency.USD), LocalDate.of(2021, 1, 1)),
                        new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2022, 1, 1))
                )
        );

        final Instrument instrument2 = new Instrument(
                "InstrumentName2",
                "IndexName2",
                "Issuer",
                LocalDate.of(2020, 1, 1),
                List.of(
                        new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                        new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2021, 1, 1)),
                        new PriceRecord(new Money(30, Currency.USD), LocalDate.of(2022, 1, 1))
                )
        );

        final Portfolio portfolio = new Portfolio(
                "Portfolio Name",
                new Money(100, Currency.USD)
        );

        portfolio.addInstrument(instrument1, 20);
        portfolio.addInstrument(instrument2, 80);

        PortfolioResult portfolioResult = portfolio.calculateResult(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1)
        );

        assertEquals(280, portfolioResult.returnOnInvestment());
        assertEquals(140, portfolioResult.rateOfReturn());
    }
}