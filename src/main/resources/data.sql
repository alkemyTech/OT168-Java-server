USE `alkemy_ong`;

LOCK TABLES `alkemy_ong`.`categories` WRITE;
/* ALTER TABLE `alkemy_ong`.`categories` DISABLE KEYS */;
INSERT INTO `alkemy_ong`.`categories` (name, description, image, createdAt, updateAt, deleted)
VALUES ('Aventuras','Prueba Aventura',,NOW(),NOW(),0);
/* ALTER TABLE `alkemy_ong`.`categories` ENABLE KEYS */;
UNLOCK TABLES;