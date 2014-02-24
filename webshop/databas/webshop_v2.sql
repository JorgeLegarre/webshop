CREATE DATABASE  IF NOT EXISTS `webshop` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `webshop`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: webshop
-- ------------------------------------------------------
-- Server version	5.6.15

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
-- Table structure for table `actors`
--

DROP TABLE IF EXISTS `actors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `dob` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actors`
--

LOCK TABLES `actors` WRITE;
/*!40000 ALTER TABLE `actors` DISABLE KEYS */;
/*!40000 ALTER TABLE `actors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `id` int(11) unsigned NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `dob` date DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `product_id` mediumint(8) unsigned NOT NULL,
  `title` varchar(45) NOT NULL,
  `format` varchar(45) DEFAULT NULL,
  `isbn` int(11) NOT NULL,
  `pages` int(11) NOT NULL,
  `publisher` varchar(45) NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `bookcol_UNIQUE` (`product_id`),
  CONSTRAINT `product_bkey` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_author`
--

DROP TABLE IF EXISTS `book_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_author` (
  `product_id` mediumint(8) unsigned NOT NULL,
  `author_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`product_id`,`author_id`),
  KEY `author_key_idx` (`author_id`),
  CONSTRAINT `product_key` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `author_key` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_author`
--

LOCK TABLES `book_author` WRITE;
/*!40000 ALTER TABLE `book_author` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `staff_responsible` mediumint(8) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `CATEGORY_STAFF_FK_idx` (`staff_responsible`),
  CONSTRAINT `CATEGORY_STAFF_FK` FOREIGN KEY (`staff_responsible`) REFERENCES `staff` (`id`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Books',27),(2,'Children',63),(3,'Clothes',63),(4,'Computing',58),(5,'Electronics',58),(6,'Films',27),(7,'Gardening',63),(8,'Mens',63),(9,'Sports & outdoors',71),(10,'Toys',62),(11,'Womens',63);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `film`
--

DROP TABLE IF EXISTS `film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `film` (
  `product_id` mediumint(8) unsigned NOT NULL,
  `title` varchar(45) NOT NULL,
  `rating` int(11) NOT NULL,
  `format` varchar(45) DEFAULT NULL,
  `director` varchar(45) NOT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `product_fkey` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film`
--

LOCK TABLES `film` WRITE;
/*!40000 ALTER TABLE `film` DISABLE KEYS */;
/*!40000 ALTER TABLE `film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `film_actors`
--

DROP TABLE IF EXISTS `film_actors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `film_actors` (
  `product_id` mediumint(8) unsigned NOT NULL,
  `actors_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`product_id`,`actors_id`),
  KEY `actors_fakey_idx` (`actors_id`),
  CONSTRAINT `actors_fakey` FOREIGN KEY (`actors_id`) REFERENCES `actors` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `product_fakey` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film_actors`
--

LOCK TABLES `film_actors` WRITE;
/*!40000 ALTER TABLE `film_actors` DISABLE KEYS */;
/*!40000 ALTER TABLE `film_actors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_categories`
--

DROP TABLE IF EXISTS `product_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_categories` (
  `product_id` mediumint(8) unsigned NOT NULL,
  `category_id` mediumint(8) unsigned NOT NULL,
  PRIMARY KEY (`product_id`,`category_id`),
  KEY `category_FK_idx` (`category_id`),
  CONSTRAINT `category_FK` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `product_FK` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_categories`
--

LOCK TABLES `product_categories` WRITE;
/*!40000 ALTER TABLE `product_categories` DISABLE KEYS */;
INSERT INTO `product_categories` VALUES (2,1),(33,1),(35,1),(37,1),(39,1),(40,1),(40,4),(33,6),(34,6),(2,8),(2,11),(34,11),(35,11),(37,11);
/*!40000 ALTER TABLE `product_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_type` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `table_name` varchar(45) NOT NULL,
  `desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
INSERT INTO `product_type` VALUES (1,'book','Book'),(2,'film','Film');
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `cost` double(10,2) DEFAULT NULL,
  `rrp` double(10,2) DEFAULT NULL,
  `product_type` mediumint(8) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PRODUCTS_PRODUCT_TYPE_idx` (`product_type`),
  CONSTRAINT `FK_PRODUCTS_PRODUCT_TYPE` FOREIGN KEY (`product_type`) REFERENCES `product_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (2,'Yeesus','Kanye West',149.00,1000.00,NULL),(3,'VIVA LA VIDA','AWESOME',200.00,0.00,NULL),(4,'VIVA LA VIDA','AWESOME',200.00,0.00,NULL),(5,'VIVA LA VIDA','AWESOME',200.00,0.00,NULL),(6,'VIVA LA VIDA','AWESOME',200.00,0.00,NULL),(7,'Mylo Xyloto','Lovers in Japan',149.00,0.00,NULL),(8,'Mylo Xyloto',NULL,149.00,0.00,NULL),(9,'Mylo Xyloto',NULL,149.00,0.00,NULL),(10,'Mylo Xyloto','Epic album!!',149.00,3000.00,NULL),(11,'Parachutes','So good',149.00,3000.00,NULL),(12,'Mylo Xyloto',NULL,149.00,0.00,NULL),(13,'Mylo Xyloto',NULL,149.00,0.00,NULL),(14,'Mylo Xyloto',NULL,149.00,0.00,NULL),(15,'Mylo Xyloto',NULL,149.00,0.00,NULL),(16,'Mylo Xyloto',NULL,149.00,0.00,NULL),(17,'Mylo Xyloto',NULL,149.00,0.00,NULL),(18,'Mylo Xyloto',NULL,149.00,0.00,NULL),(19,'Mylo Xyloto',NULL,149.00,0.00,NULL),(20,'Mylo Xyloto',NULL,149.00,0.00,NULL),(21,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(22,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(23,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(24,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(25,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(26,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(27,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(28,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(29,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(30,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(31,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(32,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(33,'Mylo Xyloto','Coldplay, 2012',149.00,1000.00,NULL),(34,'Streets of Gold','3oh!3',149.00,1000.00,NULL),(35,'Streets of Gold','3oh!3',149.00,1000.00,NULL),(37,'Yeesus','Kanye West',149.00,1000.00,NULL),(38,'Hybrid','Linkin',200.00,201.00,NULL),(39,'Hybrid Theory','Linkin Park',149.00,200.00,NULL),(40,'odalys','best mom',100.00,200.00,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_cart` (
  `user_email` varchar(50) NOT NULL,
  `product_id` mediumint(8) unsigned NOT NULL,
  `quantity` int(4) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_email`,`product_id`),
  KEY `product_FK_idx` (`product_id`),
  KEY `product_shopping_FK_idx` (`product_id`),
  CONSTRAINT `user_shopping_FK` FOREIGN KEY (`user_email`) REFERENCES `users` (`email`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `product_shopping_FK` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_cart`
--

LOCK TABLES `shopping_cart` WRITE;
/*!40000 ALTER TABLE `shopping_cart` DISABLE KEYS */;
INSERT INTO `shopping_cart` VALUES ('Test@test.se',2,20);
/*!40000 ALTER TABLE `shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `postcode` varchar(10) DEFAULT NULL,
  `mobile` varchar(100) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `salary` mediumint(9) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'Sybill','Le','1973-05-11','P.O. Box 173, 4391 Felis Ave','Redcliffe','QM3 9VI','0800 160 9672','elit.erat.vitae@nonnisi.org',35805),(2,'Kalia','Horn','1982-08-27','708-9236 Ac St.','Piancastagnaio','UR6 4PF','(013856) 08737','Duis@lobortisquam.co.uk',39782),(3,'Tatum','Bailey','1956-07-17','2295 Inceptos Av.','Morrovalle','EN6 6DH','(0151) 475 0217','mollis.lectus.pede@quismassa.com',36747),(4,'India','Franco','1982-02-15','Ap #499-6083 Vitae, Ave','Virginia Beach','L99 2YC','07624 649116','velit.Pellentesque.ultricies@nequeseddictum.org',22734),(5,'Sophia','Nash','1957-12-26','Ap #986-9837 Convallis Street','Maria','VA67 3CS','070 3590 4600','ut@arcu.com',51154),(6,'Hillary','Brennan','1984-12-15','Ap #556-7189 Vestibulum. Ave','Borgone Susa','QP3O 5HT','055 3532 1835','metus@auctorquistristique.com',59563),(7,'Juliet','Crane','1979-05-05','224-3688 Augue St.','Tolve','T1O 8EL','(01223) 29551','nisl.arcu@eliteratvitae.org',30010),(8,'Ian','Melendez','1971-04-03','Ap #621-3246 Cursus Rd.','Weert','MO2L 7JU','0800 629 1936','ut@enim.net',24560),(9,'Fritz','Holder','1967-10-21','558-600 Eu Av.','Gressan','P3 5KQ','070 3402 7173','lacus.varius@Curabiturdictum.co.uk',26688),(10,'Eve','White','1969-06-23','579 Suspendisse Av.','Alphen aan den Rijn','C9V 0ZG','0845 46 41','iaculis.nec@sem.co.uk',57655),(11,'Kaden','Levine','1965-06-19','P.O. Box 787, 7363 Penatibus St.','Huntsville','G97 7TI','0906 455 5808','Donec.nibh@mollis.net',63997),(12,'Lois','Garner','1957-04-16','887-4470 Mauris St.','Roux-Miroir','N8 9UP','0845 46 47','tristique.aliquet@Integer.com',69398),(13,'Hanna','Moses','1954-06-22','615-6451 Donec Road','Gosnells','FH78 9FN','0500 440099','pede@aliquameros.org',61582),(14,'Blair','Newman','1978-06-06','661-2463 Non, Rd.','Palermo','OC3 8YT','076 1806 3920','ante.iaculis.nec@dictumcursusNunc.co.uk',51119),(15,'Chanda','Baker','1975-07-18','576-4704 Consectetuer Street','Pontedera','R1P 2ED','0800 1111','In@quamdignissim.edu',20939),(16,'Angelica','Guy','1980-10-13','Ap #926-1665 Malesuada Street','NÃ®mes','R2 2VJ','0938 537 7371','rhoncus.Proin@lobortisrisus.co.uk',31976),(17,'Yoshi','Watson','1963-11-21','142-5328 Dapibus Street','Rostock','JG9 6SQ','056 0010 4751','libero@ultrices.com',24969),(18,'Baxter','Baxter','1950-10-18','1081 Vitae Road','Chambave','MZ3 4KA','0800 1111','ut.aliquam@inaliquet.ca',25278),(19,'Curran','Huffman','1979-06-18','2380 Urna. Rd.','Mobile','GI7F 6HJ','0800 1111','ipsum.dolor@non.co.uk',17150),(20,'Sacha','Valenzuela','1953-10-26','P.O. Box 494, 8842 Velit Avenue','Hollange','LZ4 3AD','0385 737 7314','convallis.ante.lectus@nondui.org',50962),(21,'Daquan','Flynn','1962-01-08','9823 Suscipit St.','Mesa','Y2 4OD','0977 443 0849','at.augue@ullamcorpermagna.ca',67840),(22,'Eleanor','Payne','1961-10-07','7054 Ut, Rd.','Perwez','LV89 5YL','0800 339 4550','a.enim@liberonec.edu',50769),(23,'Melvin','Chandler','1987-01-10','325-1237 A Ave','Munich','Y0 1FP','07624 277141','penatibus@diamnunc.co.uk',33815),(24,'Charles','Juarez','1953-09-18','Ap #599-8663 Semper Ave','Orta San Giulio','KV7 1ED','(0113) 358 8288','ante.lectus@urnaet.org',56209),(25,'Yasir','Mccoy','1965-08-02','466-3671 Luctus Ave','Pictou','VA0O 9JR','0800 1111','eu@fermentummetusAenean.org',38462),(26,'Leandra','Vincent','1975-11-01','178-1277 Lacus. St.','Campinas','P1B 5WL','0845 46 49','sagittis@non.edu',50310),(27,'Maxwell','Farley','1986-02-22','Ap #141-8796 A, Av.','L?vis','DF75 2MY','0957 386 2943','tellus.eu@ridiculus.org',25287),(28,'Yuri','Skinner','1979-01-24','645-9903 Erat Av.','Gatineau','MI78 9UT','07624 680387','vel.nisl@justo.org',63188),(29,'Odette','Dudley','1956-09-14','P.O. Box 270, 4168 Purus. St.','Lï¿½neburg','N7C 7EK','0500 852486','sit.amet.massa@necurnaet.com',58821),(30,'Alika','Carlson','1991-03-31','858-3129 Vitae Avenue','Rea','T0M 2SZ','0800 071143','a@diamPellentesque.co.uk',17572),(31,'Nora','Faulkner','1952-06-28','Ap #412-5178 Lectus Street','North Las Vegas','A2X 1JU','07624 781140','lacus@Nuncmaurissapien.com',47573),(32,'Deanna','Puckett','1964-03-03','190-718 Eros Rd.','Baie-Comeau','DZ1M 8SR','056 2177 0907','egestas@vitaerisusDuis.net',69889),(33,'Charissa','Orr','1957-10-21','4367 Euismod St.','Venezia','QH8P 3UP','076 5693 7043','scelerisque@imperdieterat.net',24015),(34,'Doris','Mayer','1978-12-04','2969 Pharetra. Rd.','Herentals','RU38 6RJ','07110 888477','elit@faucibuslectusa.edu',42719),(35,'Nina','Lang','1985-09-15','Ap #434-5243 Nibh. Ave','Green Bay','Z43 9PD','(01066) 012850','est.Nunc.ullamcorper@dolorsitamet.com',68601),(36,'Octavius','Pittman','1975-09-21','P.O. Box 461, 7381 Laoreet Av.','Arzano','TX09 1UG','(0112) 875 8887','odio@Vestibulumante.net',40365),(37,'Hunter','Cash','1988-08-15','Ap #812-2380 Elit Av.','Tongerlo','EZ8 3KJ','0845 46 44','arcu.Vestibulum@ante.ca',61024),(38,'Danielle','Reid','1992-09-05','8050 Auctor Street','Stevenage','RC7N 6PJ','0800 1111','dignissim.magna.a@CuraePhasellus.com',58471),(39,'Sigourney','Mays','1989-02-17','P.O. Box 170, 3405 Quis, Street','Haguenau','OJ9 6ZH','076 3746 1117','velit.in@aliquetdiam.edu',39284),(40,'Dacey','Whitfield','1974-08-22','P.O. Box 968, 9210 Gravida. Road','Kaaskerke','G2 1XZ','(0119) 392 4458','hendrerit.Donec@variuseteuismod.net',54441),(41,'Zorita','Watson','1986-09-21','303-2505 Adipiscing Road','Bowling Green','H5I 0RJ','070 1057 0681','neque.Morbi.quis@elit.edu',39259),(42,'Xena','Waller','1990-12-23','6611 Arcu Av.','Villa Latina','U9J 0HA','(0115) 278 3371','nascetur.ridiculus.mus@ullamcorperDuiscursus.net',21114),(43,'Jasmine','Dunlap','1982-05-13','P.O. Box 659, 5728 Erat Ave','Trois-Riviï¿½res','S9 6OL','0340 051 9517','Donec@Morbimetus.edu',44293),(44,'Ursula','Vaughan','1969-07-12','P.O. Box 194, 1007 Phasellus St.','Hilversum','E1 5TX','0313 682 9619','condimentum.Donec.at@Nunc.org',22749),(45,'Keegan','Palmer','1993-07-31','726-1492 Neque Rd.','Vagli Sotto','AX2 8DO','0800 007 4869','amet.ornare.lectus@nonlacinia.ca',26633),(46,'Quinn','Mcknight','1985-01-28','Ap #597-3598 Cras Avenue','Oppido Mamertina','P0 0HV','(0116) 597 0875','lobortis.Class@Suspendisseeleifend.org',26989),(47,'Joseph','Barron','1978-08-10','197-3295 Dictum. Ave','St. Albert','Z5 3BX','(028) 3719 9277','nunc.nulla.vulputate@Cumsociis.co.uk',67322),(48,'Cassandra','Watson','1993-07-19','104-571 Cursus. Street','New Orleans','K3 2FL','0308 431 5149','Aliquam.fringilla@eget.ca',55293),(49,'Tara','Doyle','1979-01-23','1169 Nec, Rd.','Bristol','O4W 3BL','(0115) 914 7169','sapien@magnatellusfaucibus.co.uk',60231),(50,'Richard','Sykes','1954-09-21','P.O. Box 650, 5364 Lobortis, Avenue','Leffinge','D7B 1RB','0334 416 8217','Suspendisse.tristique.neque@magnis.org',41492),(51,'Selma','Warren','1975-07-08','P.O. Box 859, 4774 A, Rd.','Rio Saliceto','T29 7FD','(016977) 9337','Nunc.pulvinar.arcu@Phasellusinfelis.net',39412),(52,'Xanthus','James','1969-11-18','Ap #585-129 Erat Street','Lustin','RM1Z 7ND','(0110) 285 9144','eu@Fusce.net',47794),(53,'Zia','Atkins','1976-05-02','Ap #139-6279 Pellentesque Av.','Rimbey','GW4 6AC','0500 348170','molestie.Sed.id@litora.co.uk',62783),(54,'Catherine','Santiago','1984-10-24','1672 Purus Ave','Fort St. John','AH8R 9JO','(010256) 10550','congue.a@orciPhasellus.co.uk',22021),(55,'Megan','Lyons','1973-10-30','Ap #664-5556 Imperdiet, Street','Opglabbeek','ZE0I 1AC','056 5211 4556','augue.malesuada@Nam.co.uk',57391),(56,'Dustin','Perez','1980-07-16','Ap #884-4182 Vivamus St.','Pellizzano','NE7 6AU','(013553) 96843','condimentum@Nam.com',67255),(57,'Wylie','Mann','1972-07-22','8991 Quis Street','Wedel','LP93 1VL','0872 547 2259','iaculis.nec@nibhDonecest.edu',48431),(58,'Kameko','Stewart','1989-10-07','P.O. Box 791, 2492 Egestas Av.','Villers-Perwin','GS8 7OW','(012359) 61254','nisl.Maecenas@sagittis.com',18978),(59,'Ian','Young','1982-07-22','P.O. Box 338, 8901 Varius Street','Salt Lake City','VR90 5FN','(01107) 62323','rutrum@velitegetlaoreet.co.uk',55828),(60,'Camille','Norris','1981-08-24','Ap #327-1159 Lorem Avenue','Valfabbrica','M8 4EJ','(021) 7404 1467','Ut@temporaugueac.edu',27716),(61,'Tatyana','Clayton','1968-12-28','P.O. Box 546, 7149 Amet Street','Walsall','BW13 6VK','0800 775 6456','montes@infelisNulla.edu',63508),(62,'Aaron','Hodge','1954-12-10','6287 Duis Av.','Fraser Lake','KV64 8QV','(01461) 139439','Maecenas@blanditcongue.ca',19215),(63,'Rachel','Thompson','1955-03-09','Ap #985-6122 Sed Rd.','Town of Yarmouth','F6 7OS','(012395) 15049','fringilla.purus.mauris@nasceturridiculusmus.co.uk',59490),(64,'Dustin','Kline','1966-04-24','940-3136 Ipsum. Ave','Klagenfurt','T0 5BY','070 3839 3217','elit.pharetra@mauriselit.org',42030),(65,'Hiram','Burch','1970-09-30','Ap #311-3291 Aliquam Road','Carbonear','AJ30 9ZO','0354 869 5705','purus@antedictum.co.uk',35049),(66,'Nathaniel','Franco','1957-05-07','Ap #234-629 A, St.','Sovizzo','GO9Y 0CM','(01516) 607584','sem.vitae@aliquet.com',27589),(67,'Mohammad','Paul','1951-07-01','P.O. Box 697, 728 Sapien, St.','Saint-Dizier','U6 8HG','0500 983512','et.pede.Nunc@utmi.com',65821),(68,'Jael','Gordon','1966-06-19','913-5580 Dis St.','Stafford','M52 1HF','0845 46 40','in@felisullamcorper.edu',65098),(69,'Cherokee','Nolan','1963-10-14','P.O. Box 552, 5766 Nunc Road','Kampenhout','GE26 2YN','(01239) 501116','orci.lobortis.augue@egestaslaciniaSed.com',21896),(70,'Gage','Walton','1956-02-07','5865 A Rd.','Stockport','B51 8TK','0800 295442','dolor.tempus@luctusetultrices.ca',32485),(71,'Claudia','Stuart','1981-08-12','P.O. Box 420, 1092 Dui Street','Gavorrano','Z2 2DG','0800 1111','quis@massa.edu',54705),(72,'Murphy','Marquez','1951-03-26','635-838 Enim. Ave','Ternitz','Q9 8XD','070 0807 9444','nec.ligula@cursusdiamat.org',22370),(73,'Desirae','Farley','1957-06-08','606-2574 Lacinia Av.','Colwood','R3 1LK','(014795) 50344','Quisque@metusVivamus.com',49879),(74,'Madeson','Powers','1964-09-28','P.O. Box 320, 8048 Aliquam Street','Windsor','U67 9UU','(0114) 475 0052','Curabitur.ut@placerataugueSed.ca',67749),(75,'Otto','Glenn','1956-09-22','P.O. Box 151, 1923 Et St.','Jasper','L39 1KE','(0116) 963 1102','auctor.velit.eget@accumsan.com',50948),(76,'Adena','York','1974-04-22','Ap #196-6242 Vitae Avenue','Oliver','V2 2CX','0500 042838','cursus.a.enim@etipsum.com',54838),(77,'Micah','Dyer','1950-12-11','124-7177 Ut Avenue','Scheggino','P4 5CZ','(024) 9279 2827','non.dapibus.rutrum@Aeneangravida.net',30798),(78,'Dane','Gray','1987-06-28','P.O. Box 820, 7493 Consequat Ave','Colloredo di Monte Albano','DJ5 8DJ','(016977) 9627','et.netus@Fusce.org',69777),(79,'Adria','Goodman','1994-12-09','417-8113 Lorem. Road','Saint-Herblain','NH0 5AC','(0117) 929 2105','metus.sit.amet@ultriciesligulaNullam.com',27369),(80,'Chantale','Owens','1966-10-02','425 Sem Ave','Venezia','F57 8VS','(01515) 555549','sem@lacusQuisquepurus.com',48090),(81,'Wing','Clarke','1990-01-05','P.O. Box 338, 266 Quisque Av.','San Diego','P8 3PD','0800 785226','faucibus@lobortisrisusIn.net',46135),(82,'Griffin','Jackson','1950-05-21','7234 Ligula Rd.','Tilff','O5N 1PU','0500 970148','amet.consectetuer.adipiscing@loremloremluctus.net',25557),(83,'Noah','Cotton','1993-08-09','P.O. Box 188, 3537 Dictum Street','Gespeg','ZK9H 3VK','070 9705 5072','Cum.sociis@erosturpisnon.net',35925),(84,'Maggy','Ryan','1966-11-28','P.O. Box 323, 6717 Velit Ave','Nocera Umbra','CO44 6BZ','0800 543 1814','sociis@turpis.co.uk',29898),(85,'Janna','Tyson','1954-01-16','P.O. Box 583, 2289 Ridiculus Rd.','Bellegem','E1 6OO','(01898) 030306','ullamcorper.nisl.arcu@variusultrices.edu',37125),(86,'Murphy','Phillips','1978-04-29','783-3558 Ornare, Ave','Matagami','L71 3FA','(016977) 7981','tellus@etpedeNunc.org',55695),(87,'Cameron','Hyde','1956-01-30','6435 Non, Avenue','Irricana','DD1 8AA','0394 435 2050','velit@Phasellusnulla.edu',47280),(88,'Zia','Stein','1956-06-07','Ap #974-567 Ornare Ave','New Orleans','BS2P 1RN','0800 017 0190','ac@nislarcuiaculis.edu',41256),(89,'Leigh','Campos','1961-04-13','Ap #262-7992 Nec Rd.','Cisterna di Latina','NH4A 3WC','0979 278 8133','erat.vitae.risus@Pellentesque.ca',25581),(90,'Unity','Elliott','1994-08-07','404-4644 Nulla Av.','Zaltbommel','Q2O 8JM','056 4737 8656','erat.Sed@sagittissemperNam.co.uk',45718),(91,'Yuli','Johns','1954-06-03','4478 Inceptos Av.','Lampernisse','BM2D 6TE','07472 599635','vel.turpis.Aliquam@atiaculisquis.com',26915),(92,'Pamela','Lane','1991-07-16','703-8263 Pharetra St.','Rhayader','GB9P 1TK','07624 933513','quis.massa@lobortis.net',20770),(93,'Faith','Washington','1967-11-16','P.O. Box 143, 1082 At, Av.','Adelaide','GR31 1JU','(027) 8268 8227','amet.dapibus.id@mattisornare.co.uk',30255),(94,'Amos','Jordan','1992-06-28','3612 Phasellus St.','Poggiorsini','NS9I 3TM','076 2453 3662','nisi.magna.sed@fermentumconvallis.co.uk',19949),(95,'Nehru','Franks','1953-01-30','P.O. Box 906, 5321 Nec, St.','Geelong','Y89 0SJ','0800 1111','tempus.lorem@Donecfringilla.edu',65417),(96,'Duncan','Bray','1959-01-02','Ap #994-6815 Cursus Street','Oppido Mamertina','P9A 2PD','0994 496 8368','parturient.montes@dictumplacerataugue.net',17919),(97,'Brett','Stanley','1964-06-15','5405 Commodo Rd.','Lakeshore','Z8 6VJ','0845 46 46','non.sollicitudin@milacinia.edu',58945),(98,'Danielle','Allison','1979-03-17','9573 Eu Avenue','Nordhorn','RD8 3FV','(016977) 3568','posuere@ultricesVivamus.edu',52326),(99,'Shana','Rocha','1963-01-01','2542 Convallis Rd.','Malonne','F3 8HY','(019865) 83226','ut@consequatauctor.com',17975),(100,'Isaiah','Olsen','1971-08-23','P.O. Box 428, 2300 Fermentum Rd.','BÃ©ziers','OX0B 4MB','(027) 6561 1418','non.quam.Pellentesque@et.org',43016);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `dob` date DEFAULT NULL,
  `telephone` varchar(15) DEFAULT NULL,
  `address1` varchar(50) NOT NULL,
  `address2` varchar(50) DEFAULT NULL,
  `town` varchar(50) NOT NULL,
  `postcode` varchar(50) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('isabella','rodz','isabella','rodriguez','1992-11-30','0700093719','kavallerigatan','dde','upplands','19475'),('Test@test.se','123','Test','Testson','1949-09-09','0807384756','Stockholmsv. 32','C/O Olsen','Stockholm','postcode');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-11 12:17:27
