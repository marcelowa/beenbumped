-- =============================================
-- Description: update an existing person
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
DROP PROCEDURE IF EXISTS sp_updatePerson$$
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