--liquibase formatted sql
--changeset adobrowolska:5

ALTER TABLE employee
ADD CONSTRAINT employee_user_id_uc UNIQUE (user_id);