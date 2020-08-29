
CREATE DATABASE `bankdb`;

USE `bankdb`;

/*Table structure for table `accounts` */


CREATE TABLE `accounts` (
  `idAccount` INT NOT NULL AUTO_INCREMENT,
  `accountNumber` VARCHAR(32) NOT NULL,
  `balance` FLOAT NOT NULL,
  `alias` VARCHAR(24) NOT NULL,
  `cbu` VARCHAR(32) NOT NULL,
  `maxOverdraft` FLOAT DEFAULT NULL,
  `interestRate` FLOAT DEFAULT NULL,
  `accountType` TINYINT(1) NOT NULL,
  PRIMARY KEY (`idAccount`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `accounts` */

/*Table structure for table `movements` */


CREATE TABLE `movements` (
  `idMovement` INT NOT NULL AUTO_INCREMENT,
  `movementType` TINYINT NOT NULL,
  `dayAndHour` DATETIME NOT NULL,
  `concept` VARCHAR(100) NOT NULL,
  `amount` FLOAT NOT NULL,
  `transactionNumber` INT DEFAULT NULL,
  `idService` INT DEFAULT NULL,
  `idEntryAccount` INT DEFAULT NULL,
  `idExitAccount` INT DEFAULT NULL,
  `entryBalanceBeforeMovement` FLOAT NOT NULL,
  `exitBalanceBeforeMovement` FLOAT NOT NULL,
  PRIMARY KEY (`idMovement`),
  KEY `service_fk` (`idService`),
  KEY `entryAccount` (`idEntryAccount`),
  KEY `exitAccount` (`idExitAccount`),
  CONSTRAINT `entryAccount` FOREIGN KEY (`idEntryAccount`) REFERENCES `accounts` (`idAccount`),
  CONSTRAINT `exitAccount` FOREIGN KEY (`idExitAccount`) REFERENCES `accounts` (`idAccount`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `movements` */

/*Table structure for table `services` */


CREATE TABLE `services` (
  `idService` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `amount` FLOAT NOT NULL,
  `due` DATE NOT NULL,
  PRIMARY KEY (`idService`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `services` */

/*Table structure for table `users` */


CREATE TABLE `users` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `cuitCuilCdi` VARCHAR(12) NOT NULL,
  `userType` TINYINT NOT NULL,
  `usr` VARCHAR(50) NOT NULL,
  `address` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(32) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `dni` VARCHAR(8) DEFAULT NULL,
  `idSavingsAccount` INT DEFAULT NULL,
  `idCheckingAccount` INT DEFAULT NULL,
  `birthday` DATE DEFAULT NULL,
  `name` VARCHAR(50) DEFAULT NULL,
  `businessName` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  KEY `checking_fk` (`idCheckingAccount`),
  KEY `savings_fk` (`idSavingsAccount`),
  CONSTRAINT `checking_fk` FOREIGN KEY (`idCheckingAccount`) REFERENCES `accounts` (`idAccount`),
  CONSTRAINT `savings_fk` FOREIGN KEY (`idSavingsAccount`) REFERENCES `accounts` (`idAccount`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */
