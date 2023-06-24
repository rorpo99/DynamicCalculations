CREATE TABLE formulas (
                          id SERIAL PRIMARY KEY,
                          formula_string VARCHAR(300) NOT NULL,
                          x1 NUMERIC,
                          x2 NUMERIC,
                          x3 NUMERIC,
                          x4 NUMERIC,
                          x5 NUMERIC,
                          result NUMERIC
);

ALTER TABLE formulas ADD COLUMN created_at TIMESTAMP DEFAULT NOW();