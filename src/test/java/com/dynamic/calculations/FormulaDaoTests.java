package com.dynamic.calculations;

import com.dynamic.calculations.dao.FormulaDAO;
import com.dynamic.calculations.entity.FormulaEntity;
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
@ContextConfiguration(initializers = FormulaDaoTests.Initializer.class)
public class FormulaDaoTests {

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
    private FormulaDAO dao;



    @Test
    public void retrieveAllRecords() {
        int amountOfRecords = dao.getAll().size();
        assertEquals(5, amountOfRecords);
    }

    @Test
    public void retrieveRecordById() {
        FormulaEntity formula = dao.get(1).get();
        assertEquals("x1 and x2 or not x3", formula.getFormulaString());
        assertEquals( 1, formula.getX1());
        assertEquals( 2, formula.getX2());
        assertEquals( 3, formula.getX3());
    }

    @Test
    public void retrieveRecordByFormulaString() {
        FormulaEntity formula = dao.getByFormulaString("x1 and x2 or not x3").get();
        assertNotNull(formula);
    }

    @Test
    @Transactional
    public void insertRecord() {

        assertEquals(5, dao.getAll().size());

        FormulaEntity formula = FormulaEntity.builder()
                .formulaString("x1 and x5")
                .x1(1)
                .x5(5)
                .result(1)
                .build();

        dao.create(formula);

        assertEquals(6, dao.getAll().size());
        FormulaEntity retrievedFormula = dao.get(6).get();
        assertEquals("x1 and x5", retrievedFormula.getFormulaString());
        assertEquals(1, retrievedFormula.getX1());
        assertEquals(5, retrievedFormula.getX5());
        assertEquals(1, retrievedFormula.getResult());
    }

    @Test
    @Transactional
    public void updateRecord() {
        FormulaEntity formula = dao.get(5).get();
        assertEquals("not x4 and x2", formula.getFormulaString());
        formula.setFormulaString("x3 or (x4 and x5)");
        dao.update(formula, 5);
        FormulaEntity retrievedFormula = dao.get(5).get();
        assertEquals("x3 or (x4 and x5)", retrievedFormula.getFormulaString());
    }

    @Test
    @Transactional
    public void deleteRecord() {
        assertEquals(5, dao.getAll().size());
        dao.delete(1);
        assertEquals(4, dao.getAll().size());
    }
}
