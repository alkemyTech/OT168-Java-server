CREATE DATABASE IF NOT EXISTS `alkemy_ong`;
USE `alkemy_ong`;

DROP TABLE IF EXISTS `alkemy_ong`.`roles`;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alkemy_ong`.`roles`
(
    `id`          BIGINT(255) NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(20) NOT NULL,
    `description` VARCHAR(50) DEFAULT NULL,
    `createdat`   TIMESTAMP   DEFAULT NOW(),
    `updatedat`    TIMESTAMP   DEFAULT NOW(),
    `deleted`     BIT         DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `alkemy_ong`.`users`;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alkemy_ong`.`users`
(
    `id`        BIGINT(255)        NOT NULL AUTO_INCREMENT,
    `firstName` VARCHAR(50)        NOT NULL,
    `lastName`  VARCHAR(50)        NOT NULL,
    `email`     VARCHAR(30) UNIQUE NOT NULL,
    `password`  VARCHAR(50)        NOT NULL,
    `photo`     VARCHAR(255) DEFAULT NULL,
    `createdat` TIMESTAMP    DEFAULT NOW(),
    `updatedat`  TIMESTAMP    DEFAULT NOW(),
    `deleted`   BIT          DEFAULT 0,
    `roleEntity`   BIGINT(255)        NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_role` (`roleEntity`),
    CONSTRAINT `FK_role` FOREIGN KEY (`roleEntity`) REFERENCES `alkemy_ong`.`roles` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

DROP TABLE IF EXISTS `alkemy_ong`.`members`;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alkemy_ong`.`members`
(
    `id`           BIGINT(255)  NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(50) NOT NULL,
    `facebookUrl`  VARCHAR(50) DEFAULT NULL,
    `instagramUrl` VARCHAR(30) DEFAULT NULL,
    `linkedinUrl`  VARCHAR(50) DEFAULT NULL,
    `image`        VARCHAR(255) NOT NULL,
    `description`  VARCHAR(50) DEFAULT NULL,
    `createdat`    TIMESTAMP   DEFAULT NOW(),
    `updatedat`     TIMESTAMP   DEFAULT NOW(),
    `deleted`      BIT         DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE alkemy_ong.news
(
    news_id    BIGINT(255)  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL,
    content    VARCHAR(100) NOT NULL,
    image      VARCHAR(25)  NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted    TINYINT,
    type VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS activities (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `content` TEXT NOT NULL,
  `image` VARCHAR(250) NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `deleted` BOOLEAN DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contacts (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `phone` VARCHAR(250) NOT NULL,
  `email` VARCHAR(250) NOT NULL,
  `message`  TEXT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `deleted` BOOLEAN DEFAULT 0,
  PRIMARY KEY (id)
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
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP,
  facebook_url VARCHAR(100) DEFAULT NULL,
  linkedin_url  VARCHAR(100) DEFAULT NULL,
  instagram_url VARCHAR(100) DEFAULT NULL,
  deleted BIT(1) DEFAULT 0
);

CREATE TABLE if NOT EXISTS slides (
    id_slides BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    image_url VARCHAR(256) NOT NULL,
    text VARCHAR(256),
    slide_order INT NOT NULL, -- 'order' is a keyword in mySQL
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted BIT(1) DEFAULT 0,
    organization_id BIGINT unsigned NOT NULL
);

CREATE TABLE IF NOT EXISTS testimonials (
id BIGINT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR (255) NOT NULL,
image VARCHAR (255),
content VARCHAR (255),
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP,
deleted BIT(1) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS alkemy_ong.categories (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255),
  `image` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT NOW(),
  `updated_at` TIMESTAMP,
  `deleted` BOOLEAN DEFAULT 0
);
