package com.investingsimulator.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InstrumentService {

    private InstrumentRepository instrumentRepository;

    @Autowired
    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public Page<Instrument> getInstruments(Pageable pageable, String filter) {
        InstrumentSpecification instrumentSpecification = constructInstrumentSpecification(filter);

        return instrumentRepository.findAll(instrumentSpecification, pageable);
    }

    public Instrument getInstrument(int id) {
        Instrument instrument = instrumentRepository.findById(id).orElseThrow();

        return instrument;
    }

    public InstrumentResult getInstrumentResult(int id, LocalDate startDate, LocalDate endDate) {
        Instrument instrument = instrumentRepository.findById(id).orElseThrow();

        return instrument.calculateResult(startDate, endDate);
    }

    private InstrumentSpecification constructInstrumentSpecification(String filter) {
        if (filter == null || filter.isBlank()) {
            return  null;
        }

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+)");

        Matcher matcher = pattern.matcher(filter);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid filter value");
        }

        SearchCriteria searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));

        return new InstrumentSpecification(searchCriteria);
    }
}
