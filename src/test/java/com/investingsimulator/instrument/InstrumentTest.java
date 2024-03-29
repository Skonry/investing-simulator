package com.investingsimulator.instrument;

import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstrumentTest {

    private final List<PriceRecord> priceRecords = List.of(
            new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
            new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2021, 1, 1)),
            new PriceRecord(new Money(30, Currency.USD), LocalDate.of(2022, 1, 1))
    );

    private final Instrument instrument = new Instrument(
            "InstrumentName",
            "IndexName",
            "Issuer",
            LocalDate.of(2020, 1, 1),
            priceRecords
    );

    @Test
    void properlyCalculateResult() {
        InstrumentResult instrumentResult = instrument.calculateResult(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1)
        );

        assertEquals(200, instrumentResult.returnOnInvestment());
        assertEquals(100, instrumentResult.rateOfReturn());
    }

    @Test
    void throwExceptionIfNotFoundPriceRecord() {
        assertThrows(
                NoSuchElementException.class,
                () -> {
                    instrument.calculateResult(
                            LocalDate.of(1900, 1, 1),
                            LocalDate.of(2100, 1, 1)
                    );
                }
        );
    }
}
