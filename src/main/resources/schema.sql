CREATE TABLE formulas (
                          id SERIAL PRIMARY KEY,
                          formula_string VARCHAR(300) NOT NULL,
                          x1 BIGINT,
                          x2 BIGINT,
                          x3 BIGINT,
                          x4 BIGINT,
                          x5 BIGINT,
                          result BIGINT
);

ALTER TABLE formulas ADD COLUMN created_at TIMESTAMP DEFAULT NOW();