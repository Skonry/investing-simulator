package com.investingsimulator.instrument;

import com.investingsimulator.common.Money;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InstrumentResult {
    private LocalDate startDate;
    private LocalDate endDate;
    private Money expectedResult;
    private double returnOfInvestment;
    private double rateOfReturn;

    public InstrumentResult(LocalDate startDate, LocalDate endDate, Money expectedResult) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.expectedResult = expectedResult;
        this.rateOfReturn = this.expectedResult.toDouble() / ChronoUnit.YEARS.between(startDate, endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Money getExpectedResult() {
        return expectedResult;
    }

    public double getReturnOfInvestment() {
        return returnOfInvestment;
    }

    public double getRateOfReturn() {
        return rateOfReturn;
    }
}
