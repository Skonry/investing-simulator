package com.investingsimulator.portfolio;

import com.investingsimulator.common.Percentage;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PortfolioService {

    private PortfolioRepository portfolioRepository;

    private InstrumentRepository instrumentRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, InstrumentRepository instrumentRepository) {
        this.portfolioRepository = portfolioRepository;
        this.instrumentRepository = instrumentRepository;
    }

    public Portfolio getPortfolio(int id) {
        return portfolioRepository.findById(id).orElseThrow();
    }

    public void addInstrument(int portfolioId, AddInstrumentRequest addInstrumentRequest) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        Instrument instrument = instrumentRepository.findById(addInstrumentRequest.instrumentId).orElseThrow();

        portfolio.addInstrument(instrument, new Percentage(addInstrumentRequest.percentage));
    }
}
