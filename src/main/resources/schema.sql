CREATE DATABASE IF NOT EXISTS alkemy_ong;
USE alkemy_ong;

CREATE TABLE IF NOT EXISTS alkemy_ong.categories (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255),
  `image` VARCHAR(255),
  `createdAt` TIMESTAMP DEFAULT NOW(),
  `updateAt` TIMESTAMP,
  `deleted` TINYINT (1)
);