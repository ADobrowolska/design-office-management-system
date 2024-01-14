--liquibase formatted sql
--changeset adobrowolska:6

INSERT INTO department(id, name) VALUES(1, 'Geodesy');
INSERT INTO department(id, name) VALUES(2, 'Road');
INSERT INTO department(id, name) VALUES(3, 'Bridge');