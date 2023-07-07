package com.dynamic.calculations.util;

import com.dynamic.calculations.dto.Formula;
import com.dynamic.calculations.entity.FormulaEntity;

public class FormulaMapper {

    public static FormulaEntity convertToFormulaEntity(Formula formula) {
        FormulaEntity formulaEntity = new FormulaEntity();
        formulaEntity.setFormulaString(formula.getFormulaString());
        formulaEntity.setX1(formula.getX1());
        formulaEntity.setX2(formula.getX2());
        formulaEntity.setX3(formula.getX3());
        formulaEntity.setX4(formula.getX4());
        formulaEntity.setX5(formula.getX5());
        formulaEntity.setResult(formula.getResult());
        return formulaEntity;
    }

    public static Formula convertToFormula(FormulaEntity formulaEntity) {
        Formula formula = new Formula();
        formula.setId(formulaEntity.getId());
        formula.setFormulaString(formulaEntity.getFormulaString());
        formula.setX1(formulaEntity.getX1());
        formula.setX2(formulaEntity.getX2());
        formula.setX3(formulaEntity.getX3());
        formula.setX4(formulaEntity.getX4());
        formula.setX5(formulaEntity.getX5());
        formula.setResult(formulaEntity.getResult());
        return formula;
    }
}
