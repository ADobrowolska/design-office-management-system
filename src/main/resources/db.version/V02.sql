--liquibase formatted sql
--changeset adobrowolska:2

CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    department_id INT NOT NULL,
    user_id INT NOT NULL
);

CREATE TABLE department (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

ALTER TABLE employee
    ADD CONSTRAINT department_id_fk
    FOREIGN KEY (department_id) REFERENCES department(id);

ALTER TABLE employee
    ADD CONSTRAINT user_id_fk
    FOREIGN KEY (user_id) REFERENCES app_user(id);

