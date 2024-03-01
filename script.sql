CREATE SCHEMA ejercicio_java;

SET SCHEMA ejercicio_java;

CREATE TABLE Users
(id UUID NOT NULL DEFAULT random_uuid() PRIMARY KEY,
name VARCHAR(50) NULL,
email VARCHAR(50) NULL,
password VARCHAR(50) NULL,
created DATETIME NULL,
modified DATETIME NULL,
last_login DATETIME NULL,
isactive INTEGER NULL);

CREATE TABLE Phones
(id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
number INTEGER NULL,
citycode CHAR(2) NULL,
contrycode CHAR(2) NULL,
user_id VARCHAR(50) NOT NULL,
foreign key (user_id) references Users(id),
created DATETIME NULL,
modified DATETIME NULL);