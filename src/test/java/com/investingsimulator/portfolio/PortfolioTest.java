package com.investingsimulator.portfolio;

import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentResult;
import com.investingsimulator.instrument.PriceRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        final Portfolio portfolio = new Portfolio("Portfolio Name", new Money(100, Currency.USD));

        new PortfolioInstrument(portfolio, instrument1, 50);
        new PortfolioInstrument(portfolio, instrument2, 50);

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

        final Portfolio portfolio = new Portfolio("Portfolio Name", new Money(100, Currency.USD));

        new PortfolioInstrument(portfolio, instrument1, 20);
        new PortfolioInstrument(portfolio, instrument2, 80);

        PortfolioResult portfolioResult = portfolio.calculateResult(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1)
        );

        assertEquals(220, portfolioResult.returnOnInvestment());
        assertEquals(110, portfolioResult.rateOfReturn());
    }

    @Test
    void throwExceptionIfNotFoundPriceRecord() {
        final Portfolio portfolio = new Portfolio("Portfolio Name", new Money(100, Currency.USD));

        assertThrows(
                NoSuchElementException.class,
                () -> {
                    portfolio.calculateResult(
                            LocalDate.of(1900, 1, 1),
                            LocalDate.of(2100, 1, 1)
                    );
                }
        );
    }
}
