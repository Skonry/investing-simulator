package com.investingsimulator.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PercentageAttributeConverter implements AttributeConverter<Percentage, Double> {
    @Override
    public Double convertToDatabaseColumn(Percentage percentage) {
        return percentage == null ? null : percentage.getValue();
    }

    @Override
    public Percentage convertToEntityAttribute(Double percentage) {
        return percentage == null ? null : new Percentage(percentage);
    }
}
