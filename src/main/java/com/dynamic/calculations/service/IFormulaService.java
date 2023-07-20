package com.dynamic.calculations.service;

import com.dynamic.calculations.dto.Formula;
import java.util.List;

public interface IFormulaService {

    public List<Formula> getAll();

    public Formula getFormula(int id);

    public void createFormula(Formula newFormula);

    public void updateFormula(Formula updatedFormula);

    public void deleteFormula(int id);
}
