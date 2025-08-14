-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: sf_db
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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `user_name` varchar(30) NOT NULL,
  `product_id` int NOT NULL,
  `portions` float DEFAULT NULL,
  `price` float DEFAULT NULL,
  `presence` tinyint(1) NOT NULL,
  `block` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_name`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('admin',1199,1,5,0,0),('admin',1200,5,2.87,0,0),('admin',1201,2,14.8,0,0),('admin',1202,3,30.41,0,0),('admin',1203,3,25.99,0,0),('admin',1204,1,35.92,0,0),('admin',1205,3,1,0,0),('admin',1206,2,9.99,0,0),('admin',1207,10,2.25,0,0),('admin',1208,8,1.19,0,0),('admin',1209,1,79.67,0,0),('admin',1210,4,2.48,0,0),('admin',1211,4,11.53,0,0),('admin',1212,4,4.19,0,0),('admin',1213,4,34.26,0,0),('admin',1214,10,1.22,0,0),('admin',1215,1,18.63,0,0),('admin',1216,4,1.06,0,0),('admin',1217,10,2.52,1,0),('admin',1218,4,8.79,0,0),('admin',1219,1,5.21,1,0),('admin',1220,4,12.99,0,0),('admin',1221,4,7.08,0,0),('admin',1222,4,15.99,0,0),('admin',1223,6,5.7,0,0),('admin',1224,7,4.61,0,0),('admin',1225,4,2.49,0,0),('admin',1226,1,1.25,1,0),('admin',1227,1,1,1,0),('admin',1228,2,2.44,0,0),('admin',1229,3,2.2,0,0),('admin',1230,4,5.24,0,0),('admin',1231,3,5.99,0,0),('admin',1232,3,3.55,1,0),('admin',1233,1,26.25,1,0),('admin',1234,1,61.2,0,0),('admin',1235,1,49.91,0,0),('admin',1236,1,18,0,0),('admin',1237,6,5.77,0,0),('admin',1238,1,15.9,0,0),('admin',1239,3,15.35,1,0),('admin',1240,10,15.16,0,0),('admin',1241,1,53.45,0,0),('admin',1242,1,81.2,0,0),('admin',1243,1,85.27,0,0),('admin',1244,1,15.75,0,0),('admin',1245,3,19.6,0,0),('admin',1246,1,16.35,0,0),('admin',1247,1,11.4,0,0),('admin',1248,1,14.94,0,0),('admin',1249,6,7.79,0,0),('admin',1250,5,7.32,1,0),('admin',1251,1,10.89,0,0),('admin',1252,5,7.51,0,0),('admin',1253,4,1.85,0,0),('admin',1254,4,18.99,0,0),('admin',1255,4,9.38,0,0),('admin',1256,20,3.79,0,0),('admin',1257,1,37.25,0,0),('admin',1258,2,2.71,1,0),('admin',1259,4,14.98,0,0),('admin',1260,1,17.99,0,0),('admin',1261,2,15.61,0,0),('admin',1262,4,23.3,0,0),('admin',1263,1,1.86,0,0),('admin',1264,2,7,0,0),('admin',1265,2,7.32,0,0),('admin',1266,1,3.9,0,0),('admin',1267,1,13.25,0,0),('admin',1268,4,11.99,0,0),('admin',1269,2,9.32,0,1),('admin',1270,2,3.74,1,1),('admin',1271,1,14.55,0,1),('admin',1272,4,1,0,1),('admin',1273,4,11.49,0,1),('admin',1274,3,23.59,0,1),('admin',1275,4,10.49,0,1),('admin',1276,4,71.84,0,1),('admin',1277,4,41.84,0,1),('admin',1278,4,15.95,0,1),('admin',1279,4,44.61,0,1),('admin',1280,4,56.06,1,1),('admin',1281,1,9.05,0,1),('admin',1282,4,33.13,0,1),('admin',1283,1,7.99,0,1),('admin',1284,1,16.99,0,1),('admin',1285,10,1.61,0,1),('admin',1286,7,2,0,1),('admin',1287,20,48.99,0,1),('admin',1288,20,42.99,0,1),('admin',1289,3,22.45,0,1),('admin',1290,4,9.99,0,1),('admin',1291,4,11.35,0,1),('admin',1292,4,50.99,0,1),('admin',1293,4,6.99,0,1),('admin',1294,1,5.49,0,1),('admin',1295,4,3.69,0,1),('admin',1296,4,63.8,0,1),('admin',1297,2,4,1,1),('admin',1298,20,6.19,0,1),('admin',1299,2,5.55,1,1),('admin',1300,2,4.99,1,1),('admin',1301,1,10.7,0,1),('admin',1302,4,14.48,0,1),('admin',1303,4,4.1,0,1),('admin',1304,4,1.38,0,1),('admin',1305,4,19.5,0,1),('admin',1306,4,28.95,0,1),('admin',1307,4,13.9,0,1),('admin',1308,4,11.49,0,1),('admin',1309,4,9.27,0,1),('admin',1310,4,8.99,0,1),('admin',1311,1,73.33,0,1),('admin',1312,1,39.99,0,1),('admin',1313,1,20.99,0,1),('admin',1314,1,10,0,1),('admin',1315,1,30,0,1),('admin',1316,1,50,0,1),('admin',1317,1,34.99,0,1),('admin',1318,1,17.8,1,1),('admin',1319,1,11.27,1,1),('admin',1320,4,3.18,0,1),('admin',1321,1,20,1,1),('admin',1322,1,1.28,0,1),('admin',1323,20,2.84,0,1),('admin',1324,20,41.41,0,1),('admin',1325,1,19.85,0,1),('admin',1326,4,25.99,0,1),('admin',1327,20,59.98,0,1),('admin',1328,1,23.33,0,1),('admin',1329,2,3,0,1),('admin',1330,4,6.3,0,1),('admin',1331,4,15.99,0,1),('admin',1332,1,28,1,1),('admin',1333,1,6.67,1,1),('admin',1334,2,6.71,1,1),('admin',1335,2,9.9,0,1),('admin',1336,3,11.4,0,1),('admin',1337,2,7.5,0,1),('admin',1338,7,1.5,0,1),('admin',1339,4,4.74,0,1),('admin',1340,4,3.09,0,1),('admin',1341,1,14.5,0,1),('admin',1342,4,19.3,0,1),('admin',1343,6,1.43,0,1),('admin',1344,3,3.19,1,1),('admin',1345,6,6.99,0,1),('admin',1346,1,40,0,1),('admin',1347,3,20,0,1),('admin',1348,1,2.5,0,1),('admin',1349,4,11.65,0,1),('admin',1350,7,2.18,0,1),('admin',1351,6,1,0,1),('admin',1352,1,69.5,0,1),('admin',1353,1,21.52,0,1),('admin',1354,4,8.41,0,1),('admin',1355,4,4.99,0,1),('admin',1356,4,6.99,0,1),('admin',1357,10,5.99,0,1),('admin',1358,4,3.7,0,1),('admin',1359,4,20.61,0,1),('admin',1360,1,4.68,1,1),('admin',1361,1,2.95,1,1),('admin',1362,2,32.75,0,1),('admin',1363,4,22.84,0,1),('admin',1364,4,2.39,0,1),('admin',1365,4,1,0,1),('admin',1366,3,1.98,0,1),('admin',1367,10,5.16,0,1);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-14 11:03:00
