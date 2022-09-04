package com.investingsimulator.portfolio;

public record PortfolioResponse (int id, String name) {
    public PortfolioResponse(Portfolio portfolio) {
        this(portfolio.getId(), portfolio.getName());
    }
}
