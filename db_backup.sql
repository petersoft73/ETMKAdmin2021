-- MySQL dump 10.13  Distrib 8.0.15, for macos10.14 (x86_64)
--
-- Host: localhost    Database: mgl2020
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ErintesVedelem`
--

DROP TABLE IF EXISTS `ErintesVedelem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ErintesVedelem` (
  `erVedId` int(11) NOT NULL,
  `ervenyes` date DEFAULT NULL,
  `jegyzokonyvSzam` varchar(255) DEFAULT NULL,
  `kelt` date DEFAULT NULL,
  PRIMARY KEY (`erVedId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ErintesVedelem`
--

LOCK TABLES `ErintesVedelem` WRITE;
/*!40000 ALTER TABLE `ErintesVedelem` DISABLE KEYS */;
/*!40000 ALTER TABLE `ErintesVedelem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Lepcso`
--

DROP TABLE IF EXISTS `Lepcso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Lepcso` (
  `lepcsoSzama` int(11) NOT NULL,
  `uzemkepes` bit(1) NOT NULL,
  `erVedId` int(11) DEFAULT NULL,
  `locationId` int(11) DEFAULT NULL,
  `tipusId` int(11) DEFAULT NULL,
  PRIMARY KEY (`lepcsoSzama`),
  KEY `FKt09tycp4vmhkgijw76yvytyik` (`erVedId`),
  KEY `FKh8oomolk5ktleu03o987ti537` (`locationId`),
  KEY `FKf3s4888sn5cnrhv6qk06pa5qx` (`tipusId`),
  CONSTRAINT `FKf3s4888sn5cnrhv6qk06pa5qx` FOREIGN KEY (`tipusId`) REFERENCES `tipus` (`tipusId`),
  CONSTRAINT `FKh8oomolk5ktleu03o987ti537` FOREIGN KEY (`locationId`) REFERENCES `location` (`locationId`),
  CONSTRAINT `FKt09tycp4vmhkgijw76yvytyik` FOREIGN KEY (`erVedId`) REFERENCES `erintesvedelem` (`erVedId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lepcso`
--

LOCK TABLES `Lepcso` WRITE;
/*!40000 ALTER TABLE `Lepcso` DISABLE KEYS */;
/*!40000 ALTER TABLE `Lepcso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Location` (
  `locationId` int(11) NOT NULL,
  `locationName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tipus`
--

DROP TABLE IF EXISTS `Tipus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Tipus` (
  `tipusId` int(11) NOT NULL,
  `tipusNev` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tipusId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tipus`
--

LOCK TABLES `Tipus` WRITE;
/*!40000 ALTER TABLE `Tipus` DISABLE KEYS */;
/*!40000 ALTER TABLE `Tipus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-30 11:21:30
