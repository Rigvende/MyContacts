-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema new_schema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema new_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `new_schema` DEFAULT CHARACTER SET utf8mb4 ;
-- -----------------------------------------------------
-- Schema contacts_patrusova
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema contacts_patrusova
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `contacts_patrusova` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
USE `new_schema` ;

-- -----------------------------------------------------
-- Table `new_schema`.`new_table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `new_schema`.`new_table` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ff` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `contacts_patrusova` ;

-- -----------------------------------------------------
-- Table `contacts_patrusova`.`photos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_patrusova`.`photos` (
  `id_photo` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `photo_path` VARCHAR(255) NOT NULL DEFAULT '',
  `photo_name` VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_photo`))
ENGINE = InnoDB
AUTO_INCREMENT = 142
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `contacts_patrusova`.`contacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_patrusova`.`contacts` (
  `id_contact` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `contact_name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL DEFAULT '',
  `birthday` DATE NULL DEFAULT NULL,
  `gender` SET('male', 'female', 'unknown') NOT NULL DEFAULT 'unknown',
  `citizenship` VARCHAR(45) NOT NULL DEFAULT '',
  `family_status` VARCHAR(45) NOT NULL DEFAULT '',
  `website` VARCHAR(255) NOT NULL DEFAULT '',
  `email` VARCHAR(255) NOT NULL DEFAULT '',
  `work_place` VARCHAR(100) NOT NULL DEFAULT '',
  `country` VARCHAR(45) NOT NULL DEFAULT '',
  `city` VARCHAR(45) NOT NULL DEFAULT '',
  `address` VARCHAR(45) NOT NULL DEFAULT '',
  `zipcode` VARCHAR(9) NOT NULL DEFAULT '',
  `id_photo` BIGINT(20) UNSIGNED NOT NULL,
  `deleted` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id_contact`),
  INDEX `contact_photo_idx` (`id_photo` ASC) VISIBLE,
  CONSTRAINT `contact_photo`
    FOREIGN KEY (`id_photo`)
    REFERENCES `contacts_patrusova`.`photos` (`id_photo`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 225
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `contacts_patrusova`.`attachments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_patrusova`.`attachments` (
  `id_attachment` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `attachment_path` VARCHAR(255) NOT NULL,
  `attachment_name` VARCHAR(255) NOT NULL,
  `load_date` DATE NOT NULL,
  `comments` VARCHAR(255) NOT NULL DEFAULT '',
  `id_contact` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_attachment`),
  INDEX `attachment_contact_idx` (`id_contact` ASC) VISIBLE,
  CONSTRAINT `attachment_contact`
    FOREIGN KEY (`id_contact`)
    REFERENCES `contacts_patrusova`.`contacts` (`id_contact`))
ENGINE = InnoDB
AUTO_INCREMENT = 83
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `contacts_patrusova`.`phones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contacts_patrusova`.`phones` (
  `id_phone` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_contact` BIGINT(20) UNSIGNED NOT NULL,
  `country_code` VARCHAR(4) NOT NULL DEFAULT '',
  `operator_code` VARCHAR(4) NOT NULL DEFAULT '',
  `phone_number` VARCHAR(10) NOT NULL DEFAULT '',
  `phone_type` SET('домашний', 'рабочий', 'мобильный', 'иной') NOT NULL DEFAULT 'иной',
  `comments` VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_phone`),
  INDEX `phone_contact_idx` (`id_contact` ASC) VISIBLE,
  CONSTRAINT `phone_contact`
    FOREIGN KEY (`id_contact`)
    REFERENCES `contacts_patrusova`.`contacts` (`id_contact`))
ENGINE = InnoDB
AUTO_INCREMENT = 98
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
