package com.investingsimulator.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/instrument")
public class InstrumentController {

    private InstrumentsService instrumentsService;

    @Autowired
    public InstrumentController(InstrumentsService instrumentsService) {
        this.instrumentsService = instrumentsService;
    }

    @GetMapping
    public List<InstrumentDto> index() {
        List<Instrument> instruments = instrumentsService.getInstruments();

        return instruments.stream().map(InstrumentDto::new).toList();
    }
}
