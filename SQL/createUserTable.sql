CREATE  TABLE `beenbumped`.`Users` (

 `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT ,

 `userName` VARCHAR(100) NOT NULL,

 `email` VARCHAR(320) NOT NULL ,

 `password` VARCHAR(45) NOT NULL ,

 `firstName` VARCHAR(30) NOT NULL ,

 `lastName` VARCHAR(30) NOT NULL ,

 `city` VARCHAR(30) NULL ,

 `streetName` VARCHAR(50) NULL ,

 `houseNumber` SMALLINT NULL ,

 `addressDetails` VARCHAR(64) NULL ,

 `zipcode` INT NULL ,

 `phone1` VARCHAR(20) NULL ,

 `phone2` VARCHAR(20) NULL ,

 PRIMARY KEY (`userId`) ,

 UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) );