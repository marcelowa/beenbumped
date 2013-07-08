DROP PROCEDURE IF EXISTS sp_createPerson;
DROP PROCEDURE IF EXISTS sp_updatePerson;
DROP PROCEDURE IF EXISTS sp_getPersonById;
DROP PROCEDURE IF EXISTS sp_createUser;
DROP PROCEDURE IF EXISTS sp_updateUser;
DROP PROCEDURE IF EXISTS sp_getUserById;
DROP PROCEDURE IF EXISTS sp_authenticateUser;
DROP PROCEDURE IF EXISTS sp_authorizeUser;
DROP PROCEDURE IF EXISTS sp_createIncident;
DROP PROCEDURE IF EXISTS sp_updateIncident;

-- =============================================
-- Description: Create a new person
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
CREATE PROCEDURE sp_createPerson (
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
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
INSERT INTO beenbumped.t_persons (
	email,
	firstName,
	lastName,
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
DELIMITER ;

-- =============================================
-- Description: update an existing person
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
CREATE PROCEDURE sp_updatePerson (
	IN personIdParam INT UNSIGNED,
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
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
UPDATE beenbumped.t_persons SET
	email						= email,
	firstName				= firstName,
	lastName					= lastName,
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
DELIMITER ;


-- =============================================
-- Description: get an existing person by id
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
CREATE PROCEDURE sp_getPersonById (
	IN personIdParam INT UNSIGNED
)
BEGIN
SELECT
	personId,
	email,
	firstName,
	lastName,
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
	beenbumped.t_persons 
WHERE personId = personIdParam;
END$$
DELIMITER ;

-- =============================================
-- Description: Create a new user
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
CREATE PROCEDURE sp_createUser (
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
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

INSERT INTO beenbumped.t_users (
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
DELIMITER ;

-- =============================================
-- Description: update an existing user
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
CREATE PROCEDURE sp_updateUser (
	IN personIdParam INT UNSIGNED,
	IN userIdParam INT UNSIGNED,
	IN email VARCHAR(320),
	IN firstName VARCHAR(30),
	IN lastName VARCHAR(30),
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
FROM beenbumped.t_users AS u
WHERE
	u.userId				= userIdParam
	AND u.personId		= personIdParam;

IF rowsUpdatedUserOut = 1 THEN
	call sp_updatePerson(
		personIdParam,
		email,
		firstName,
		lastName,
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
DELIMITER ;

-- =============================================
-- Description: get an existing user by id
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
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
	beenbumped.t_users as u
	LEFT JOIN beenbumped.t_persons as p ON u.personId = p.personId
WHERE u.userId = userIdParam;
END$$
DELIMITER ;

-- =============================================
-- Description: authenticate and existing user, update authenticate table and return hash
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
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
	beenbumped.t_users AS u
WHERE u.username = usernameParam
	AND u.password = passwordParam
LIMIT 1;

IF userIdOut > 0 THEN
	SET hashResultOut = password(concat(userIdOut,usernameParam,passwordParam,NOW()));
	INSERT INTO beenbumped.t_authenticate(userId, authHash, expired, created, modified) VALUES(userIdOut, hashResultOut, ADDDATE(NOW(),1), NOW(), NOW());
END IF; 
END$$
DELIMITER ;

-- =============================================
-- Description: authorize by userId and hash
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
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
	beenbumped.t_authenticate AS a
WHERE
	a.userId = userIdParam
	AND a.authHash = hashParam
	AND NOW() < expired
LIMIT 1;

IF authResultOut = true THEN
	UPDATE beenbumped.t_authenticate AS a
	SET expired = ADDDATE(NOW(),1)
	WHERE 
		a.userId = userIdParam
		AND a.authHash = hashParam;
END IF;
END$$
DELIMITER ;

-- =============================================
-- Description: Create a new incident
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
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
INSERT INTO beenbumped.t_incidents (
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


COMMIT;
END$$
DELIMITER ;

-- =============================================
-- Description: Update an existing incident
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
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

UPDATE beenbumped.t_incidents SET
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
DELIMITER ;