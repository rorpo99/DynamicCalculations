package com.dynamic.calculations.dto;

import com.dynamic.calculations.entity.FormulaEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Formula {
    private int id;
    private String formulaString;
    private Integer x1;
    private Integer x2;
    private Integer x3;
    private Integer x4;
    private Integer x5;
    private Integer result;

    public Formula(String formulaString) {
        this.formulaString = formulaString;
    }

    public FormulaEntity convertToFormulaEntity() {
        FormulaEntity formulaEntity = new FormulaEntity();
        formulaEntity.setFormulaString(this.formulaString);
        formulaEntity.setX1(this.x1);
        formulaEntity.setX2(this.x2);
        formulaEntity.setX3(this.x3);
        formulaEntity.setX4(this.x4);
        formulaEntity.setX5(this.x5);
        formulaEntity.setResult(this.result);
        return formulaEntity;
    }
}
