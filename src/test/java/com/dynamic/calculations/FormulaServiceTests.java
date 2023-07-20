package com.dynamic.calculations;

import com.dynamic.calculations.dto.Formula;
import com.dynamic.calculations.exception.InvalidFormulaException;
import com.dynamic.calculations.service.FormulaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.util.TestPropertyValues;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = FormulaServiceTests.Initializer.class)
public class FormulaServiceTests {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("formulas")
            .withUsername("postgres")
            .withPassword("deutsche123#")
            .withExposedPorts(5432)
            .withInitScript("schema.sql");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    String.format("spring.datasource.url=jdbc:postgresql://localhost:%d/formulas", postgres.getFirstMappedPort()),
                    "spring.datasource.username=postgres",
                    "spring.datasource.password=deutsche123#"
            ).applyTo(applicationContext);
        }
    }

    static {
        postgres.start();
        var containerDelegate = new JdbcDatabaseDelegate(postgres, "");
        ScriptUtils.runInitScript(containerDelegate, "test-data.sql");
    }

    @Autowired
    private FormulaService service;

    @Test
    public void getMethodsReturnFormulaClass() {
        int amountOfRecords = service.getAll().size();
        assertEquals(5, amountOfRecords);
        assertEquals(Formula.class, service.getFormula(1).getClass());

    }

    @Test
    @Transactional
    public void createFormulaAndCheckResult() throws InvalidFormulaException {
        //answer for this set up is 15
        Formula formula = Formula.builder()
                .formulaString("x1 and not x3 or (x2 and not (x4 and x2) or x5)")
                .x1(23)
                .x2(3)
                .x3(18)
                .x4(9)
                .x5(8)
                .build();
        service.createFormula(formula);
        Formula retrievedFormula = service.getFormula(6);
        assertEquals("x1 and not x3 or (x2 and not (x4 and x2) or x5)", retrievedFormula.getFormulaString());
        assertEquals(23, retrievedFormula.getX1());
        assertEquals(3, retrievedFormula.getX2());
        assertEquals(18, retrievedFormula.getX3());
        assertEquals(9, retrievedFormula.getX4());
        assertEquals(8, retrievedFormula.getX5());
        assertEquals(15, retrievedFormula.getResult());
    }

    @Test
    @Transactional
    public void updateVariableValueAndCheckResultChange() throws InvalidFormulaException {
        Formula formula = service.getFormula(5);

        assertEquals(2, formula.getResult());

        formula.setX2(3);
        service.updateFormula(formula);

        assertEquals(3, service.getFormula(5).getResult());
    }

    @Test
    @Transactional
    public void updateFormulaStringAndCheckResultChange() throws InvalidFormulaException {
        Formula formula = service.getFormula(5);

        assertEquals(2, formula.getResult());

        formula.setFormulaString("x2 or x4 or true");
        service.updateFormula(formula);

        assertEquals(7, service.getFormula(5).getResult());
    }

    @Test
    @Transactional
    public void deleteFormula() {
        assertEquals(5, service.getAll().size());
        service.deleteFormula(5);
        assertEquals(4, service.getAll().size());
    }
}
