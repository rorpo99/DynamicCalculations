package com.dynamic.calculations.entity;

import com.dynamic.calculations.dto.Formula;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormulaEntity {

    private int id;

    private String formulaString;
    private Integer x1;
    private Integer x2;
    private Integer x3;
    private Integer x4;
    private Integer x5;
    private Integer result;


    public Formula convertToFormula() {
        Formula formula = new Formula();
        formula.setId(this.id);
        formula.setFormulaString(this.formulaString);
        formula.setX1(this.x1);
        formula.setX2(this.x2);
        formula.setX3(this.x3);
        formula.setX4(this.x4);
        formula.setX5(this.x5);
        formula.setResult(this.result);
        return formula;
    }
}
