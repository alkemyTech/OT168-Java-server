USE `alkemy_ong`;

LOCK TABLES `alkemy_ong`.`roles` WRITE;
INSERT INTO `alkemy_ong`.`roles` (name, description, created) VALUES ('ADMIN', 'System administrator',NOW());
UNLOCK TABLES;

LOCK TABLES `alkemy_ong`.`users` WRITE;
/*!40000 ALTER TABLE `alkemy_ong`.`users` DISABLE KEYS */;
INSERT INTO `alkemy_ong`.`users` (firstName, lastName, email, password,photo, created, active, fk_role)
VALUES ('Juan','Perez','juan@mail.com',MD5('1234'),'',NOW(),1,1);
/*!40000 ALTER TABLE `alkemy_ong`.`users` ENABLE KEYS */;
UNLOCK TABLES;