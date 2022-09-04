package com.investingsimulator.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/instrument")
public class InstrumentController {

    private InstrumentService instrumentService;

    @Autowired
    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    public Page<InstrumentResponse> index(Pageable pageable, @RequestParam(required = false) String filter) {
        Page<Instrument> instrumentsPage = instrumentService.getInstruments(pageable, filter);

        return instrumentsPage.map(InstrumentResponse::new);
    }

    @GetMapping("/{id}")
    public InstrumentResponse show(@PathVariable int id) {
        Instrument instrument = instrumentService.getInstrument(id);

        return new InstrumentResponse(instrument);
    }

    @GetMapping("/{id}/result")
    public InstrumentResultResponse result(
            @PathVariable int id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        InstrumentResult instrumentResult = instrumentService.getInstrumentResult(id, startDate, endDate);

        return new InstrumentResultResponse(instrumentResult);
    }
}
