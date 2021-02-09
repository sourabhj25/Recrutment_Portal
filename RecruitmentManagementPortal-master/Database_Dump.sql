CREATE DATABASE  IF NOT EXISTS `RMPortal` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `RMPortal`;
-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: RMPortal
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(45) DEFAULT 'AGS',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'AGS'),(2,'Java'),(3,'UX'),(4,'DotNet'),(5,'Data Mining');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_bonus`
--

DROP TABLE IF EXISTS `employee_bonus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_bonus` (
  `range_id` int(11) NOT NULL AUTO_INCREMENT,
  `from` int(11) DEFAULT '0',
  `to` int(11) DEFAULT '1',
  `bonus_amount` double NOT NULL,
  PRIMARY KEY (`range_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_bonus`
--

LOCK TABLES `employee_bonus` WRITE;
/*!40000 ALTER TABLE `employee_bonus` DISABLE KEYS */;
INSERT INTO `employee_bonus` VALUES (1,0,2,0),(2,2,5,10000),(3,5,15,15000);
/*!40000 ALTER TABLE `employee_bonus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_referal`
--

DROP TABLE IF EXISTS `employee_referal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_referal` (
  `referal_id` int(11) NOT NULL AUTO_INCREMENT,
  `applicant_name` varchar(255) DEFAULT NULL,
  `work_experience` float DEFAULT '0',
  `technical_skill` varchar(500) DEFAULT NULL,
  `referance_email` varchar(255) DEFAULT NULL,
  `referance_user_id` int(11) DEFAULT NULL,
  `resume` longblob,
  `application_status` varchar(255) DEFAULT NULL,
  `job_id` int(11) DEFAULT NULL,
  `bonous_status` varchar(45) DEFAULT 'In Process',
  `status_date` date DEFAULT NULL,
  `applicant_email` varchar(45) DEFAULT 'default@agsft.com',
  `bonus_amount` bigint(20) DEFAULT '0',
  `file_name` varchar(100) DEFAULT NULL,
  `file_extension` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`referal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_referal`
--

LOCK TABLES `employee_referal` WRITE;
/*!40000 ALTER TABLE `employee_referal` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_referal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_vacancy`
--

DROP TABLE IF EXISTS `job_vacancy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_vacancy` (
  `job_vacancy_id` int(11) NOT NULL AUTO_INCREMENT,
  `job_title` varchar(70) NOT NULL,
  `number_of_openings` int(11) NOT NULL,
  `experience_required` varchar(50) DEFAULT NULL,
  `job_description` varchar(500) DEFAULT NULL,
  `technical_skills` varchar(500) NOT NULL,
  `job_location` varchar(500) NOT NULL,
  `salary_ctc` varchar(100) DEFAULT 'Not Disclosed',
  `education` varchar(500) DEFAULT NULL,
  `job_type` varchar(50) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `exp_to` int(11) DEFAULT '0',
  `exp_from` int(11) DEFAULT '0',
  PRIMARY KEY (`job_vacancy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_vacancy`
--

LOCK TABLES `job_vacancy` WRITE;
/*!40000 ALTER TABLE `job_vacancy` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_vacancy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'AddOpenPosition'),(2,'UpdateOpenPosition'),(3,'ViewOpenPosition'),(4,'ChangeApplicationStatus'),(5,'DeactivateUser'),(6,'AssignRole'),(7,'ViewResumeStatus'),(8,'ViewBonus'),(9,'AddReferral'),(10,'AddBonusDetails'),(11,'UpdateBonusDetails');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_role`
--

DROP TABLE IF EXISTS `permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_role` (
  `role_permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_permission_id`),
  KEY `fk_role_permission_1_idx` (`permission_id`),
  KEY `fk_role_permission_2_idx` (`role_id`),
  CONSTRAINT `FK3tuvkbyi6wcytyg21hvpd6txw` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`),
  CONSTRAINT `FK50sfdcvbvdaclpn7wp4uop4ml` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_role_permission_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_permission_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_role`
--

LOCK TABLES `permission_role` WRITE;
/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */;
INSERT INTO `permission_role` VALUES (1,1,3),(2,1,7),(3,1,8),(4,1,9),(5,2,1),(6,2,2),(7,2,3),(8,2,4),(9,2,5),(10,2,6),(11,2,7),(12,3,1),(13,3,2),(14,3,4),(15,3,5),(16,3,8),(17,3,10),(18,3,11),(19,2,8),(20,2,9);
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referral_status`
--

DROP TABLE IF EXISTS `referral_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referral_status` (
  `referral_id` int(11) NOT NULL AUTO_INCREMENT,
  `referral_status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`referral_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referral_status`
--

LOCK TABLES `referral_status` WRITE;
/*!40000 ALTER TABLE `referral_status` DISABLE KEYS */;
INSERT INTO `referral_status` VALUES (1,'In Process'),(2,'Screening'),(3,'Technical Round'),(4,'Machine Round'),(5,'HR Round'),(6,'Selected'),(7,'Rejected'),(8,'Joined');
/*!40000 ALTER TABLE `referral_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'END_USER'),(2,'ADMIN'),(3,'HR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `created_at` varchar(45) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0',
  `last_login` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT '0',
  `address` varchar(45) DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `blood_group` varchar(45) DEFAULT NULL,
  `employee_id` varchar(10) DEFAULT 'AGSxxx',
  `dept_id` int(11) DEFAULT '1',
  `department` varchar(45) DEFAULT 'AGS',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_1_idx` (`email`),
  KEY `fk_user_1_idx1` (`role_id`),
  KEY `fk_user_2_idx` (`dept_id`),
  CONSTRAINT `FKmf82od1cs7u7drq5eua8ukyrw` FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_user_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_2` FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `fk_user_role_1_idx` (`role_id`),
  KEY `fk_user_role_2_idx` (`user_id`),
  CONSTRAINT `fk_user_role_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_token`
--

DROP TABLE IF EXISTS `user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_token` (
  `user_token_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_token` varchar(45) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `token_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_token_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_token`
--

LOCK TABLES `user_token` WRITE;
/*!40000 ALTER TABLE `user_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-25 11:59:24
