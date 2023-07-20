package com.dynamic.calculations.service;

import com.dynamic.calculations.dto.Formula;
import com.dynamic.calculations.exception.InvalidFormulaException;

import java.util.List;

public interface IFormulaService {

    public List<Formula> getAll();

    public Formula getFormula(int id);

    public void createFormula(Formula newFormula) throws InvalidFormulaException;

    public void updateFormula(Formula updatedFormula) throws InvalidFormulaException;

    public void deleteFormula(int id);
}
