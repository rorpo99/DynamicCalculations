package com.dynamic.calculations.service;

import com.dynamic.calculations.dao.DAO;
import com.dynamic.calculations.dto.Formula;
import com.dynamic.calculations.entity.FormulaEntity;
import com.dynamic.calculations.exception.InvalidFormulaException;
import com.dynamic.calculations.util.FormulaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dynamic.calculations.util.FormulaMapper.convertToFormula;
import static com.dynamic.calculations.util.FormulaMapper.convertToFormulaEntity;
import static com.dynamic.calculations.util.FormulaUtil.calculateResult;

@Service
@RequiredArgsConstructor
public class FormulaService implements IFormulaService {

    private final DAO<FormulaEntity> formulaDao;

    public List<Formula> getAll() {
        List<FormulaEntity> formulas = formulaDao.getAll();
        return formulas.stream().map(FormulaMapper::convertToFormula).toList();
    }

    public Formula getFormula(int id) {
        return convertToFormula(formulaDao.get(id).get());
    }

    public void createFormula(Formula newFormula) throws InvalidFormulaException {
        Integer result = calculateResult(newFormula);
        newFormula.setResult(result);
        formulaDao.create(convertToFormulaEntity(newFormula));
    }

    public void updateFormula(Formula updatedFormula) throws InvalidFormulaException {
        Integer result = calculateResult(updatedFormula);
        updatedFormula.setResult(result);
        formulaDao.update(convertToFormulaEntity(updatedFormula), updatedFormula.getId());
    }

    public void deleteFormula(int id) {
        formulaDao.delete(id);
    }
}
