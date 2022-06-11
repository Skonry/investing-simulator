package com.investingsimulator.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instrument")
public class InstrumentController {

    private InstrumentService instrumentService;

    @Autowired
    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public Page<InstrumentResponse> index(Pageable pageable) {
        Page<Instrument> instrumentsPage = instrumentService.getInstruments(pageable);

        return instrumentsPage.map(InstrumentResponse::new);
    }

    @GetMapping("/{id}")
    public InstrumentResponse show(@PathVariable int id) {
        Instrument instrument = instrumentService.getInstrument(id);

        return new InstrumentResponse(instrument);
    }
}
