CREATE DATABASE IF NOT EXISTS it_world_db;
USE it_world_db;

CREATE TABLE IF NOT EXISTS customers (
  customer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(50) NOT NULL,
  comment     VARCHAR(300)
);


CREATE TABLE IF NOT EXISTS companies (
  company_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(50) NOT NULL,
  address    VARCHAR(50),
  comment    VARCHAR(300)
);

CREATE TABLE IF NOT EXISTS projects (
  project_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(50) NOT NULL,
  customer_id BIGINT      NOT NULL,
  comment     VARCHAR(300),
  FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS developers (
  developer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  fname        VARCHAR(50) NOT NULL,
  lname        VARCHAR(50),
  email        VARCHAR(100),
  project_id   BIGINT,
  company_id   BIGINT,
  FOREIGN KEY (project_id) REFERENCES projects (project_id)
  ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (company_id) REFERENCES companies (company_id)
  ON DELETE SET NULL ON UPDATE CASCADE,
  INDEX (email)
);

CREATE TABLE IF NOT EXISTS skills (
  skill_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS developers_skills (
  developer_id BIGINT NOT NULL,
  skill_id     BIGINT NOT NULL,
  FOREIGN KEY (developer_id) REFERENCES developers (developer_id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (skill_id) REFERENCES skills (skill_id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  UNIQUE KEY (developer_id, skill_id)
);
