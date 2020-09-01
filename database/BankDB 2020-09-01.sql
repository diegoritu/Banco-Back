
CREATE DATABASE `bankdb`;

USE `bankdb`;

/*Table structure for table `accounts` */


CREATE TABLE `accounts` (
  `id_account` INT NOT NULL AUTO_INCREMENT,
  `account_number` VARCHAR(32) NOT NULL,
  `balance` FLOAT NOT NULL,
  `alias` VARCHAR(24) NOT NULL,
  `cbu` VARCHAR(32) NOT NULL,
  `max_overdraft` FLOAT DEFAULT NULL,
  `interest_rate` FLOAT DEFAULT NULL,
  `account_type` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id_account`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `accounts` */

/*Table structure for table `movements` */


CREATE TABLE `movements` (
  `id_movement` INT NOT NULL AUTO_INCREMENT,
  `movement_type` TINYINT NOT NULL,
  `day_and_hour` DATETIME NOT NULL,
  `concept` VARCHAR(100) NOT NULL,
  `amount` FLOAT NOT NULL,
  `transaction_number` INT DEFAULT NULL,
  `id_service` INT DEFAULT NULL,
  `id_entry_account` INT DEFAULT NULL,
  `id_exit_account` INT DEFAULT NULL,
  `entry_balance_before_movement` FLOAT NOT NULL,
  `exit_balance_before_movement` FLOAT NOT NULL,
  PRIMARY KEY (`id_movement`),
  KEY `service_fk` (`id_service`),
  KEY `entryAccount` (`id_entry_account`),
  KEY `exitAccount` (`id_exit_account`),
  CONSTRAINT `entry_account_fk` FOREIGN KEY (`id_entry_account`) REFERENCES `accounts` (`id_account`),
  CONSTRAINT `exitAccount_fk` FOREIGN KEY (`id_exit_account`) REFERENCES `accounts` (`id_account`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `movements` */

/*Table structure for table `services` */


CREATE TABLE `services` (
  `id_service` INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `amount` FLOAT NOT NULL,
  `due` DATE NOT NULL,
  PRIMARY KEY (`id_service`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `services` */

/*Table structure for table `users` */


CREATE TABLE `users` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `cuit_cuil_cdi` VARCHAR(12) NOT NULL,
  `user_type` TINYINT NOT NULL,
  `usr` VARCHAR(50) NOT NULL,
  `address` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(32) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `dni` VARCHAR(8) DEFAULT NULL,
  `id_savings_account` INT DEFAULT NULL,
  `id_checking_account` INT DEFAULT NULL,
  `birthdate` DATE DEFAULT NULL,
  `name` VARCHAR(50) DEFAULT NULL,
  `business_name` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  KEY `checking_fk` (`id_checking_account`),
  KEY `savings_fk` (`id_savings_account`),
  CONSTRAINT `checking_fk` FOREIGN KEY (`id_checking_account`) REFERENCES `accounts` (`id_account`),
  CONSTRAINT `savings_fk` FOREIGN KEY (`id_savings_account`) REFERENCES `accounts` (`id_account`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */
