INSERT INTO formulas (formula_string, x1, x2, x3, x4, x5, result) VALUES('x1 and x2 or not x3', 1, 2, 3, 4, 5, null);
INSERT INTO formulas (formula_string, x1, x2, x3, x4, x5, result) VALUES('x2 and x3 and not (x4 or x5)', null, 2, 3, 4, 5, null);
INSERT INTO formulas (formula_string, x1, x2, x3, x4, x5, result) VALUES('x1', 1, null, null, null, null,  null);
INSERT INTO formulas (formula_string, x1, x2, x3, x4, x5, result) VALUES('x3 or x5', null, null, 3, null, 5, null);
INSERT INTO formulas (formula_string, x1, x2, x3, x4, x5, result) VALUES('not x4 and x2', null, 2, null, 4, null, 2);