-- =============================================
-- Description: Create a new person
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
DROP PROCEDURE IF EXISTS sp_createUser$$
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

SET personIdOut	= 0;
SET userIdOut		= 0;
SET authHashOut	= "";

DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
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