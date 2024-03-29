-- SQL/stored-procedures.sql
-- DELIMITER $$

DROP PROCEDURE IF EXISTS sp_createPerson$$
DROP PROCEDURE IF EXISTS sp_updatePerson$$
DROP PROCEDURE IF EXISTS sp_getPersonById$$
DROP PROCEDURE IF EXISTS sp_createUser$$
DROP PROCEDURE IF EXISTS sp_updateUser$$
DROP PROCEDURE IF EXISTS sp_getUserById$$
DROP PROCEDURE IF EXISTS sp_authenticateUser$$
DROP PROCEDURE IF EXISTS sp_authorizeUser$$
DROP PROCEDURE IF EXISTS sp_createIncident$$
DROP PROCEDURE IF EXISTS sp_updateIncident$$
DROP PROCEDURE IF EXISTS sp_getIncidentById$$
DROP PROCEDURE IF EXISTS sp_getIncidentHistory$$


-- =============================================
-- Description: Create a new person
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_createPerson (
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
	IN idNumber VARCHAR(10),
	IN city VARCHAR(30),
	IN streetName VARCHAR(50),
	IN houseNumber SMALLINT,
	IN addressDetails VARCHAR(64),
	IN zipcode INT,
	IN phone1 VARCHAR(20),
	IN phone2 VARCHAR(20) ,
	IN insuranceCompany VARCHAR(64),
	IN insuranceAgentName VARCHAR(64),
	IN insurancePhone1 VARCHAR(20),
	IN insurancePhone2 VARCHAR(20),
	IN insuranceNumber VARCHAR(20),
	OUT personIdOut INT UNSIGNED
)
BEGIN
INSERT INTO t_persons (
	email,
	firstName,
	lastName,
	idNumber,
	city,
	streetName,
	houseNumber,
	addressDetails,
	zipcode,
	phone1,
	phone2,
	insuranceCompany,
	insuranceAgentName,
	insurancePhone1,
	insurancePhone2,
	insuranceNumber,
	created,
	modified
)
VALUES (
	email,
	firstName,
	lastName,
	idNumber,
	city,
	streetName,
	houseNumber,
	addressDetails,
	zipcode,
	phone1,
	phone2,
	insuranceCompany,
	insuranceAgentName,
	insurancePhone1,
	insurancePhone2,
	insuranceNumber,
	NOW(),
	NOW()
);
SET personIdOut = LAST_INSERT_ID();
END$$

-- =============================================
-- Description: update an existing person
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_updatePerson (
	IN personIdParam INT UNSIGNED,
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
	IN idNumber VARCHAR(10),
	IN city VARCHAR(30),
	IN streetName VARCHAR(50),
	IN houseNumber SMALLINT,
	IN addressDetails VARCHAR(64),
	IN zipcode INT,
	IN phone1 VARCHAR(20),
	IN phone2 VARCHAR(20) ,
	IN insuranceCompany VARCHAR(64),
	IN insuranceAgentName VARCHAR(64),
	IN insurancePhone1 VARCHAR(20),
	IN insurancePhone2 VARCHAR(20),
	IN insuranceNumber VARCHAR(20),
	OUT rowsUpdatedOut INT UNSIGNED
)
BEGIN
UPDATE t_persons SET
	email						= email,
	firstName				= firstName,
	lastName					= lastName,
	idNumber					= idNumber,
	city						= city,
	streetName				= streetName,
	houseNumber				= houseNumber,
	addressDetails			= addressDetails,
	zipcode					= zipcode,
	phone1					= phone1,
	phone2					= phone2,
	insuranceCompany		= insuranceCompany,
	insuranceAgentName	= insuranceAgentName,
	insurancePhone1		= insurancePhone1,
	insurancePhone2		= insurancePhone2,
	insuranceNumber		= insuranceNumber,
	modified					= NOW()
WHERE
	personId					= personIdParam;
SET rowsUpdatedOut 		= ROW_COUNT();
END$$


-- =============================================
-- Description: get an existing person by id
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_getPersonById (
	IN personIdParam INT UNSIGNED
)
BEGIN
SELECT
	personId,
	email,
	firstName,
	lastName,
	idNumber,
	city,
	streetName,
	houseNumber,
	addressDetails,
	zipcode,
	phone1,
	phone2,
	insuranceCompany,
	insuranceAgentName,
	insurancePhone1,
	insurancePhone2,
	insuranceNumber,
	created,
	modified
FROM
	t_persons 
WHERE personId = personIdParam;
END$$

-- =============================================
-- Description: Create a new user
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_createUser (
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
	IN idNumber	VARCHAR(10),
	IN city VARCHAR(30),
	IN streetName VARCHAR(50),
	IN houseNumber SMALLINT,
	IN addressDetails VARCHAR(64),
	IN zipcode INT,
	IN phone1 VARCHAR(20),
	IN phone2 VARCHAR(20) ,
	IN insuranceCompany VARCHAR(64),
	IN insuranceAgentName VARCHAR(64),
	IN insurancePhone1 VARCHAR(20),
	IN insurancePhone2 VARCHAR(20),
	IN insuranceNumber VARCHAR(20),
	IN username VARCHAR(100),
	IN password VARCHAR(45),
	OUT personIdOut INT UNSIGNED,
	OUT userIdOut INT UNSIGNED,
	OUT authHashOut VARCHAR(100)
)
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

SET personIdOut	= 0;
SET userIdOut		= 0;
SET authHashOut	= "";

START TRANSACTION;

call sp_createPerson(
	email,
	firstName,
	lastName,
	idNumber,
	city,
	streetName,
	houseNumber,
	addressDetails,
	zipcode,
	phone1,
	phone2,
	insuranceCompany,
	insuranceAgentName,
	insurancePhone1,
	insurancePhone2,
	insuranceNumber,
	personIdOut
);

INSERT INTO t_users (
	personId,
	username,
	password,
	created,
	modified
)
VALUES (
	personIdOut,
	username,
	password,
	NOW(),
	NOW()
);
call sp_authenticateUser(username, password, userIdOut, authHashOut);
COMMIT;
END$$

-- =============================================
-- Description: update an existing user
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_updateUser (
	IN personIdParam INT UNSIGNED,
	IN userIdParam INT UNSIGNED,
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
	IN idNumber	VARCHAR(10),
	IN city VARCHAR(30),
	IN streetName VARCHAR(50),
	IN houseNumber SMALLINT,
	IN addressDetails VARCHAR(64),
	IN zipcode INT,
	IN phone1 VARCHAR(20),
	IN phone2 VARCHAR(20),
	IN insuranceCompany VARCHAR(64),
	IN insuranceAgentName VARCHAR(64),
	IN insurancePhone1 VARCHAR(20),
	IN insurancePhone2 VARCHAR(20),
	IN insuranceNumber VARCHAR(20),
	OUT rowsUpdatedPersonOut INT UNSIGNED,
	OUT rowsUpdatedUserOut INT UNSIGNED
)
BEGIN

SET rowsUpdatedUserOut = 0;

SELECT COUNT(u.userID) INTO rowsUpdatedUserOut
FROM t_users AS u
WHERE
	u.userId				= userIdParam
	AND u.personId		= personIdParam;

IF rowsUpdatedUserOut = 1 THEN
	call sp_updatePerson(
		personIdParam,
		email,
		firstName,
		lastName,
		idNumber,
		city,
		streetName,
		houseNumber,
		addressDetails,
		zipcode,
		phone1,
		phone2,
		insuranceCompany,
		insuranceAgentName,
		insurancePhone1,
		insurancePhone2,
		insuranceNumber,
		rowsUpdatedPersonOut
	);
END IF;
END$$

-- =============================================
-- Description: get an existing user by id
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_getUserById (
	IN userIdParam INT UNSIGNED
)
BEGIN
SELECT
	u.userId,
	p.personId,
	u.username,
	u.password,
	p.email,
	p.firstName,
	p.lastName,
	P.idNumber,
	p.city,
	p.streetName,
	p.houseNumber,
	p.addressDetails,
	p.zipcode,
	p.phone1,
	p.phone2,
	p.insuranceCompany,
	p.insuranceAgentName,
	p.insurancePhone1,
	p.insurancePhone2,
	p.insuranceNumber,
	u.created,
	u.modified
FROM
	t_users as u
	LEFT JOIN t_persons as p ON u.personId = p.personId
WHERE u.userId = userIdParam;
END$$

-- =============================================
-- Description: authenticate and existing user, update authenticate table and return hash
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_authenticateUser (
	IN usernameParam VARCHAR(100),
	IN passwordParam VARCHAR(45),
	OUT userIdOut INT UNSIGNED,
	OUT hashResultOut VARCHAR(100)
)
BEGIN

SET userIdOut = 0;
SET hashResultOut = "";

SELECT
	u.userId INTO userIdOut
FROM
	t_users AS u
WHERE u.username = usernameParam
	AND u.password = passwordParam
LIMIT 1;

IF userIdOut > 0 THEN
	SET hashResultOut = password(concat(userIdOut,usernameParam,passwordParam,NOW()));
	INSERT INTO t_authenticate(userId, authHash, expired, created, modified) VALUES(userIdOut, hashResultOut, ADDDATE(NOW(),1), NOW(), NOW());
END IF; 
END$$

-- =============================================
-- Description: authorize by userId and hash
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_authorizeUser (
	IN userIdParam INT UNSIGNED,
	IN hashParam VARCHAR(100),
	OUT authResultOut BOOLEAN
)
BEGIN

SET authResultOut = false;

SELECT
	true INTO authResultOut
FROM
	t_authenticate AS a
WHERE
	a.userId = userIdParam
	AND a.authHash = hashParam
	AND NOW() < expired
LIMIT 1;

IF authResultOut = true THEN
	UPDATE t_authenticate AS a
	SET expired = ADDDATE(NOW(),1)
	WHERE 
		a.userId = userIdParam
		AND a.authHash = hashParam;
END IF;
END$$

-- =============================================
-- Description: Create a new incident
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_createIncident (
	IN userId INT UNSIGNED,
	IN date DATETIME,
	IN notes TEXT,
	IN location VARCHAR(128),
	IN vehicleLicensePlate VARCHAR(64),
	IN vehicleBrand VARCHAR(64),
	IN vehicleModel VARCHAR(64),
	IN driverPersonId INT UNSIGNED,
	IN ownerPersonId INT UNSIGNED,
	OUT incidentIdOut INT UNSIGNED
)
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;

SET incidentIdOut = 0;

START TRANSACTION;

-- create incident
INSERT INTO t_incidents (
	userId,
	date,
	notes,
	location,
	vehicleLicensePlate,
	vehicleBrand,
	vehicleModel,
	driverPersonId,
	ownerPersonId,
	created,
	modified
)
VALUES (
	userId,
	date,
	notes,
	location,
	vehicleLicensePlate,
	vehicleBrand,
	vehicleModel,
	driverPersonId,
	ownerPersonId,
	NOW(),
	NOW()
);

SET incidentIdOut = LAST_INSERT_ID();

COMMIT;
END$$

-- =============================================
-- Description: Update an existing incident
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_updateIncident (
	IN incidentIdParam INT UNSIGNED,
	IN userIdParam INT UNSIGNED,
	IN date DATETIME,
	IN notes TEXT,
	IN location VARCHAR(128),
	IN vehicleLicensePlate VARCHAR(64),
	IN vehicleBrand VARCHAR(64),
	IN vehicleModel VARCHAR(64),
	IN driverPersonId INT UNSIGNED,
	IN ownerPersonId INT UNSIGNED,
	OUT rowsUpdatedOut INT UNSIGNED
)
BEGIN
SET rowsUpdatedOut = 0;

UPDATE t_incidents SET
	date						= date,
	location					= location,
	vehicleLicensePlate	= vehicleLicensePlate,
	vehicleBrand			= vehicleBrand,
	vehicleModel			= vehicleModel,
	driverPersonId			= driverPersonId,
	ownerPersonId			= ownerPersonId,
	modified					= NOW()
WHERE
	incidentId				= incidentIdParam
	AND userId				= userIdParam;
SET rowsUpdatedOut 		= ROW_COUNT();

END$$

-- =============================================
-- Description: get an existing person by id
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================

CREATE PROCEDURE sp_getIncidentById (
	IN incidentIdParam INT UNSIGNED,
	IN userIdParam INT UNSIGNED
)
BEGIN
SELECT
	incidentId,
	userId,
	date,
	notes,
	vehicleLicensePlate,
	vehicleBrand,
	vehicleModel,
	driverPersonId,
	ownerPersonId,
	created,
	modified
FROM
	t_incidents
WHERE incidentId = incidentIdParam
	AND userId = userIdParam;

END$$

-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_getIncidentHistory`(
	IN userId INT,
	IN pageNumber SMALLINT,
	IN linesInPage SMALLINT,
	OUT numberOfLines SMALLINT
)
BEGIN

declare skip smallint;
declare rows smallint;

/** this Sp will return all the incidents a user have with a pager
and cos mysql is @$^# I have to use dynamic statment*/

SET @userIdInput = userId;

IF pageNumber <= 1 then
	set @skip = 0;
	set @rows = linesInPage;
ELSE
	set @skip = linesInPage*(pageNumber-1);
	set @rows = linesInPage*(pageNumber);
END IF;

PREPARE STMT FROM
'SET @numberOfLines = (SELECT count(*) FROM t_incidents WHERE userId = ?)';
EXECUTE STMT USING @userIdInput;
set numberOfLines = @numberOfLines;

PREPARE STMT FROM 
'SELECT incidentId, userId, date, notes, location, vehicleLicensePlate, vehicleBrand, vehicleModel, driverPersonId, ownerPersonId
FROM t_incidents
WHERE userId = ?
LIMIT ?,?';

EXECUTE STMT USING @userIdInput,@skip,@rows;

END$$
