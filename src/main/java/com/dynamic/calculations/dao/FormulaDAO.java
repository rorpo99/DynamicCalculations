package com.dynamic.calculations.dao;

import com.dynamic.calculations.dto.Formula;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormulaDAO implements DAO<Formula>{
    private final JdbcTemplate jdbcTemplate;

    public FormulaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Formula> getAll() {
        return jdbcTemplate.query("SELECT * FROM formulas", new BeanPropertyRowMapper<>(Formula.class));
    }

    public Formula get(int id) {
        return jdbcTemplate.query(
                        "SELECT * FROM formulas WHERE id=?",
                        new Object[] {id}, new BeanPropertyRowMapper<>(Formula.class))
                .stream().findAny().orElse(null);
    }

    public Formula getByFormulaString(String formula) {
        return jdbcTemplate.query(
                        "SELECT * FROM formulas WHERE formula_string=?",
                        new Object[] {formula}, new BeanPropertyRowMapper<>(Formula.class))
                .stream().findAny().orElse(null);
    }

    public void create(Formula formula) {
        jdbcTemplate.update("INSERT INTO formulas (formula_string, x1, x2, x3, x4, x5, result) VALUES(?, ?, ?, ?, ?, ?, ?)",
                formula.getFormulaString(),
                formula.getX1(),
                formula.getX2(),
                formula.getX3(),
                formula.getX4(),
                formula.getX5(),
                formula.getResult());
    }

    public void update(Formula updatedFormula, int id) {
        jdbcTemplate.update("UPDATE formulas SET formula_string=?, x1=?, x2=?, x3=?, x4=?, x5=?, result=? WHERE id=?",
                updatedFormula.getFormulaString(),
                updatedFormula.getX1(),
                updatedFormula.getX2(),
                updatedFormula.getX3(),
                updatedFormula.getX4(),
                updatedFormula.getX5(),
                updatedFormula.getResult(),
                id);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE from formulas WHERE id=?", id);
    }
}