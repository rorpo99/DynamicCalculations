package com.dynamic.calculations;

import com.dynamic.calculations.controller.FormulaController;
//import org.junit.Test;
import com.dynamic.calculations.dto.Formula;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FormulaController.class)
@AutoConfigureMockMvc
public class FormulaControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FormulaController formulaController;

    @Test
    void getRequestReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/formulas")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidInputThenReturns200() throws Exception {
        Formula formula = Formula.builder().formulaString("x1").x1(1).build();

        mockMvc.perform(put("/api/v1/formulas/{id}", 10)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(formula)))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidInputThenReturns400() throws Exception {
        Formula formula = Formula.builder().build();

        mockMvc.perform(post("/api/v1/formulas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(formula)))
                .andExpect(status().isBadRequest());
    }

}
