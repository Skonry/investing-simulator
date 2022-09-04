package com.investingsimulator.portfolio;

import java.util.List;

public record PortfolioResult (
        Portfolio portfolio,
        List<PortfolioInstrumentResult> portfolioInstrumentResults,
        double returnOnInvestment,
        double rateOfReturn
) { }
