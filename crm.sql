-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: crm
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `reg_no` varchar(255) NOT NULL,
  `make` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `available` varchar(255) NOT NULL,
  `rate` double(11,2) NOT NULL,
  PRIMARY KEY (`reg_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES ('sha112','Toyota','Prius','NO',59.00),('sha113','Ford','Edge','NO',141.00),('sha114','Nissan','Juke','NO',92.00),('sha115','Jeep','Compass','NO',80.00),('sha116','Honda','CRV','NO',86.00),('sha117','Toyota','Corolla','YES',40.00),('sha118','Mitsubishi','Mirage','NO',47.00),('sha119','Chevrolet','Spark','YES',28.29),('sha121','BMW','M4','NO',32.00),('sha122','Hyundai','Sonata','YES',73.00),('sha123','Audi','A6','NO',30.00),('sha124','Ford','Mustang','YES',98.18),('sha125','Toyota','Rav4','NO',66.00),('sha131','Dodge','Grand Caravan','YES',115.00),('sha132','Chrysler','Pacifica','YES',186.00),('sha456','Ford','F-150','YES',136.00),('sha459','Dodge','Durango','YES',129.89);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_customer`
--

DROP TABLE IF EXISTS `car_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_customer` (
  `rent_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `reg_id` varchar(255) NOT NULL,
  `pickup_date` date NOT NULL,
  `return_date` date NOT NULL,
  `fee` double(11,2) NOT NULL,
  `fine` double(11,2) NOT NULL,
  `days_overdue` int NOT NULL,
  `returned` tinyint(1) NOT NULL,
  PRIMARY KEY (`rent_id`),
  KEY `car_customer_ibfk_1` (`customer_id`),
  KEY `fk_reg_no` (`reg_id`),
  CONSTRAINT `car_customer_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_reg_no` FOREIGN KEY (`reg_id`) REFERENCES `car` (`reg_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_customer`
--

LOCK TABLES `car_customer` WRITE;
/*!40000 ALTER TABLE `car_customer` DISABLE KEYS */;
INSERT INTO `car_customer` VALUES (15,2,'sha123','2022-02-01','2022-02-02',30.00,50.00,5,1),(18,5,'sha115','2022-02-18','2022-03-23',2640.00,0.00,0,0),(22,4,'sha114','2022-02-20','2022-02-28',736.00,0.00,0,1),(24,7,'sha123','2022-02-15','2022-02-16',30.00,0.00,0,1),(25,6,'sha125','2022-02-16','2022-02-20',264.00,0.00,0,0),(27,2,'sha118','2022-02-17','2022-02-18',47.00,0.00,0,0),(28,2,'sha118','2022-02-16','2022-03-04',752.00,0.00,0,0),(29,6,'sha118','2022-02-16','2022-03-04',752.00,0.00,0,0),(30,4,'sha121','2022-02-16','2022-02-18',64.00,0.00,0,0),(31,7,'sha123','2022-02-16','2022-02-17',30.00,0.00,0,0),(32,10,'sha114','2022-02-16','2022-02-17',92.00,0.00,0,0),(34,9,'sha112','2022-02-19','2022-02-20',59.00,0.00,0,0);
/*!40000 ALTER TABLE `car_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL,
  `license` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `license` (`license`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Lic-456-789-001','John Doe','123 Nowhere St, Winnipeg','(204) 123-4567'),(2,'Lic-123-123-002','Ali Sulaiman','456 Somewhere St, Winnipeg','(204) 555-2475'),(3,'Lic-123-121-111','Mark Roe','852 Here St, Winnipeg	','(204) 111-9734'),(4,'Lic-123-123-999','Elon Must','A12 Mars St, Winnipeg','(204) 222-2022'),(5,'Lic-456-123-003','Jeff Besauce','170 Amazon St, Winnipeg','(204) 169-2022'),(6,'Lic-111-001-000','Morgan Williams','47 Hanoi Way, Winnipeg','(204) 333-3558'),(7,'Lic-111-001-001','Penelope Logan','1411 Lilydale Dr, Winnipeg','(204) 635-4290'),(9,'Lic-165-115-753','Matthew Parry','199 Lafayette St, Winnipeg		','(204) 987-9431'),(10,'Lic-165-105-247','Nikola Tesla','369 Universe Way, Winnipeg		','(204) 369-3693'),(11,'Lic-547-754-010','Mar Manimtim','246 Here St, Winnipeg','(204) 749-7799');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-19  0:22:33
