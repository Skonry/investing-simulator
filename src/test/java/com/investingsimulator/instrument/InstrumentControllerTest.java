package com.investingsimulator.instrument;

import com.investingsimulator.InvestingSimulatorApplication;
import com.investingsimulator.common.Currency;
import com.investingsimulator.common.Money;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InvestingSimulatorApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class InstrumentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Nested
    class Index {
        @Test
        void shouldReturnAllRecords() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/instrument")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(3)));
        }

        @Test
        void shouldReturnPaginatedRecords() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/instrument")
                            .param("size", "2")
                            .param("page", "1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(2)));

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/instrument")
                            .param("size", "2")
                            .param("page", "2")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(1)));
        }
    }
}
