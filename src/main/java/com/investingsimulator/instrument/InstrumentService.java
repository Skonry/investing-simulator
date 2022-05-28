package com.investingsimulator.instrument;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InstrumentService {

    private InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }
    public List<Instrument> getInstruments() {
        return instrumentRepository.findAll();
    }
}
