package com.investingsimulator.portfolio;

import com.investingsimulator.InvestingSimulatorApplication;
import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
import com.investingsimulator.instrument.Instrument;
import com.investingsimulator.instrument.InstrumentRepository;
import com.investingsimulator.instrument.PriceRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InvestingSimulatorApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PortfolioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private PortfolioInstrumentRepository portfolioInstrumentRepository;

    @Nested
    class Index {
        @AfterEach
        private void clearTestData() {
            portfolioRepository.deleteAll();
            instrumentRepository.deleteAll();
        }

        @Test
        void shouldReturnPortfolios() throws Exception {
            Portfolio portfolio1 = new Portfolio(
                    "Portfolio Name 1",
                    new Money(100, Currency.USD)
            );

            Portfolio portfolio2 = new Portfolio(
                    "Portfolio Name 2",
                    new Money(100, Currency.USD)
            );

            portfolioRepository.save(portfolio1);

            portfolioRepository.save(portfolio2);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/portfolio")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)));
        }
    }

    @Nested
    class Show {
        @AfterEach
        private void clearTestData() {
            portfolioRepository.deleteAll();
            instrumentRepository.deleteAll();
        }

        @Test
        void shouldReturnPortfolio() throws Exception {
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

            Portfolio portfolio = new Portfolio(
                    "Portfolio Name",
                    new Money(100, Currency.USD)
            );

            portfolio.addInstrument(instrument, 100);

            portfolioRepository.save(portfolio);

            mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/portfolio/{id}", portfolio.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class Result {
        @AfterEach
        private void clearTestData() {
            portfolioInstrumentRepository.deleteAll();
            portfolioRepository.deleteAll();
            instrumentRepository.deleteAll();
        }

        @Test
        void shouldReturnPortfolioResult() throws Exception {
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

            Portfolio portfolio = new Portfolio(
                    "Portfolio Name",
                    new Money(100, Currency.USD)
            );

            portfolio.addInstrument(instrument, 100);

            portfolioRepository.save(portfolio);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/portfolio/{id}/result", portfolio.getId())
                            .param("startDate", "2020-01-01")
                            .param("endDate", "2022-01-01")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.returnOnInvestment", is(200.0)));
        }
    }
}
