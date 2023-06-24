package com.dynamic.calculations.controller;

import com.dynamic.calculations.dao.FormulaDAO;
import com.dynamic.calculations.dto.Formula;
import com.dynamic.calculations.service.FormulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/formulas")
@RequiredArgsConstructor
public class FormulaController {

    private final FormulaDAO formulaDao;

    private final FormulaService formulaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Formula> displayHistory() {
        return formulaDao.getAll();
    }

    @GetMapping("{id}")
    public Formula displayFormulaDetails(@PathVariable("id") int id) {
        return formulaDao.get(id);
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void calculateResult(@RequestBody Formula newFormula) {
        Integer result = formulaService.calculateResult(newFormula);
        newFormula.setResult(result);
        formulaDao.create(newFormula);
    }

    @PutMapping("{id}")
    public void updateFormula(@RequestBody Formula updatedFormula) {
        Integer result = formulaService.calculateResult(updatedFormula);
        updatedFormula.setResult(result);
        formulaDao.update(updatedFormula, updatedFormula.getId());
    }

    @DeleteMapping("{id}")
    public void deleteFormula(@PathVariable("id") int id) {
        formulaDao.delete(id);
    }
}
