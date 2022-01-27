CREATE TABLE IF NOT EXISTS employee_wage
(
    id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    wage DECIMAL(19,2),
    eventTime TIMESTAMP NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (name,surname)
);
