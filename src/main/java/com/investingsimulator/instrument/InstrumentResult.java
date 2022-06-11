package com.investingsimulator.instrument;

import com.investingsimulator.common.Money;
import com.investingsimulator.common.Percentage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InstrumentResult {

    private LocalDate startDate;

    private LocalDate endDate;

    private Percentage expectedResult;

    private Percentage rateOfReturn;

    public InstrumentResult(LocalDate startDate, LocalDate endDate, Percentage expectedResult) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedResult = expectedResult;
        this.rateOfReturn = new Percentage(
                this.expectedResult.getValue() / ChronoUnit.YEARS.between(startDate, endDate)
        );
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Percentage getExpectedResult() {
        return expectedResult;
    }

    public Percentage getRateOfReturn() {
        return rateOfReturn;
    }
}
