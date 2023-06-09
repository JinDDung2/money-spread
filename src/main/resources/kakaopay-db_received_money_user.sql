-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: kakaopay-db
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `received_money_user`
--

DROP TABLE IF EXISTS `received_money_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `received_money_user` (
  `id` bigint NOT NULL,
  `received_money` int NOT NULL,
  `money_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK19070juiwuxdy6vb0b41gdisd` (`money_id`),
  KEY `FK4fafnqend6dvhvmrjqpckcfcj` (`user_id`),
  CONSTRAINT `FK19070juiwuxdy6vb0b41gdisd` FOREIGN KEY (`money_id`) REFERENCES `money` (`id`),
  CONSTRAINT `FK4fafnqend6dvhvmrjqpckcfcj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `received_money_user`
--

LOCK TABLES `received_money_user` WRITE;
/*!40000 ALTER TABLE `received_money_user` DISABLE KEYS */;
INSERT INTO `received_money_user` VALUES (14,11909,18,NULL),(15,1,18,NULL),(16,57469,18,NULL),(17,30621,18,NULL),(19,51569,22,NULL),(20,18507,22,NULL),(21,29924,22,NULL),(23,33055,26,NULL),(24,607,26,NULL),(25,66338,26,NULL),(27,168,30,NULL),(28,38669,30,NULL),(29,61163,30,NULL),(31,98943,34,NULL),(32,572,34,NULL),(33,485,34,NULL),(35,284,38,NULL),(36,42285,38,NULL),(37,57431,38,NULL),(39,32439,42,NULL),(40,6725,42,NULL),(41,60836,42,NULL),(44,6663,43,NULL),(45,93,43,NULL),(46,3244,43,NULL),(48,18622,47,NULL),(49,15,47,NULL),(50,1363,47,NULL),(52,1,51,NULL),(53,18926,51,NULL),(54,1073,51,NULL),(56,755,55,NULL),(57,18364,55,NULL),(58,881,55,NULL),(64,3286,63,NULL),(65,16005,63,NULL),(66,709,63,NULL),(68,5349,67,NULL),(69,1332,67,NULL),(70,13319,67,NULL),(72,9889,71,NULL),(73,2009,71,NULL),(74,8102,71,NULL),(76,337,75,NULL),(77,16485,75,NULL),(78,3178,75,NULL),(80,4228,79,NULL),(81,3211,79,NULL),(82,12561,79,NULL),(84,6187,83,NULL),(85,13562,83,NULL),(86,251,83,NULL),(88,53,87,8),(89,16831,87,9),(90,172,87,10),(91,2944,87,11),(93,6286,92,8),(94,13714,92,9),(96,5862,95,8),(97,14138,95,9),(99,19860,98,8),(100,140,98,9),(102,1489,101,10),(103,4605,101,9),(104,13903,101,NULL);
/*!40000 ALTER TABLE `received_money_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-15 11:55:26
