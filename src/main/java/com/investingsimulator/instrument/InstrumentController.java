package com.investingsimulator.instrument;

import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/instrument")
public class InstrumentController {
    private InstrumentService instrumentService;

    private InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentController(InstrumentService instrumentService, InstrumentRepository instrumentRepository) {
        this.instrumentService = instrumentService;
        this.instrumentRepository = instrumentRepository;
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

    @PostMapping("/generate")
    public int generate() {
        final List<PriceRecord> priceRecords = List.of(
                new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2021, 1, 1)),
                new PriceRecord(new Money(30, Currency.USD), LocalDate.of(2022, 1, 1))
        );

        final Instrument instrument = new Instrument(
                "InstrumentName",
                "IndexName",
                "Issuer",
                LocalDate.of(2020, 1, 1),
                priceRecords
        );

        instrumentRepository.save(instrument);

        return instrument.getId();
    }
}
