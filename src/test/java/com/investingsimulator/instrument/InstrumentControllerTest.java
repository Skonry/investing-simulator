package com.investingsimulator.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InstrumentController.class)
public class InstrumentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper
}
