package com.investingsimulator.instrument;

import java.time.LocalDate;
import java.util.List;

public record InstrumentResponse(
        int id,
        String name,
        String underlyingIndex,
        String issuer,
        LocalDate dateOfFirstQuotation,
        LocalDate dateOfLastQuotation,
        List<PriceRecord> priceRecords
) {
    public InstrumentResponse(Instrument instrument) {
        this(
                instrument.getId(),
                instrument.getName(),
                instrument.getUnderlyingIndex(),
                instrument.getIssuer(),
                instrument.getDateOfFirstQuotation(),
                instrument.getDateOfLastQuotation(),
                instrument.getPriceRecords()
        );
    }
}
