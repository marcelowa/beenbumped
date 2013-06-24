-- =============================================
-- Description: update an existing user
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
DROP PROCEDURE IF EXISTS sp_updateUser$$
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
	IN phone2 VARCHAR(20) ,
	IN insuranceCompany VARCHAR(64),
	IN insuranceAgentName VARCHAR(64),
	IN insurancePhone1 VARCHAR(20),
	IN insurancePhone2 VARCHAR(20),
	IN insuranceNumber VARCHAR(20),
	IN username VARCHAR(100),
	IN password VARCHAR(45),
	OUT rowsUpdatedPersonOut INT UNSIGNED,
	OUT rowsUpdatedUserOut INT UNSIGNED
)
BEGIN

UPDATE beenbumped.t_users SET
	username				= username,
	password				= password,
	modified				= NOW()
WHERE
	userId				= userIdParam
	AND personId		= personIdParam;

SET rowsUpdatedUserOut = ROW_COUNT();

if rowsUpdatedUserOut = 1 THEN
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