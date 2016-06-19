-- MySQL Workbench Forward Engineering
 
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
 
-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;
 
-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;
 
-- -----------------------------------------------------
-- Table `mydb`.`Student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Student` ;
 
CREATE TABLE IF NOT EXISTS `mydb`.`Student` (
  `StudentID` INT NOT NULL,
  `StudentPassword` VARCHAR(64) NULL,
  `StudentFirstName` VARCHAR(64) NOT NULL,
  `StudentLastName` VARCHAR(64) NOT NULL,
  `StudentSection` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`StudentID`))
ENGINE = InnoDB;
 
 
-- -----------------------------------------------------
-- Table `mydb`.`Activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Activity` ;
 
CREATE TABLE IF NOT EXISTS `mydb`.`Activity` (
  `ActivityID` INT NOT NULL,
  `ActivityName` VARCHAR(512) NOT NULL,
  `ActivityFile` BLOB NOT NULL,
  `ActivityTimestamp` TIMESTAMP NOT NULL,
  `ActivityDeadline` DATETIME NOT NULL,
  `ActivityFilename` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`ActivityID`),
  UNIQUE INDEX `ActivityTimestamp_UNIQUE` (`ActivityTimestamp` ASC))
ENGINE = InnoDB;
 
 
-- -----------------------------------------------------
-- Table `mydb`.`Deliverable`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Deliverable` ;
 
CREATE TABLE IF NOT EXISTS `mydb`.`Deliverable` (
  `DeliverableID` INT NOT NULL,
  `StudentID` INT NOT NULL,
  `ActivityID` INT NOT NULL,
  `DeliverableSourceCode` BLOB NOT NULL,
  `DateSubmitted` TIMESTAMP NOT NULL,
  `DeliverableSourceCodeFileName` VARCHAR(512) NOT NULL,
  `Grade` FLOAT NOT NULL,
  PRIMARY KEY (`DeliverableID`, `StudentID`, `ActivityID`),
  INDEX `fk_Deliverable_Student_idx` (`StudentID` ASC),
  INDEX `fk_Deliverable_Activity1_idx` (`ActivityID` ASC),
  CONSTRAINT `fk_Deliverable_Student`
    FOREIGN KEY (`StudentID`)
    REFERENCES `mydb`.`Student` (`StudentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Deliverable_Activity1`
    FOREIGN KEY (`ActivityID`)
    REFERENCES `mydb`.`Activity` (`ActivityID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 
 
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;