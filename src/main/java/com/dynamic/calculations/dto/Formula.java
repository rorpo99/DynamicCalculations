package com.dynamic.calculations.dto;

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
}
