package com.investingsimulator.instrument;

import java.time.LocalDate;

public record InstrumentResultResponse(
    int id,
    String name,
    String underlyingIndex,
    String issuer,
    LocalDate dateOfFirstQuotation,
    LocalDate dateOfLastQuotation,
    double returnOnInvestment,
    double rateOfReturn
) {
    public InstrumentResultResponse(InstrumentResult instrumentResult) {
        this(
                instrumentResult.instrument().getId(),
                instrumentResult.instrument().getName(),
                instrumentResult.instrument().getUnderlyingIndex(),
                instrumentResult.instrument().getIssuer(),
                instrumentResult.instrument().getDateOfFirstQuotation(),
                instrumentResult.instrument().getDateOfLastQuotation(),
                instrumentResult.returnOnInvestment(),
                instrumentResult.rateOfReturn()
        );
    }
}
