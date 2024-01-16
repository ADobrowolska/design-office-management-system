--liquibase formatted sql
--changeset adobrowolska:3

CREATE TABLE project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    budget DECIMAL NOT NULL,
    description TEXT
);

CREATE TABLE assign_project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    project_id INT NOT NULL
);

ALTER TABLE assign_project
    ADD CONSTRAINT employee_id_fk
    FOREIGN KEY (employee_id) REFERENCES employee(id);

    ALTER TABLE assign_project
        ADD CONSTRAINT project_id_fk
        FOREIGN KEY (project_id) REFERENCES project(id);



