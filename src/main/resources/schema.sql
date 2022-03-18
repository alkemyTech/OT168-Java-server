CREATE DATABASE IF NOT EXISTS alkemy_ong;
USE alkemy_ong;

CREATE TABLE alkemy_ong.news (
news_id BIGINT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR (20) NOT NULL,
content VARCHAR (100) NOT NULL,
image VARCHAR (25) NOT NULL,
created_at TIMESTAMP,
updated_at TIMESTAMP,
deleted TINYINT
);

CREATE TABLE IF NOT EXISTS organizations (
  id_organization BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(60) NOT NULL,
  image VARCHAR(256) NOT NULL,
  address VARCHAR(256) NULL,
  phone INT UNSIGNED NULL,
  email VARCHAR(60) NOT NULL,
  about_us_text VARCHAR(256) NULL,
  welcome_text VARCHAR(256) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  deleted BIT(1) NOT NULL DEFAULT 0
);

CREATE TABLE if NOT EXISTS slides (
    id_slides BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    image_url VARCHAR(256) NOT NULL,
    text VARCHAR(256),
    slide_order INT NOT NULL, -- 'order' is a keyword in mySQL
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted BIT(1) NOT NULL DEFAULT 0,
    organization_id BIGINT unsigned NOT NULL
);