package com.investingsimulator.portfolio;

import java.util.List;

public record PortfolioResponse (int id, String name, double deposit, List<PortfolioInstrument> portfolioInstruments) {
    public PortfolioResponse(Portfolio portfolio) {
        this(portfolio.getId(), portfolio.getName(), portfolio.getDeposit().toDouble(), portfolio.getPortfolioInstruments());
    }
}
