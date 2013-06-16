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
	OUT hashResult VARCHAR(100),
	OUT expiredResult DATETIME
)
BEGIN
SELECT
	u.userId INTO userIdResult
FROM
	beenbumped.users as u
WHERE u.username = usernameParam and u.password = passwordParam
LIMIT 1;

IF userIdResult IS NULL THEN
	SET HashResult = NULL;
	SET expiredResult = NULL;
ELSE 
	SET HashResult = password(concat(userIdResult,usernameParam,passwordParam,NOW()));
	SET expiredResult = ADDDATE(NOW(),1);
	INSERT INTO beenbumped.authenticate(userId, hash, expired, created, modified) VALUES(userIdResult, hashResult, expiredResult, NOW(), NOW());
END IF;
 

END$$

DELIMITER ;