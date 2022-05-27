package com.investingsimulator.instrument;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InstrumentsService {

    private InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentsService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }
    public List<Instrument> getInstruments() {
        return instrumentRepository.findAll();
    }
}
