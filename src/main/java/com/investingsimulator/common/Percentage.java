package com.investingsimulator.common;

public class Percentage {
    private double value;

    public Percentage(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double getNormalized() { return  value / 100; }
}
