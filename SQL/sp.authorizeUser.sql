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