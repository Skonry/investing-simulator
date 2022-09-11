package com.investingsimulator.portfolio;

import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import com.investingsimulator.common.Percentage;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepository;

    private InstrumentRepository instrumentRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, InstrumentRepository instrumentRepository) {
        this.portfolioRepository = portfolioRepository;
        this.instrumentRepository = instrumentRepository;
    }

    public List<Portfolio> getPortfolios() {
        return portfolioRepository.findAll();
    }

    public Portfolio getPortfolio(int id) {
        return portfolioRepository.findById(id).orElseThrow();
    }

    public PortfolioResult getPortfolioResult(int id, LocalDate startDate, LocalDate endDate) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow();

        return portfolio.calculateResult(startDate, endDate);
    }

    public Portfolio createPortfolio(PortfolioCreationRequest portfolioCreationRequest) {
        Portfolio portfolio = new Portfolio(
                portfolioCreationRequest.name(),
                new Money(portfolioCreationRequest.deposit(), Currency.USD)
        );

        return portfolioRepository.save(portfolio);
    }
}
