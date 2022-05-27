package com.investingsimulator.portfolio;

import com.investingsimulator.common.Money;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PortfolioResult {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Money deposit;
    private final Money expectedResult;
    private final double returnOfInvestment;
    private final double rateOfReturn;

    public PortfolioResult(LocalDate startDate, LocalDate endDate, Money deposit, Money expectedResult) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.expectedResult = expectedResult;
        this.returnOfInvestment = deposit.toDouble() / expectedResult.toDouble() * 100;
        this.rateOfReturn = this.returnOfInvestment / ChronoUnit.YEARS.between(startDate, endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Money getDeposit() {
        return deposit;
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
