--liquibase formatted sql
--changeset adobrowolska:4

CREATE TABLE employee_rate (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    category VARCHAR(250) NOT NULL,
    rate DOUBLE NOT NULL,
    employee_id INT NOT NULL
);

CREATE TABLE cost (
    id INT AUTO_INCREMENT PRIMARY KEY,
    occurrence_date TIMESTAMP NOT NULL,
    quantity INTEGER NOT NULL,
    employee_rate_id INT NOT NULL,
    project_id INT NOT NULL
);

ALTER TABLE employee_rate
    ADD CONSTRAINT employee_rate_fk
    FOREIGN KEY (employee_id) REFERENCES employee(id);

ALTER TABLE cost
    ADD CONSTRAINT cost_employee_rate_fk
    FOREIGN KEY (employee_rate_id) REFERENCES employee_rate(id);

ALTER TABLE cost
    ADD CONSTRAINT cost_project_fk
    FOREIGN KEY (project_id) REFERENCES project(id);