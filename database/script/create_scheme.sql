

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hrinkov_social_network
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hrinkov_social_network
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hrinkov_social_network` DEFAULT CHARACTER SET utf8 ;
USE `hrinkov_social_network` ;

-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`locations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `locationscol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`schools`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`schools` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `year_of_ending` DATETIME NULL,
  `locations_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_schools_locations1_idx` (`locations_id` ASC),
  CONSTRAINT `fk_schools_locations1`
    FOREIGN KEY (`locations_id`)
    REFERENCES `hrinkov_social_network`.`locations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`universities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`universities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `faculty` VARCHAR(50) NOT NULL,
  `specialty` VARCHAR(50) NOT NULL,
  `year_of_ending` DATETIME NOT NULL,
  `locations_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_schools_locations1_idx` (`locations_id` ASC),
  CONSTRAINT `fk_schools_locations10`
    FOREIGN KEY (`locations_id`)
    REFERENCES `hrinkov_social_network`.`locations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NULL,
  `password` VARCHAR(60) NULL,
  `role` VARCHAR(20) NULL,
  `registration_date` DATETIME NULL,
  `date_of_birth` DATETIME NULL,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `telephone_number` VARCHAR(13) NULL,
  `locations_id` INT NOT NULL,
  `schools_id` INT NOT NULL,
  `universities_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_users_locations1_idx` (`locations_id` ASC),
  INDEX `fk_users_schools1_idx` (`schools_id` ASC),
  INDEX `fk_users_universities1_idx` (`universities_id` ASC),
  CONSTRAINT `fk_users_locations1`
    FOREIGN KEY (`locations_id`)
    REFERENCES `hrinkov_social_network`.`locations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_schools1`
    FOREIGN KEY (`schools_id`)
    REFERENCES `hrinkov_social_network`.`schools` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_universities1`
    FOREIGN KEY (`universities_id`)
    REFERENCES `hrinkov_social_network`.`universities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`public_messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`public_messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `heading` VARCHAR(100) NULL,
  `content` VARCHAR(1000) NULL,
  `author_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_public_messages_users1_idx` (`author_id` ASC),
  CONSTRAINT `fk_public_messages_users1`
    FOREIGN KEY (`author_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`comments` (
  `id` INT NOT NULL,
  `content` VARCHAR(45) NULL,
  `author_id` INT NOT NULL,
  `public_messages_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comments_users1_idx` (`author_id` ASC),
  INDEX `fk_comments_public_messages1_idx` (`public_messages_id` ASC),
  CONSTRAINT `fk_comments_users1`
    FOREIGN KEY (`author_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_public_messages1`
    FOREIGN KEY (`public_messages_id`)
    REFERENCES `hrinkov_social_network`.`public_messages` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(45) NULL,
  `sender_id` INT NOT NULL,
  `recipient_id` INT NOT NULL,
  `departure_time` DATETIME NULL,
  `is_read` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_messages_users1_idx` (`sender_id` ASC),
  INDEX `fk_messages_users2_idx` (`recipient_id` ASC),
  CONSTRAINT `fk_messages_users1`
    FOREIGN KEY (`sender_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_messages_users2`
    FOREIGN KEY (`recipient_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`friends`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`friends` (
  `user_id` INT NOT NULL,
  `friend_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `friend_id`),
  INDEX `fk_users_has_users_users2_idx` (`friend_id` ASC),
  INDEX `fk_users_has_users_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_users_has_users_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_users_users2`
    FOREIGN KEY (`friend_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`friendship_requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`friendship_requests` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `friend_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_friendship_requests_users1_idx` (`user_id` ASC),
  INDEX `fk_friendship_requests_users2_idx` (`friend_id` ASC),
  CONSTRAINT `fk_friendship_requests_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_friendship_requests_users2`
    FOREIGN KEY (`friend_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`communities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`communities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `heading` VARCHAR(100) NULL,
  `information` VARCHAR(1000) NULL,
  `users_id` INT NOT NULL,
  `type` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_communities_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_communities_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`communities_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`communities_users` (
  `communities_id` INT NOT NULL,
  `users_id` INT NOT NULL,
  PRIMARY KEY (`communities_id`, `users_id`),
  INDEX `fk_communities_has_users_users1_idx` (`users_id` ASC),
  INDEX `fk_communities_has_users_communities1_idx` (`communities_id` ASC),
  CONSTRAINT `fk_communities_has_users_communities1`
    FOREIGN KEY (`communities_id`)
    REFERENCES `hrinkov_social_network`.`communities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_communities_has_users_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`posts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `heading` VARCHAR(100) NULL,
  `content` VARCHAR(1000) NULL,
  `author_id` INT NOT NULL,
  `communities_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_public_messages_users1_idx` (`author_id` ASC),
  INDEX `fk_posts_communities1_idx` (`communities_id` ASC),
  CONSTRAINT `fk_public_messages_users10`
    FOREIGN KEY (`author_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_posts_communities1`
    FOREIGN KEY (`communities_id`)
    REFERENCES `hrinkov_social_network`.`communities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`post_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`post_comments` (
  `id` INT NOT NULL,
  `content` VARCHAR(45) NULL,
  `author_id` INT NOT NULL,
  `post_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comments_users1_idx` (`author_id` ASC),
  INDEX `fk_post_comments_posts1_idx` (`post_id` ASC),
  CONSTRAINT `fk_comments_users10`
    FOREIGN KEY (`author_id`)
    REFERENCES `hrinkov_social_network`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_comments_posts1`
    FOREIGN KEY (`post_id`)
    REFERENCES `hrinkov_social_network`.`posts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hrinkov_social_network`.`sun_calendars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hrinkov_social_network`.`sun_calendars` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `beginning_of_twilight` DATETIME NOT NULL,
  `sunrise` DATETIME NOT NULL,
  `highest_point` DATETIME NOT NULL,
  `sunset` DATETIME NOT NULL,
  `end_of_twilight` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
