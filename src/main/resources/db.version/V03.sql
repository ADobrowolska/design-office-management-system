--liquibase formatted sql
--changeset adobrowolska:3

CREATE TABLE project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    budget DECIMAL NOT NULL,
    description TEXT,
    employee_id INT
);

ALTER TABLE project
    ADD CONSTRAINT employee_id_fk
    FOREIGN KEY (employee_id) REFERENCES employee(id);

