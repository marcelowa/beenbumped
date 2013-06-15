-- =============================================
-- Description: get an existing user by id
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
DROP PROCEDURE IF EXISTS sp_getUserByUsernameAndPassword$$
CREATE PROCEDURE sp_getUserByUsernameAndPassword (
	IN usernameParam VARCHAR(100),
	IN passwordParam VARCHAR(45)
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
	beenbumped.users as u
	LEFT JOIN beenbumped.persons as p ON u.personId = p.personId
WHERE u.username = usernameParam and u.password = passwordParam;

END$$

DELIMITER ;