-- =============================================
-- Description: authorize by userId and hash
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
DROP PROCEDURE IF EXISTS sp_authorizeUser$$
CREATE PROCEDURE sp_authorizeUser (
	IN userIdParam INT UNSIGNED,
	IN hashParam VARCHAR(100),
	OUT authResult BOOLEAN
)
BEGIN

SET authResult = false;

SELECT
	true INTO authResult
FROM
	beenbumped.authenticate AS a
WHERE
	a.userId = userIdParam
	AND a.hash = hashParam
	AND NOW() < expired
LIMIT 1;

IF authResult = true THEN
	UPDATE beenbumped.authenticate AS a
	SET expired = ADDDATE(NOW(),1)
	WHERE 
		a.userId = userIdParam
		AND a.hash = hashParam;
END IF;

END$$

DELIMITER ;