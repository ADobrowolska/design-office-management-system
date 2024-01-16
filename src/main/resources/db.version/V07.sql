--liquibase formatted sql
--changeset adobrowolska:7

ALTER TABLE project
ADD CONSTRAINT project_name_uc UNIQUE (name);