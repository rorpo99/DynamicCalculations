package com.dynamic.calculations.controller;

import com.dynamic.calculations.dto.Formula;
import com.dynamic.calculations.service.IFormulaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/formulas")
@RequiredArgsConstructor
public class FormulaController {

    private final IFormulaService formulaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Formula> displayHistory() {
        return formulaService.getAll();
    }

    @GetMapping("{id}")
    public Formula displayFormulaDetails(@PathVariable("id") int id) {
        return formulaService.getFormula(id);
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void calculateResult(@RequestBody @Valid Formula newFormula) {
        formulaService.createFormula(newFormula);
    }

    @PutMapping("{id}")
    public void updateFormula(@RequestBody @Valid Formula updatedFormula) {
        formulaService.updateFormula(updatedFormula);
    }

    @DeleteMapping("{id}")
    public void deleteFormula(@PathVariable("id") int id) {
        formulaService.deleteFormula(id);
    }
}
