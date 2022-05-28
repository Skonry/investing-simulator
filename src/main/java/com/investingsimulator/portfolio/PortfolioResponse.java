package com.investingsimulator.portfolio;

import java.time.LocalDate;

public class PortfolioResponse {
    public int id;

    public String name;

    public PortfolioResult portfolioResult;

    public PortfolioResponse(Portfolio portfolio) {
        this.id = portfolio.getId();
        this.name = portfolio.getName();
        this.portfolioResult = portfolio.calculateResult(
                LocalDate.now().minusYears(1),
                LocalDate.now()
        );
    }
}
