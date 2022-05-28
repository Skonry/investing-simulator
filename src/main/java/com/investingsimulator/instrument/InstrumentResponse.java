package com.investingsimulator.instrument;

import java.time.LocalDate;

public class InstrumentResponse {
    public int id;

    public String name;

    public String underlyingIndex;

    public String issuer;

    public LocalDate dateOfFirstQuotation;

    public InstrumentResult instrumentResult;

    public InstrumentResponse(Instrument instrument) {
        this.id = instrument.getId();
        this.name = instrument.getName();
        this.underlyingIndex = instrument.getUnderlyingIndex();
        this.issuer = instrument.getIssuer();
        this.dateOfFirstQuotation = instrument.getDateOfFirstQuotation();
        this.instrumentResult = instrument.calculateResult(
                this.dateOfFirstQuotation,
                LocalDate.now()
        );
    }
}
