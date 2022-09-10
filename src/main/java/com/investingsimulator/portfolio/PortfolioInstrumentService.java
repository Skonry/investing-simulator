package com.investingsimulator.portfolio;

import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioInstrumentService {
    private final PortfolioRepository portfolioRepository;
    private final InstrumentRepository instrumentRepository;
    private final PortfolioInstrumentRepository portfolioInstrumentRepository;

    @Autowired
    public PortfolioInstrumentService(
            PortfolioRepository portfolioRepository,
            InstrumentRepository instrumentRepository,
            PortfolioInstrumentRepository portfolioInstrumentRepository
    ) {
        this.portfolioRepository = portfolioRepository;
        this.instrumentRepository = instrumentRepository;
        this.portfolioInstrumentRepository = portfolioInstrumentRepository;
    }

    public void addInstrument(int portfolioId, AddInstrumentRequest addInstrumentRequest) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        Instrument instrument = instrumentRepository.findById(addInstrumentRequest.instrumentId()).orElseThrow();

        portfolio.addInstrument(instrument, addInstrumentRequest.percentage());

        portfolioRepository.save(portfolio);
    }

    public void removeInstrument(int portfolioId, int id) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow();

        portfolio.removeInstrument(id);

        portfolioRepository.save(portfolio);
    }
}
