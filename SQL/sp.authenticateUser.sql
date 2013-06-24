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
	OUT userIdResult INT UNSIGNED,
	OUT hashResult VARCHAR(100)
)
BEGIN

SET userIdResult = -1;
SET hashResult = "";

SELECT
	u.userId INTO userIdResult
FROM
	beenbumped.users AS u
WHERE u.username = usernameParam
	AND u.password = passwordParam
LIMIT 1;

IF userIdResult > 0 THEN
	SET hashResult = password(concat(userIdResult,usernameParam,passwordParam,NOW()));
	INSERT INTO beenbumped.authenticate(userId, hash, expired, created, modified) VALUES(userIdResult, hashResult, ADDDATE(NOW(),1), NOW(), NOW());
END IF;
 
END$$

DELIMITER ;