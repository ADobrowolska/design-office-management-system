--liquibase formatted sql
--changeset adobrowolska:8

ALTER TABLE employee_rate
    ADD COLUMN currency VARCHAR(20);

UPDATE employee_rate SET currency = "PLN";

ALTER TABLE employee_rate
MODIFY currency VARCHAR(20) NOT NULL;
