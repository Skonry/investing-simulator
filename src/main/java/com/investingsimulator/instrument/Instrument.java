package com.investingsimulator.instrument;

import com.investingsimulator.common.Percentage;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "instruments")
public class
Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    private String underlyingIndex;

    private String issuer;

    private LocalDate dateOfFirstQuotation;

    private LocalDate dateOfLastQuotation;

    @OneToMany(cascade = CascadeType.ALL)
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
        this.dateOfLastQuotation = priceRecords.get(priceRecords.size() - 1).getDate();
        this.priceRecords = priceRecords;
    }

    public Instrument() {}

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

    public LocalDate getDateOfLastQuotation() { return dateOfLastQuotation; }

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


        Percentage expectedResult = new Percentage(
                endRecord.getValue().toDouble() / startRecord.getValue().toDouble() * 50
        );

        return new InstrumentResult(startDate, endDate, expectedResult);
    }
}
