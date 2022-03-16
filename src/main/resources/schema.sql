DROP DATABASE IF EXISTS alkemy_ong;
CREATE DATABASE IF NOT EXISTS alkemy_ong;
USE alkemy_ong;

DROP TABLE IF EXISTS alkemy_ong.news;
CREATE TABLE alkemy_ong.news (
'news_id' BIGINT(255) NOT NULL AUTO_INCREMENT PRIMARY KEY,
'name' VARCHAR (20) NOT NULL,
'content' VARCHAR (100) NOT NULL,
'image' VARCHAR (25) NOT NULL,
'create_at' TIMESTAMP,
'update_at' TIMESTAMP,
'status' TINYINT,
)