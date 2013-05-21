DELIMITER $$
USE `beenbumped`$$
CREATE PROCEDURE `beenbumped`.`usp_userAuthentication` 
	(
	`uId` INT,
	`pass` VARCHAR(45),
	OUT `result` BOOLEAN
	)
BEGIN
/**
-- =============================================
-- Author:  	Avishai Mizrahi
-- Create date: 19/05/2013
-- Description: Verify the user password
-- Example call: SET @uId = 1;
--				 SET @pass = '6F9619FF-8B86-D011-B42D-00C04FC964FF';
--				 PREPARE s FROM 'CALL usp_userAuthentication(@uId, @pass,@result)';
--				 EXECUTE s;
--				 SELECT @uId, @pass,@result;
-- =============================================
**/

SET `result` = IF ( (SELECT `password`
					 FROM `beenbumped`.`users`
					 WHERE `userId` = `uId`
					) = `pass`, true,false
				  );

END$$

DELIMITER ;