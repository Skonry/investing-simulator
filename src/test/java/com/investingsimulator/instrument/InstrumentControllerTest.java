package com.investingsimulator.instrument;

import com.investingsimulator.InvestingSimulatorApplication;
import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InvestingSimulatorApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Nested
    class Index {
        private List<Instrument> testInstruments = new ArrayList<Instrument>();

        @BeforeEach
        private void setupTestData() {
            testInstruments.add(createInstrument("nameA", "indexA", "issuerA", 2020));
            testInstruments.add(createInstrument("nameB", "indexA", "issuerB", 2020));
            testInstruments.add(createInstrument("nameC", "indexA", "issuerC", 2020));
            testInstruments.add(createInstrument("nameD", "indexB", "issuerB", 2020));
            testInstruments.add(createInstrument("nameE", "indexB", "issuerC", 2020));
            testInstruments.add(createInstrument("nameF", "indexC", "issuerA", 2020));
            testInstruments.add(createInstrument("nameG", "indexD", "issuerB", 2020));
        }

        private Instrument createInstrument(
                String name,
                String underlyingIndex,
                String issuer,
                int yearOfFirstQuotation
        ) {
            List<PriceRecord> priceRecords = List.of(
                    new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                    new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2021, 1, 1)),
                    new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2022, 1, 1))
            );

            Instrument instrument = new Instrument(
                    name,
                    underlyingIndex,
                    issuer,
                    LocalDate.of(yearOfFirstQuotation, 1, 1),
                    priceRecords
            );

            return instrumentRepository.save(instrument);
        }

        @AfterEach
        private void clearTestData() {
            testInstruments.clear();
            instrumentRepository.deleteAll();
        }

        @Test
        void shouldReturnAllRecords() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/instrument")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(7)));
        }

        @Test
        void shouldReturnPaginatedRecords() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/instrument")
                            .param("size", "4")
                            .param("page", "0")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(4)));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/instrument")
                            .param("size", "4")
                            .param("page", "1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(3)));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/instrument")
                            .param("size", "4")
                            .param("page", "2")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(0)));
        }

        @Test
        void shouldReturnSortedRecords() throws  Exception {
            testInstruments.sort(
                    Comparator.comparing(Instrument::getUnderlyingIndex)
            );

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/instrument")
                            .param("sort", "underlyingIndex")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(
                            "$.content[*].name",
                            equalTo(testInstruments.stream().map(instrument -> instrument.getName()).toList())
                    ));
        }

        @Test
        void shouldReturnFilteredRecords() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/instrument")
                            .param("filter", "underlyingIndex:indexA")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(
                            "$.content[*].underlyingIndex",
                            everyItem(equalTo("indexA"))
                    ));
        }
    }
}
