CREATE DATABASE  IF NOT EXISTS `test_db3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test_db3`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: test_db3
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `gender` enum('CHILD','MEN','UNISEX','WOMEN') DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'2024-11-19 20:47:41.966934','2024-11-19 20:47:41.966934',NULL,'MEN','Áo Sơ Mi','Áo'),(2,'2024-11-20 17:42:14.896862','2024-11-20 17:42:14.896862',NULL,'MEN','Quần Trouser','Quần'),(3,'2024-11-20 17:42:30.124052','2024-11-20 17:42:30.124052',NULL,'MEN','Quần Jeans','Quần'),(4,'2024-11-20 17:42:35.154004','2024-11-20 17:42:35.154004',NULL,'MEN','Quần Shorts','Quần'),(5,'2024-11-20 17:42:48.026797','2024-11-20 17:42:48.028022',NULL,'MEN','Quần Âu','Quần'),(6,'2024-11-20 17:43:15.303997','2024-11-20 17:43:15.303997',NULL,'MEN','Áo polo','Áo'),(7,'2024-11-20 17:43:22.567117','2024-11-20 17:43:22.567117',NULL,'MEN','Áo Thun','Áo'),(52,'2024-11-20 17:50:34.074155','2024-11-20 17:50:34.075167',NULL,'WOMEN','Áo Thun','Áo'),(53,'2024-11-20 17:50:41.928735','2024-11-20 17:50:41.928735',NULL,'WOMEN','Áo Polo','Áo'),(54,'2024-11-20 17:50:52.358474','2024-11-20 17:50:52.358474',NULL,'WOMEN','Áo Sơ mi','Áo'),(55,'2024-11-20 17:51:43.191596','2024-11-20 17:51:43.191596',NULL,'WOMEN','Áo Blouse','Áo'),(56,'2024-11-20 17:52:23.615008','2024-11-20 17:52:23.615008',NULL,'WOMEN','Chân Váy','Váy'),(57,'2024-11-20 17:52:33.880782','2024-11-20 17:52:33.880782',NULL,'WOMEN','Đầm','Váy'),(58,'2024-11-20 17:54:14.324422','2024-11-20 17:54:14.324422',NULL,'UNISEX','Áo sơ mi','Áo'),(59,'2024-11-20 17:54:28.709466','2024-11-20 17:54:28.709466',NULL,'UNISEX','Áo thun','Áo'),(60,'2024-11-20 17:56:10.118487','2024-11-20 17:56:10.118487',NULL,'UNISEX','Áo Hoodie Truyền Thống','Áo Hoddie'),(61,'2024-11-20 17:56:17.706440','2024-11-20 17:56:17.706440',NULL,'UNISEX','Áo Hoodie Oversize','Áo Hoddie');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order_items`
--

DROP TABLE IF EXISTS `tbl_order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_order_items` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_size_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi5qvuwu0gqfww50julf2kq3d7` (`order_id`),
  KEY `FKe9f37pnidhj8tsb7ikm8ksau6` (`product_size_id`),
  CONSTRAINT `FKe9f37pnidhj8tsb7ikm8ksau6` FOREIGN KEY (`product_size_id`) REFERENCES `tbl_product_stock_size` (`id`),
  CONSTRAINT `FKi5qvuwu0gqfww50julf2kq3d7` FOREIGN KEY (`order_id`) REFERENCES `tbl_orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order_items`
--

LOCK TABLES `tbl_order_items` WRITE;
/*!40000 ALTER TABLE `tbl_order_items` DISABLE KEYS */;
INSERT INTO `tbl_order_items` VALUES (2,'2024-11-22 12:25:31.237917','2024-11-22 12:25:31.237917',200000.00,2,2,1),(52,'2024-11-22 12:39:38.179027','2024-11-22 12:39:38.179027',200000.00,2,52,1),(102,'2024-11-22 12:41:13.045215','2024-11-22 12:41:13.045215',200000.00,2,102,1),(152,'2024-11-28 09:53:36.267230','2024-11-28 09:53:36.267230',200000.00,2,152,1),(202,'2024-11-28 09:56:08.571809','2024-11-28 09:56:08.571809',200000.00,100,202,1),(252,'2024-11-28 10:40:13.861430','2024-11-28 10:40:13.861430',200000.00,100,252,1),(302,'2024-11-28 10:41:50.294749','2024-11-28 10:41:50.294749',200000.00,100,302,1),(352,'2024-11-28 10:43:04.525803','2024-11-28 10:43:04.525803',200000.00,100,352,1),(402,'2024-11-28 10:44:13.013757','2024-11-28 10:44:13.013757',200000.00,100,402,1),(452,'2024-11-29 16:12:36.783275','2024-11-29 16:12:36.783275',200000.00,3,452,1),(502,'2024-11-29 16:14:36.381371','2024-11-29 16:14:36.381371',200000.00,3,502,1),(552,'2024-11-29 16:17:06.604650','2024-11-29 16:17:06.604650',200000.00,3,552,1),(553,'2024-11-29 16:22:29.681053','2024-11-29 16:22:29.681053',200000.00,3,553,1),(602,'2024-11-29 16:34:23.531258','2024-11-29 16:34:23.531258',200000.00,3,602,1),(603,'2024-11-29 16:36:06.684119','2024-11-29 16:36:06.684119',200000.00,3,603,1),(604,'2024-11-29 16:36:51.409800','2024-11-29 16:36:51.409800',200000.00,3,604,1),(652,'2024-11-29 16:43:11.955934','2024-11-29 16:43:11.955934',200000.00,3,652,1),(702,'2024-11-29 16:44:24.491223','2024-11-29 16:44:24.491223',200000.00,3,702,1),(703,'2024-11-29 16:47:49.649533','2024-11-29 16:47:49.649533',200000.00,3,703,1),(704,'2024-11-29 16:51:37.747092','2024-11-29 16:51:37.747092',200000.00,3,704,1),(705,'2024-11-29 16:55:01.535226','2024-11-29 16:55:01.535226',200000.00,3,705,1),(706,'2024-11-29 16:55:49.607295','2024-11-29 16:55:49.607295',200000.00,3,706,1),(707,'2024-11-29 16:59:41.716057','2024-11-29 16:59:41.716057',200000.00,3,707,1),(708,'2024-11-29 17:03:05.559339','2024-11-29 17:03:05.559339',200000.00,3,708,1),(709,'2024-11-29 17:03:06.684179','2024-11-29 17:03:06.684179',200000.00,3,709,1),(710,'2024-11-29 17:05:15.518750','2024-11-29 17:05:15.518750',200000.00,3,710,1),(711,'2024-11-29 17:06:20.824023','2024-11-29 17:06:20.824023',200000.00,3,711,1),(712,'2024-11-29 17:07:52.171351','2024-11-29 17:07:52.171351',200000.00,3,712,1),(752,'2024-11-29 17:27:28.349098','2024-11-29 17:27:28.349098',200000.00,3,752,1),(802,'2024-11-29 17:34:27.497764','2024-11-29 17:34:27.497764',200000.00,3,802,1),(803,'2024-11-29 17:36:28.488725','2024-11-29 17:36:28.489724',200000.00,3,803,1),(804,'2024-11-29 17:37:05.316158','2024-11-29 17:37:05.316158',200000.00,3,804,1),(805,'2024-11-29 17:39:22.491028','2024-11-29 17:39:22.491028',200000.00,3,805,1),(806,'2024-11-29 17:41:18.385221','2024-11-29 17:41:18.385221',200000.00,3,806,1),(807,'2024-11-29 17:52:26.492381','2024-11-29 17:52:26.492381',200000.00,3,807,1),(808,'2024-11-29 17:54:00.493967','2024-11-29 17:54:00.493967',200000.00,3,808,1),(809,'2024-11-29 17:58:06.172835','2024-11-29 17:58:06.172835',200000.00,3,809,1),(810,'2024-11-29 18:00:33.968799','2024-11-29 18:00:33.968799',200000.00,3,810,1);
/*!40000 ALTER TABLE `tbl_order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_orders`
--

DROP TABLE IF EXISTS `tbl_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_orders` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shipping_address` text,
  `status` enum('CANCELLED','DELIVERED','PENDING','PROCESSING','SHIPPED') NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpppim1yc9udwxeam99pmiarh3` (`user_id`),
  CONSTRAINT `FKpppim1yc9udwxeam99pmiarh3` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_orders`
--

LOCK TABLES `tbl_orders` WRITE;
/*!40000 ALTER TABLE `tbl_orders` DISABLE KEYS */;
INSERT INTO `tbl_orders` VALUES (2,'2024-11-22 12:25:31.222727','2024-11-22 12:25:31.222727','2024-11-22 12:25:31','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',400000.00,1),(52,'2024-11-22 12:39:38.165486','2024-11-22 12:39:38.165486','2024-11-22 12:39:38','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',400000.00,1),(102,'2024-11-22 12:41:13.031164','2024-11-22 12:41:13.031164','2024-11-22 12:41:13','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',400000.00,1),(152,'2024-11-28 09:53:36.226571','2024-11-28 09:53:36.226571','2024-11-28 09:53:36','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',400000.00,1),(202,'2024-11-28 09:56:08.558292','2024-11-28 09:56:08.558292','2024-11-28 09:56:09','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',20000000.00,1),(252,'2024-11-28 10:40:13.845923','2024-11-28 10:40:13.845923','2024-11-28 10:40:14','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',20000000.00,1),(302,'2024-11-28 10:41:50.280629','2024-11-28 10:41:50.280629','2024-11-28 10:41:50','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',20000000.00,1),(352,'2024-11-28 10:43:04.510414','2024-11-28 10:43:04.510414','2024-11-28 10:43:04','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',20000000.00,1),(402,'2024-11-28 10:44:12.997413','2024-11-28 10:44:12.997413','2024-11-28 10:44:13','Hoa Phu, Ung Hoa, Ha Noi','PROCESSING',20000000.00,1),(452,'2024-11-29 16:12:36.766804','2024-11-29 16:12:36.766804','2024-11-29 16:12:37','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(502,'2024-11-29 16:14:36.373469','2024-11-29 16:14:36.373469','2024-11-29 16:14:36','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(552,'2024-11-29 16:17:06.592444','2024-11-29 16:17:06.592444','2024-11-29 16:17:07','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(553,'2024-11-29 16:22:29.681053','2024-11-29 16:22:29.681053','2024-11-29 16:22:30','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(602,'2024-11-29 16:34:23.521253','2024-11-29 16:34:23.521253','2024-11-29 16:34:23','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(603,'2024-11-29 16:36:06.684119','2024-11-29 16:36:06.684119','2024-11-29 16:36:07','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(604,'2024-11-29 16:36:51.409800','2024-11-29 16:36:51.409800','2024-11-29 16:36:51','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(652,'2024-11-29 16:43:11.943872','2024-11-29 16:43:11.943872','2024-11-29 16:43:12','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(702,'2024-11-29 16:44:24.483125','2024-11-29 16:44:24.483125','2024-11-29 16:44:24','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(703,'2024-11-29 16:47:49.649533','2024-11-29 16:47:49.649533','2024-11-29 16:47:50','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(704,'2024-11-29 16:51:37.747092','2024-11-29 16:51:37.747092','2024-11-29 16:51:38','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(705,'2024-11-29 16:55:01.535226','2024-11-29 16:55:01.535226','2024-11-29 16:55:02','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(706,'2024-11-29 16:55:49.607295','2024-11-29 16:55:49.607295','2024-11-29 16:55:50','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(707,'2024-11-29 16:59:41.716057','2024-11-29 16:59:41.716057','2024-11-29 16:59:42','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(708,'2024-11-29 17:03:05.559339','2024-11-29 17:03:05.559339','2024-11-29 17:03:06','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(709,'2024-11-29 17:03:06.683170','2024-11-29 17:03:06.683170','2024-11-29 17:03:07','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(710,'2024-11-29 17:05:15.518750','2024-11-29 17:05:15.518750','2024-11-29 17:05:16','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(711,'2024-11-29 17:06:20.824023','2024-11-29 17:06:20.824023','2024-11-29 17:06:21','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(712,'2024-11-29 17:07:52.171351','2024-11-29 17:07:52.171351','2024-11-29 17:07:52','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(752,'2024-11-29 17:27:28.340603','2024-11-29 17:27:28.340603','2024-11-29 17:27:28','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(802,'2024-11-29 17:34:27.486507','2024-11-29 17:34:27.486507','2024-11-29 17:34:27','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(803,'2024-11-29 17:36:28.488725','2024-11-29 17:36:28.488725','2024-11-29 17:36:28','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(804,'2024-11-29 17:37:05.316158','2024-11-29 17:37:05.316158','2024-11-29 17:37:05','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(805,'2024-11-29 17:39:22.491028','2024-11-29 17:39:22.491028','2024-11-29 17:39:22','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(806,'2024-11-29 17:41:18.385221','2024-11-29 17:41:18.385221','2024-11-29 17:41:18','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(807,'2024-11-29 17:52:26.492381','2024-11-29 17:52:26.492381','2024-11-29 17:52:26','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(808,'2024-11-29 17:54:00.493967','2024-11-29 17:54:00.493967','2024-11-29 17:54:00','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(809,'2024-11-29 17:58:06.172835','2024-11-29 17:58:06.172835','2024-11-29 17:58:06','Hoa Phu, Ung Hoa','PROCESSING',600000.00,1),(810,'2024-11-29 18:00:33.967798','2024-11-29 18:09:34.745715','2024-11-29 18:00:34','Hoa Phu, Ung Hoa','CANCELLED',600000.00,1);
/*!40000 ALTER TABLE `tbl_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_product`
--

DROP TABLE IF EXISTS `tbl_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` text,
  `name` varchar(100) NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbcsum17r3ig4ebjtu95rf8ifh` (`category_id`),
  CONSTRAINT `FKbcsum17r3ig4ebjtu95rf8ifh` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_product`
--

LOCK TABLES `tbl_product` WRITE;
/*!40000 ALTER TABLE `tbl_product` DISABLE KEYS */;
INSERT INTO `tbl_product` VALUES (1,'2024-11-19 20:48:03.508621','2024-11-19 20:48:03.508621','Đây là chiếc áo sơ mi dài tay giúp chàng thể hiện sự chỉn chu, nghiêm chỉnh với vẻ ngoài cuốn hút. Mẫu áo sơ mi nam hoạ tiết nhí vừa mới mẻ, vừa trẻ trung chắc chắn sẽ làm cho tủ đồ công sở của bạn thêm thú vị.','Sơ Mi Dài Tay Nam',1);
/*!40000 ALTER TABLE `tbl_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_product_attributes`
--

DROP TABLE IF EXISTS `tbl_product_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_attributes` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9pph5gjh9d20qa1gr7hykp5xw` (`product_id`),
  CONSTRAINT `FK9pph5gjh9d20qa1gr7hykp5xw` FOREIGN KEY (`product_id`) REFERENCES `tbl_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_product_attributes`
--

LOCK TABLES `tbl_product_attributes` WRITE;
/*!40000 ALTER TABLE `tbl_product_attributes` DISABLE KEYS */;
INSERT INTO `tbl_product_attributes` VALUES (1,'2024-11-19 20:48:03.516143','2024-11-19 20:48:03.516143',NULL,200000.00,1);
/*!40000 ALTER TABLE `tbl_product_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_product_images`
--

DROP TABLE IF EXISTS `tbl_product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_images` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `product_attribute_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9s3scc179ays1dtfwhrt7w5g9` (`product_attribute_id`),
  CONSTRAINT `FK9s3scc179ays1dtfwhrt7w5g9` FOREIGN KEY (`product_attribute_id`) REFERENCES `tbl_product_attributes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_product_images`
--

LOCK TABLES `tbl_product_images` WRITE;
/*!40000 ALTER TABLE `tbl_product_images` DISABLE KEYS */;
INSERT INTO `tbl_product_images` VALUES (1,'2024-11-19 20:48:03.520698','2024-11-19 20:48:03.520698','https://res.cloudinary.com/diqcupueq/image/upload/v1732024072/spwanuvhwu2xqgalo4yk.webp',1),(2,'2024-11-19 20:48:03.523716','2024-11-19 20:48:03.523716','https://res.cloudinary.com/diqcupueq/image/upload/v1732024074/agmmdjknpjieujvrptil.webp',1),(3,'2024-11-19 20:48:03.523716','2024-11-19 20:48:03.523716','https://res.cloudinary.com/diqcupueq/image/upload/v1732024076/artuzli0joejy1vzmhpl.webp',1),(4,'2024-11-19 20:48:03.523716','2024-11-19 20:48:03.523716','https://res.cloudinary.com/diqcupueq/image/upload/v1732024078/lesm6mlzvfnbwhp5cmbk.webp',1),(5,'2024-11-19 20:48:03.523716','2024-11-19 20:48:03.523716','https://res.cloudinary.com/diqcupueq/image/upload/v1732024080/t3jkzsujageqpillqbon.webp',1),(6,'2024-11-19 20:48:03.523716','2024-11-19 20:48:03.523716','https://res.cloudinary.com/diqcupueq/image/upload/v1732024082/fcimziiyez7psl8opp4q.webp',1);
/*!40000 ALTER TABLE `tbl_product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_product_stock_size`
--

DROP TABLE IF EXISTS `tbl_product_stock_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_stock_size` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `size` int NOT NULL,
  `stock` int NOT NULL,
  `product_attribute_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp97owikfno41khgydgrf7vvao` (`product_attribute_id`),
  CONSTRAINT `FKp97owikfno41khgydgrf7vvao` FOREIGN KEY (`product_attribute_id`) REFERENCES `tbl_product_attributes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_product_stock_size`
--

LOCK TABLES `tbl_product_stock_size` WRITE;
/*!40000 ALTER TABLE `tbl_product_stock_size` DISABLE KEYS */;
INSERT INTO `tbl_product_stock_size` VALUES (1,'2024-11-19 20:48:03.527636','2024-11-29 18:46:01.668597',12,74,1),(2,'2024-11-19 20:48:03.530854','2024-11-19 20:48:03.530854',14,100,1),(3,'2024-11-19 20:48:03.530854','2024-11-19 20:48:03.530854',20,60,1);
/*!40000 ALTER TABLE `tbl_product_stock_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_promotion`
--

DROP TABLE IF EXISTS `tbl_promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_promotion` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_promotion`
--

LOCK TABLES `tbl_promotion` WRITE;
/*!40000 ALTER TABLE `tbl_promotion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_promotion_product`
--

DROP TABLE IF EXISTS `tbl_promotion_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_promotion_product` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `percentage` float DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `promotion_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj2h79opwip7dnb93hln9qpqsg` (`product_id`),
  KEY `FK8rk8coly15ir5k99t59xj77ht` (`promotion_id`),
  CONSTRAINT `FK8rk8coly15ir5k99t59xj77ht` FOREIGN KEY (`promotion_id`) REFERENCES `tbl_promotion` (`id`),
  CONSTRAINT `FKj2h79opwip7dnb93hln9qpqsg` FOREIGN KEY (`product_id`) REFERENCES `tbl_product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_promotion_product`
--

LOCK TABLES `tbl_promotion_product` WRITE;
/*!40000 ALTER TABLE `tbl_promotion_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_promotion_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_review`
--

DROP TABLE IF EXISTS `tbl_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_review` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `rating` int NOT NULL,
  `product_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKndy9ofcrkwangeykomm71tx6k` (`product_id`),
  UNIQUE KEY `UKerrdpjju4gm5ox1lis9p2g5k5` (`customer_id`),
  CONSTRAINT `FKa12qg0difn0jw3m9puletyuxx` FOREIGN KEY (`product_id`) REFERENCES `tbl_product` (`id`),
  CONSTRAINT `FKmnsrgcyg2aquvj1yl92aghfms` FOREIGN KEY (`customer_id`) REFERENCES `tbl_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_review`
--

LOCK TABLES `tbl_review` WRITE;
/*!40000 ALTER TABLE `tbl_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_users`
--

DROP TABLE IF EXISTS `tbl_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_users` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `authority` tinyint DEFAULT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_enabled` bit(1) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL,
  `reset_token_expiry_date` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `amount_available` decimal(38,2) DEFAULT NULL,
  `amount_reserved` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj562wwmipqt96rkoqbo0jc34` (`email`),
  UNIQUE KEY `UKc190nfu2w5xwvexf9dv08grsq` (`username`),
  CONSTRAINT `tbl_users_chk_1` CHECK ((`authority` between 0 and 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_users`
--

LOCK TABLES `tbl_users` WRITE;
/*!40000 ALTER TABLE `tbl_users` DISABLE KEYS */;
INSERT INTO `tbl_users` VALUES (1,'2024-11-22 12:21:23.553367','2024-11-29 18:46:01.682819',_binary '\0',_binary '\0','Ha Noi',0,_binary '\0','giap8547813@gmail.com','Giap',_binary '','Nguyen Van','$2a$10$2GS8SVJ7vo6Nd/34Wns4dux4d8wpeNWpo5O1j2aboTPum3lw4EAzW','0939306888',NULL,NULL,'test123456',9400000.00,50000.00);
/*!40000 ALTER TABLE `tbl_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_users_reviews`
--

DROP TABLE IF EXISTS `tbl_users_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_users_reviews` (
  `user_id` bigint NOT NULL,
  `reviews_id` bigint NOT NULL,
  UNIQUE KEY `UKpi4hatng6c4ghl3h0s98xv4df` (`reviews_id`),
  KEY `FKkhne6oxtyx1yasmc3uc1u2quh` (`user_id`),
  CONSTRAINT `FKkhne6oxtyx1yasmc3uc1u2quh` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`),
  CONSTRAINT `FKkoe4aymb3jlx74xvkk2xgdn2f` FOREIGN KEY (`reviews_id`) REFERENCES `tbl_review` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_users_reviews`
--

LOCK TABLES `tbl_users_reviews` WRITE;
/*!40000 ALTER TABLE `tbl_users_reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_users_reviews` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-30  1:20:13
