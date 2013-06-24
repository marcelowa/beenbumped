-- =============================================
-- Description: get an existing person by id
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
DROP PROCEDURE IF EXISTS sp_getPersonById$$
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