package com.investingsimulator.portfolio;

import com.investingsimulator.instrument.InstrumentResult;

import java.time.LocalDate;

public record PortfolioResultResponse(int id, String name, double returnOnInvestment, double rateOfReturn) {
    public PortfolioResultResponse(PortfolioResult portfolioResult) {
        this(
                portfolioResult.portfolio().getId(),
                portfolioResult.portfolio().getName(),
                portfolioResult.returnOnInvestment(),
                portfolioResult.rateOfReturn()
        );
    }
}

