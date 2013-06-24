-- create tables

DROP TABLE IF EXISTS beenbumped.t_users;
DROP TABLE IF EXISTS beenbumped.t_persons;
DROP TABLE IF EXISTS beenbumped.t_authenticate;

CREATE TABLE IF NOT EXISTS beenbumped.t_persons (
	personId INT UNSIGNED NOT NULL AUTO_INCREMENT ,
	email VARCHAR(320) NOT NULL ,
	firstName VARCHAR(30) NOT NULL ,
	lastName VARCHAR(30) NOT NULL ,
	city VARCHAR(30) NULL ,
	streetName VARCHAR(50) NULL ,
	houseNumber SMALLINT NULL ,
	addressDetails VARCHAR(64) NULL ,
	zipcode INT NULL ,
	phone1 VARCHAR(20) NULL ,
	phone2 VARCHAR(20) NULL ,
	insuranceCompany VARCHAR(64) NULL ,
	insuranceAgentName VARCHAR(64) NULL ,
	insurancePhone1 VARCHAR(20) NULL ,
	insurancePhone2 VARCHAR(20) NULL ,
	insuranceNumber VARCHAR(20) NULL ,
	created DATETIME NOT NULL ,
	modified DATETIME NOT NULL ,
 PRIMARY KEY (personId) ,
 UNIQUE INDEX personId_UNIQUE (personId ASC));
 
CREATE TABLE IF NOT EXISTS beenbumped.t_users (
	userId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	personId INT UNSIGNED NOT NULL,
	username VARCHAR(100) NOT NULL,
	password VARCHAR(45) NOT NULL,
	created DATETIME NOT NULL,
	modified DATETIME NOT NULL,
	PRIMARY KEY (userId),
	CONSTRAINT fk_users_persons FOREIGN KEY (personId) REFERENCES t_persons(personId),
	UNIQUE INDEX userId_UNIQUE (userId ASC),
	UNIQUE INDEX personId_UNIQUE (personId ASC),
	UNIQUE INDEX username_UNIQUE (username ASC)
);

CREATE TABLE IF NOT EXISTS beenbumped.t_authenticate (
	authId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	userId INT UNSIGNED NOT NULL,
	authHash VARCHAR(100) NOT NULL,
	expired DATETIME NOT NULL,
	created DATETIME NOT NULL,
	modified DATETIME NOT NULL,
 PRIMARY KEY (authId),
 UNIQUE INDEX authId_UNIQUE (authId ASC));