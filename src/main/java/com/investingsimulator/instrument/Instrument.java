package com.investingsimulator.instrument;

import com.investingsimulator.common.Money;
import com.investingsimulator.portfolio.PortfolioResult;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "instruments")
public class
Instrument {

    @Id
    private int id;
    private String name;

    private String underlyingIndex;

    private String issuer;

    private LocalDate dateOfFirstQuotation;

    @OneToMany
    private List<PriceRecord> priceRecords;

    public Instrument(
            String name,
            String underlyingIndex,
            String issuer,
            LocalDate dateOfFirstQuotation,
            List<PriceRecord> priceRecords
    ) {
        this.name = name;
        this.underlyingIndex = underlyingIndex;
        this.issuer = issuer;
        this.dateOfFirstQuotation = dateOfFirstQuotation;
        this.priceRecords = priceRecords;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnderlyingIndex() {
        return underlyingIndex;
    }

    public String getIssuer() {
        return issuer;
    }

    public LocalDate getDateOfFirstQuotation() {
        return dateOfFirstQuotation;
    }

    public List<PriceRecord> getPriceRecords() {
        return priceRecords;
    }

    public void setPriceRecords(List<PriceRecord> priceRecords) {
        this.priceRecords = priceRecords;
    }

    public InstrumentResult calculateResult(LocalDate startDate, LocalDate endDate) {
        PriceRecord startRecord = priceRecords
                                    .stream()
                                    .filter(record -> record.getDate().isEqual(startDate))
                                    .findAny()
                                    .orElseThrow();

        PriceRecord endRecord = priceRecords
                                    .stream()
                                    .filter(record -> record.getDate().isEqual(endDate))
                                    .findAny()
                                    .orElseThrow();

        Money expectedResult = new Money(
                startRecord.getValue().toDouble() / endRecord.getValue().toDouble(),
                startRecord.getValue().getCurrency()
        );

        return new InstrumentResult(startDate, endDate, expectedResult);
    }
}
