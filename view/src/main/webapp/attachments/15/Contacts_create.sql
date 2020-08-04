CREATE SCHEMA `contacts_patrusova` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;

CREATE TABLE `contacts_patrusova`.`photos` (
  `id_photo` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `photo` BLOB NULL,
  PRIMARY KEY (`id_photo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE TABLE `contacts_patrusova`.`contacts` (
  `id_contact` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `contact_name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL DEFAULT '',
  `birthday` DATE NULL DEFAULT NULL,
  `gender` SET('м', 'ж') NOT NULL DEFAULT 'м',
  `citizenship` VARCHAR(45) NOT NULL DEFAULT '',
  `family_status` VARCHAR(45) NOT NULL DEFAULT '',
  `website` VARCHAR(255) NOT NULL DEFAULT '',
  `email` VARCHAR(255) NOT NULL DEFAULT '',
  `work_place` VARCHAR(100) NOT NULL DEFAULT '',
  `country` VARCHAR(45) NOT NULL DEFAULT '',
  `city` VARCHAR(45) NOT NULL DEFAULT '',
  `address` VARCHAR(45) NOT NULL DEFAULT '',
  `zipcode` VARCHAR(45) NOT NULL DEFAULT '',
  `id_photo` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id_contact`),
  INDEX `contact_photo_idx` (`id_photo` ASC) VISIBLE,
  CONSTRAINT `contact_photo`
    FOREIGN KEY (`id_photo`)
    REFERENCES `contacts_patrusova`.`photos` (`id_photo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE TABLE `contacts_patrusova`.`attachments` (
  `id_attachment` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `attachment` BLOB NOT NULL,
  `attachment_name` VARCHAR(255) NOT NULL DEFAULT '',
  `load_date` DATE NOT NULL,
  `comments` VARCHAR(255) NOT NULL DEFAULT '',
  `id_contact` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_attachment`),
  INDEX `attachment_contact_idx` (`id_contact` ASC) VISIBLE,
  CONSTRAINT `attachment_contact`
    FOREIGN KEY (`id_contact`)
    REFERENCES `contacts_patrusova`.`contacts` (`id_contact`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;

CREATE TABLE `contacts_patrusova`.`phones` (
  `id_phone` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_contact` BIGINT(20) UNSIGNED NOT NULL,
  `country_code` VARCHAR(5) NOT NULL DEFAULT '',
  `operator_code` VARCHAR(5) NOT NULL DEFAULT '',
  `phone_number` VARCHAR(15) NOT NULL DEFAULT '',
  `phone_type` SET('домашний', 'рабочий', 'мобильный', 'иной') NOT NULL DEFAULT 'иной',
  `comments` VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id_phone`),
  INDEX `phone_contact_idx` (`id_contact` ASC) VISIBLE,
  CONSTRAINT `phone_contact`
    FOREIGN KEY (`id_contact`)
    REFERENCES `contacts_patrusova`.`contacts` (`id_contact`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;