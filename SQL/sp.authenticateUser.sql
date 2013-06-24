-- =============================================
-- Description: authenticate and existing user, update authenticate table and return hash
-- Note: all arguments must be passed to the proc since mysql doesn't support optional arguments
-- =============================================
DELIMITER $$
USE beenbumped$$
DROP PROCEDURE IF EXISTS sp_authenticateUser$$
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