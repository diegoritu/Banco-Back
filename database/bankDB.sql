CREATE DATABASE `bankdb`;

USE `bankdb`;

/*Table structure for table `checking_accounts` */


CREATE TABLE `checking_accounts` (
  `idCheckingAccount` INT NOT NULL AUTO_INCREMENT,
  `accountNumber` VARCHAR(32) NOT NULL,
  `balance` FLOAT NOT NULL,
  `alias` VARCHAR(24) NOT NULL,
  `cbu` VARCHAR(32) NOT NULL,
  `maxOverdraft` FLOAT NOT NULL,
  PRIMARY KEY (`idCheckingAccount`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `checking_accounts` */

/*Table structure for table `savings_accounts` */


CREATE TABLE `savings_accounts` (
  `idSavingsAccount` INT NOT NULL AUTO_INCREMENT,
  `accountNumber` VARCHAR(32) NOT NULL,
  `balance` FLOAT NOT NULL,
  `alias` VARCHAR(24) NOT NULL,
  `cbu` VARCHAR(32) NOT NULL,
  `interestRate` FLOAT NOT NULL,
  PRIMARY KEY (`idSavingsAccount`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `savings_accounts` */

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
  `username` VARCHAR(50) NOT NULL,
  `address` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(32) NOT NULL,
  `mobilePhone` VARCHAR(32) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `dni` VARCHAR(8) DEFAULT NULL,
  `idSavingsAccount` INT DEFAULT NULL,
  `idCheckingAccount` INT DEFAULT NULL,
  `birthDate` DATE DEFAULT NULL,
  `firstName` VARCHAR(50) DEFAULT NULL,
  `lastName` VARCHAR(50) DEFAULT NULL,
  `businessName` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  KEY `savings_fk` (`idSavingsAccount`),
  KEY `checking_fk` (`idCheckingAccount`),
  CONSTRAINT `checking_fk` FOREIGN KEY (`idCheckingAccount`) REFERENCES `checking_accounts` (`idCheckingAccount`),
  CONSTRAINT `savings_fk` FOREIGN KEY (`idSavingsAccount`) REFERENCES `savings_accounts` (`idSavingsAccount`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

/*Table structure for table `movements` */


CREATE TABLE `movements` (
  `idMovement` INT NOT NULL AUTO_INCREMENT,
  `movementType` TINYINT NOT NULL,
  `dayAndHour` DATETIME NOT NULL,
  `concept` VARCHAR(100) NOT NULL,
  `amount` FLOAT NOT NULL,
  `transactionNumber` INT DEFAULT NULL,
  `idService` INT DEFAULT NULL,
  `idChEntryAccount` INT DEFAULT NULL,
  `idChExitAccount` INT DEFAULT NULL,
  `idSaEntryAccount` INT DEFAULT NULL,
  `idSaExitAccount` INT DEFAULT NULL,
  `entryBalanceBeforeMovement` FLOAT NOT NULL,
  `exitBalanceBeforeMovement` FLOAT NOT NULL,
  PRIMARY KEY (`idMovement`),
  KEY `service_fk` (`idService`),
  KEY `checkingEntry_fk` (`idChEntryAccount`),
  KEY `checkingExit_fk` (`idChExitAccount`),
  KEY `savingsEntry_fk` (`idSaEntryAccount`),
  KEY `savingsExit_fk` (`idSaExitAccount`),
  CONSTRAINT `checkingEntry_fk` FOREIGN KEY (`idChEntryAccount`) REFERENCES `checking_accounts` (`idCheckingAccount`),
  CONSTRAINT `checkingExit_fk` FOREIGN KEY (`idChExitAccount`) REFERENCES `checking_accounts` (`idCheckingAccount`),
  CONSTRAINT `savingsEntry_fk` FOREIGN KEY (`idSaEntryAccount`) REFERENCES `savings_accounts` (`idSavingsAccount`),
  CONSTRAINT `savingsExit_fk` FOREIGN KEY (`idSaExitAccount`) REFERENCES `savings_accounts` (`idSavingsAccount`),
  CONSTRAINT `service_fk` FOREIGN KEY (`idService`) REFERENCES `services` (`idService`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `movements` */
