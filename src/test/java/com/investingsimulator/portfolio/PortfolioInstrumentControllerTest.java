package com.investingsimulator.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investingsimulator.InvestingSimulatorApplication;
import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentRepository;
import com.investingsimulator.instrument.PriceRecord;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
public class PortfolioInstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PortfolioInstrumentRepository portfolioInstrumentRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    class AddInstrument {
        @AfterEach
        private void clearTestData() {
            portfolioRepository.deleteAll();
            portfolioInstrumentRepository.deleteAll();
            instrumentRepository.deleteAll();
        }

        @Test
        void shouldAddPortfolioInstrument() throws Exception {
            Instrument instrument = instrumentRepository.save(new Instrument(
                    "InstrumentName1",
                    "IndexName1",
                    "Issuer",
                    LocalDate.of(2020, 1, 1),
                    List.of(
                            new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                            new PriceRecord(new Money(15, Currency.USD), LocalDate.of(2021, 1, 1)),
                            new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2022, 1, 1))
                    )
            ));

            Portfolio portfolio = portfolioRepository.save(
                    new Portfolio("Portfolio Name", new Money(100, Currency.USD))
            );

            AddInstrumentRequest addInstrumentRequest = new AddInstrumentRequest(instrument.getId(), 50);

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/portfolio/{portfolioId}/instrument", portfolio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(addInstrumentRequest)))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class RemoveInstrument {
        @AfterEach
        private void clearTestData() {
            portfolioRepository.deleteAll();
            portfolioInstrumentRepository.deleteAll();
            instrumentRepository.deleteAll();
        }

        @Test
        void shouldRemovePortfolioInstrument() throws Exception {
            Instrument instrument = instrumentRepository.save(new Instrument(
                    "InstrumentName1",
                    "IndexName1",
                    "Issuer",
                    LocalDate.of(2020, 1, 1),
                    List.of(
                            new PriceRecord(new Money(10, Currency.USD), LocalDate.of(2020, 1, 1)),
                            new PriceRecord(new Money(15, Currency.USD), LocalDate.of(2021, 1, 1)),
                            new PriceRecord(new Money(20, Currency.USD), LocalDate.of(2022, 1, 1))
                    )
            ));

            Portfolio portfolio = portfolioRepository.save(
                    new Portfolio("Portfolio Name", new Money(100, Currency.USD))
            );


            PortfolioInstrument portfolioInstrument = portfolioInstrumentRepository.save(
                    new PortfolioInstrument(portfolio, instrument, 100)
            );

            mockMvc.perform(MockMvcRequestBuilders
                    .delete(
                            "/api/portfolio/{portfolioId}/instrument/{id}",
                            portfolio.getId(),
                            portfolioInstrument.getId()
                    )
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }
}
