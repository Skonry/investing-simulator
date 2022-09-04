package com.investingsimulator.portfolio;

import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

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

        PortfolioInstrument portfolioInstrument = new PortfolioInstrument(
                portfolio,
                instrument,
                addInstrumentRequest.percentage()
        );

        portfolioInstrumentRepository.save(portfolioInstrument);
    }

    public void removeInstrument(int id) {
        portfolioInstrumentRepository.deleteById(id);
    }
}
