package com.investingsimulator.portfolio;

public class AddInstrumentRequest {
    public int instrumentId;

    public double percentage;

    public AddInstrumentRequest(int instrumentId, double percentage) {
        this.instrumentId = instrumentId;
        this.percentage = percentage;
    }
}
