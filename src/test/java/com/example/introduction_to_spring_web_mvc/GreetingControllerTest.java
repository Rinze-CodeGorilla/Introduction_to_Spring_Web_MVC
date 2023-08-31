package com.example.introduction_to_spring_web_mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String testName = "rinze";

    @Test
    @DirtiesContext
    void greeting() throws Exception {
        hoi();
        mvc.perform(get("/api/names"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(testName)))
                ;
    }

    @Test
    @DirtiesContext
    void hoi() throws Exception {
        var result = mvc.perform(
                        get("/hoi")
                                .queryParam("name", testName)
                                .accept(MediaType.TEXT_HTML)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString(testName)))
//                .andReturn()
                ;
    }

    @Test
    void stuk1() throws Exception {
        mvc.perform(get("/stuk"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void stuk2() throws Exception {
        mvc.perform(get("/stuk").queryParam("name"))
                .andExpect(status().isBadRequest());
    }
}