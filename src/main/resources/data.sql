INSERT INTO `activities` (`id`, `name`, `content`, `image`)
VALUES (1, 'Programas Educativos', 'Mediante nuestros programas educativos, buscamos incrementar la capacidad intelectual, moral y afectiva de las personas de acuerdo con la cultura y las normas de convivencia de la sociedad a la que
pertenecen.', 'Foto 1.jpg');

INSERT INTO `activities` (`id`, `name`, `content`, `image`)
VALUES (2, 'Apoyo Escolar para el nivel Primario', 'El espacio de apoyo escolar es el corazón del área educativa. Se realizan los
talleres de lunes a jueves de 10 a 12 horas y de 14 a 16 horas en el contraturno, Los sábados también se realiza el taller para niños y niñas que
asisten a la escuela doble turno. Tenemos un espacio especial para los de 1er grado 2 veces por semana ya que ellos necesitan atención especial!
Actualmente se encuentran inscriptos a este programa 150 niños y niñas de 6 a 15 años.
Este taller está pensado para ayudar a los alumnos con el material que traen de la escuela, también tenemos una docente que les da
clases de lengua y matemática con una planiﬁcación propia que armamos en Manos para nivelar a los niños y que vayan con más herramientas a la
escuela.', 'Foto 2.jpg');

INSERT INTO `activities` (`id`, `name`, `content`, `image`)
VALUES (3, 'Apoyo Escolar Nivel Secundaria', 'Del mismo modo que en primaria, este taller es el corazón del área
secundaria. Se realizan talleres de lunes a viernes de 10 a 12 horas y de 16 a 18 horas en el contraturno. Actualmente se encuentran inscriptos en el taller
50 adolescentes entre 13 y 20 años. Aquí los jóvenes se presentan con el material que traen del colegio y una docente de la institución y un grupo de
voluntarios los recibe para ayudarlos a estudiar o hacer la tarea. Este espacio también es utilizado por los jóvenes como un punto de encuentro y
relación entre ellos y la institución.', 'Foto 3.jpg');

INSERT INTO `activities` (`id`, `name`, `content`, `image`)
VALUES (4, 'Tutorías', 'Es un programa destinado a jóvenes a partir del tercer año de secundaria,
cuyo objetivo es garantizar su permanencia en la escuela y construir un proyecto de vida que da sentido al colegio. El objetivo de esta propuesta es
lograr la integración escolar de niños y jóvenes del barrio promoviendo el soporte socioeducativo y emocional apropiado, desarrollando los recursos
institucionales necesarios a través de la articulación de nuestras intervenciones con las escuelas que los alojan, con las familias de los
alumnos y con las instancias municipales, provinciales y nacionales que correspondan. El programa contempla:
● Encuentro semanal con tutores (Individuales y grupales)
● Actividad proyecto (los participantes del programa deben pensar una actividad relacionada a lo que quieren hacer una vez terminada la
escuela y su tutor los acompaña en el proceso)
● Ayudantías (los que comienzan el 2do años del programa deben elegir una de las actividades que se realizan en la institución para acompañarla e ir conociendo como es el mundo laboral que les
espera).
● Acompañamiento escolar y familiar (Los tutores son encargados de articular con la familia y con las escuelas de los jóvenes para
monitorear el estado de los tutorados)
● Beca estímulo (los jóvenes reciben una beca estímulo que es supervisada por los tutores). Actualmente se encuentran inscriptos en
el programa 30 adolescentes.
● Taller Arte y Cuentos: Taller literario y de manualidades que se realiza semanalmente.
● Paseos recreativos y educativos: Estos paseos están pensados para promover la participación y sentido de pertenencia de los niños, niñas
y adolescentes al área educativa.', 'Foto 4.jpg');

INSERT INTO `alkemy_ong`.`roles` (`id`, `name`, `description`) VALUES (1, 'ADMIN', 'ADMIN');
INSERT INTO `alkemy_ong`.`roles` (`id`, `name`, `description`) VALUES (2, 'USER', 'USER');

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `role_id`) VALUES ('1', 'admin', 'admin', 'admin@gmail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'admin.png', '1');
INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `role_id`) VALUES ('2', 'user', 'user', 'user@gmail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'user.png', '2');


INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(3, 'Sofia', 'Gimenez', 'sofi@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'sofi.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(4, 'Camila', 'Medina', 'cami@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'cami.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(5, 'Victoria', 'Medina', 'vicki@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'vicki.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(6, 'Jorgue', 'Nogueira', 'jorge@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'jorge.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(7, 'Facundo', 'Benitez', 'facu@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'facu.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(8, 'Andrea', 'Reina', 'andre@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'andre.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(9, 'Eliana', 'Paniagua', 'eli@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'eli.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(10, 'Victor', 'Montivero', 'victor@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'victor.jpg', NOW(), NOW(), 0, 1);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(11, 'Mauro', 'Montenegro', 'mauro@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'mauro.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(12, 'Eugenia', 'Flores', 'euge@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'euge.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(13, 'Natalia', 'Ramirez', 'nati@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'nati.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(14, 'Sergio', 'Suarez', 'sergio@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'sergio.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(15, 'Silvia', 'Otaka', 'sil@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'sil.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(16, 'Lucas', 'Spareche', 'lucas@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'lucas.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(17, 'Pamela', 'Diaz', 'pame@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'pame.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(18, 'Manuel', 'Orsini', 'mano@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'mano.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(19, 'Hector', 'Salamanca', 'hector@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'hector.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`users` (`id`, `firstName`, `lastName`, `email`, `password`, `photo`, `createdat`, `updatedat`, `deleted`, `role_id`)
VALUES
(20, 'Matias', 'Montoto', 'mumo@mail.com', '$2a$10$ws3AMXjA5FmNduRJGHFkg.KLqZUZ13K921Q0s72zBaA7ik/T/2/Pi', 'mumo.jpg', NOW(), NOW(), 0, 2);

INSERT INTO `alkemy_ong`.`news` (`news_id`,`name`, `content`, `image`, `deleted`,`type`) VALUES (1,'Médicos Sin Fronteras cumple 50 años', 'La historia de los 13 jóvenes que crearon una ONG gigante.', 'medicos.jpg', '0',"news");
INSERT INTO `alkemy_ong`.`news` (`news_id`,`name`, `content`, `image`, `deleted`,`type`) VALUES (2,'Embajada de Uruguay organizó una feria solidaria', 'Se trató de la exposición y venta de productos elaborados por distintas organizaciones.', 'embajada.jpg', '0',"news");
INSERT INTO `alkemy_ong`.`news` (`news_id`,`name`, `content`, `image`, `deleted`,`type`) VALUES (3,'Proyecto "Libros Solidarios"', 'La Fundación Pequeños Puentes que busca padrinos y madrinas para proyectos literarios.', 'libros.jpg', '0',"news");
INSERT INTO `alkemy_ong`.`news` (`news_id`,`name`, `content`, `image`, `deleted`,`type`) VALUES (4,'ONG logró captar financiamiento en el mercado', 'Sumatoria logró colocar un bono social de forma exitosa.', 'sumatoria.jpg', '0',"news");
INSERT INTO `alkemy_ong`.`news` (`news_id`,`name`, `content`, `image`, `deleted`,`type`) VALUES (5,'El Banco de Alimentos en la pandemia', 'La organización sin fines de lucro registró una suba de donaciones del 66%.', 'banco.jpg', '0',"news");
INSERT INTO `alkemy_ong`.`news` (`news_id`,`name`, `content`, `image`, `deleted`,`type`) VALUES (6,'La ONG que empodera a jóvenes sudafricanos', 'Cuenta con cinco campeonatos anuales por provincia.', 'sudafrica.jpg', '0',"news");
INSERT INTO `alkemy_ong`.`news` (`news_id`,`name`, `content`, `image`, `deleted`,`type`) VALUES (7,'Campaña de salud de ONG', 'La campaña permite acceder a consultas y estudios ginecológicos gratuitos.', 'salud.jpg', '0',"news");

INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (1,'Felicitaciones a los médicos integrantes', '3', '1', '0');
INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (2,'Hermosa feria y productos riquísimos', '8', '2', '0');
INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (3,'¿Dónde puedo donar libros de mis hijos?', '2', '3', '0');
INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (4,'Los precios de los productos fueron bajísimos', '7', '2', '0');
INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (5,'He colaborado toda mi vida con los doctores y lo voy a seguir haciendo', '4', '1', '0');
INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (6,'Ojalá el Estado les diera el financiamiento necesario', '1', '4', '0');
INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (7,'Gracias por esta acción solidaria para los que no tienen de comer', '1', '5', '0');
INSERT INTO `alkemy_ong`.`comments` (`id`,`body`, `user_id`, `news_id`, `deleted`) VALUES (8,'Gracias por este aporte a la salud sexual femennina', '9', '7', '0');

INSERT INTO `alkemy_ong`.`members` (`id`,`name`, `facebookurl`, `instagramurl`, `linkedinurl`, `image`, `description`, `deleted`)
 VALUES
(1,'Jose', 'www.faceJose.com', 'www.instaJose.com', 'www.linkJose', 'jose.jpg', 'Desc Jose', 0);

INSERT INTO `alkemy_ong`.`members` (`id`,`name`, `facebookurl`, `instagramurl`, `linkedinurl`, `image`, `description`, `deleted`)
 VALUES
(2,'Camila', 'www.faceCami.com', 'www.instaCami.com', 'www.linkCami', 'cami.jpg', 'Desc de Camila', 0);