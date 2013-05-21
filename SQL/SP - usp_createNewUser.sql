-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE `beenbumped`.`usp_createNewUser` 
	(
		`userName` VARCHAR(100),
		`email` VARCHAR(320),
		`password` VARCHAR(45),
		`firstName` VARCHAR(30),
		`lastName` VARCHAR(30),
		`city` VARCHAR(30),
		`streetName` VARCHAR(50),
		`houseNumber` SMALLINT,
		`addressDetails` VARCHAR(64),
		`zipcode` INT,
		`phone1` VARCHAR(20),
		`phone2` VARCHAR(20)
	)
BEGIN
/**
-- =============================================
-- Author:  	Avishai Mizrahi
-- Create date: 19/05/2013
-- Description: Create a new user
-- Example call: call usp_createNewUser ('test1','t@t.com','6F9619FF-8B86-D011-B42D-00C04FC964FF','first','last','pump','dur','10','walla walla wo','5555','050-5557777','054-5557777')
-- =============================================
**/

INSERT INTO `beenbumped`.`users` 
	(
		`userName`,
		`email`,
		`password` ,
		`firstName` ,
		`lastName` ,
		`city` ,
		`streetName` ,
		`houseNumber` ,
		`addressDetails` ,
		`zipcode` ,
		`phone1` ,
		`phone2`
	)
VALUES
	(
		`userName`,
		`email`,
		`password` ,
		`firstName` ,
		`lastName` ,
		`city` ,
		`streetName` ,
		`houseNumber` ,
		`addressDetails` ,
		`zipcode` ,
		`phone1` ,
		`phone2`
	);

END$$

DELIMITER;