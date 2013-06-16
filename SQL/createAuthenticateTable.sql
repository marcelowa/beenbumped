DROP TABLE IF EXISTS beenbumped.authenticate;
CREATE TABLE IF NOT EXISTS beenbumped.authenticate (
	authId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	userId INT UNSIGNED NOT NULL,
	hash VARCHAR(100) NOT NULL,
	expired DATETIME NOT NULL,
	created DATETIME NOT NULL,
	modified DATETIME NOT NULL,
 PRIMARY KEY (authId),
 UNIQUE INDEX authId_UNIQUE (authId ASC));