CREATE DATABASE  IF NOT EXISTS `jobportal` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jobportal`;
-- MySQL dump 10.13  Distrib 5.7.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: jobportal
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `Id` int(11) NOT NULL,
  `SendDate` date NOT NULL,
  `DocName` varchar(255) NOT NULL,
  `Nr_Ref` char(20) NOT NULL,
  `Firma` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`,`SendDate`,`DocName`),
  KEY `Nr_Ref` (`Nr_Ref`),
  KEY `Firma` (`Firma`),
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`Nr_Ref`) REFERENCES `offers` (`Nr_Ref`),
  CONSTRAINT `application_ibfk_2` FOREIGN KEY (`Firma`) REFERENCES `client` (`CompanyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,'2017-07-05','cv1.pdf','AB/BV/IN99','9900053217'),(2,'2017-07-05','cv2.pdf','AK/WA/PZ22','2299005516'),(3,'2017-07-05','cv3.pdf','AZ/GD/BE78','789003410'),(4,'2017-07-05','cv4.pdf','KP/MA/MA34','3451112095'),(5,'2017-07-05','CV5.pdf','KS/WR/SO12','1234567892'),(6,'2017-07-05','cv6.pdf','MA/BY/MA21','2100344587'),(7,'2017-07-05','cv7.pdf','MR1/PO/MA49','4900751901'),(8,'2017-07-05','cv8.pdf','OC/WR/MA90','9005346785'),(9,'2017-07-05','cv9.pdf','OW/PŁ/LO88','8805302221'),(10,'2017-07-05','cv10.pdf','PH/ZG/IG56','5677653210'),(11,'2017-07-05','cv11.pdf','SE/GD/SO12','1234567892'),(12,'2017-07-05','cv12.pdf','SS/WA/SA87','8770533221');
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `Nazwa` varchar(255) NOT NULL,
  `Ulica` varchar(255) NOT NULL,
  `Kod` varchar(255) NOT NULL,
  `Miasto` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `CompanyId` varchar(255) NOT NULL,
  `Data_zal_kon` date DEFAULT NULL,
  PRIMARY KEY (`CompanyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('Softpol','Promienista 16','60-600','Poznań','softpol@soft.com','Soft123','1234567892','2017-05-19'),('Makro','Warszawska 30','40-539','Bytom','makro@poczta.pl','12A_34fcfA','2100344587','2017-02-05'),('PZU S.A.','Al. Jana Pawła II 24','00-133','Warszawa','pzu@pzu.pl','pzu1890_a','2299005516','2017-01-23'),('Mars','Magazynowa 30','60-834','Sochaczew','mars@mail.com','Mar20_30','3451112095','2017-01-23'),('Konimpex','Nadsądecka 32','50-33','Konin','konimpex@firma.pl','Koni23_2D','4400556612','2017-06-06'),('Manpower','Trójpole 7','60-245','Poznań','makro@poczta.pl','AxDfGG543','4900751901','2017-04-20'),('Ignity','Promienista 40','67-042','Zielona Góra','inity@poczta.pl','Ignis2019','5677653210','2017-02-20'),('Agrobex','Kochanowskiego 7','60-692','Poznań','mieszkania@agrobex.pl','agrohouse20','678892211','2017-07-08'),('PolSteel','Estońska 34','50-112','Gliwice','polsteel@gmail.com','pst23_Qwe','7783210067','2017-06-03'),('BEST','Pomorska 54','34-569','Gdańsk','best@dot.com','best@com21','789003410','2017-05-01'),('SaleCom','Ostrowska 78','00-669','Warszawa','sc@so.com','sof12_3sdA','8770533221','2017-03-30'),('Logitex','Mazowiecka 12','49-492','Płock','logitex@ovh.com','201Logi1','8805302221','2017-04-20'),('Manufact','Bryszewska 15','30-456','Wrocław','manu@mail.co','as_12@newD','9005346785','2017-05-01'),('PolExport','Gronowa 15','45-678','Wrocław','pexp@domena.pl','pEXP123A','987654321','2017-05-24'),('Interactive','Poznańska 2','20-890','Bydgoszcz','inter@mail.com','Int32_15@','9900053217','2017-03-15');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_data`
--

DROP TABLE IF EXISTS `contact_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_data` (
  `Name_LastName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  PRIMARY KEY (`Name_LastName`,`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_data`
--

LOCK TABLES `contact_data` WRITE;
/*!40000 ALTER TABLE `contact_data` DISABLE KEYS */;
INSERT INTO `contact_data` VALUES ('Jan Nowak','jnowak@onet.pl'),('Marcin Prokop','asd123@gazeta.pl'),('Piotr Piotrowski','piotrek@piotrek.pl'),('Łukasz Nowacki','nowi16@wp.pl');
/*!40000 ALTER TABLE `contact_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactmessages`
--

DROP TABLE IF EXISTS `contactmessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactmessages` (
  `Name_LastName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Message` text,
  `GetDate` date DEFAULT NULL,
  KEY `Name_LastName` (`Name_LastName`,`Email`),
  CONSTRAINT `contactmessages_ibfk_1` FOREIGN KEY (`Name_LastName`, `Email`) REFERENCES `contact_data` (`Name_LastName`, `Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactmessages`
--

LOCK TABLES `contactmessages` WRITE;
/*!40000 ALTER TABLE `contactmessages` DISABLE KEYS */;
INSERT INTO `contactmessages` VALUES ('Jan Nowak','jnowak@onet.pl','Proszę o kontakt','2017-07-04'),('Marcin Prokop','asd123@gazeta.pl','Kiedy nastąpi przywrócenie serwisu?','2017-07-04'),('Łukasz Nowacki','nowi16@wp.pl','To jest wiadomość testowa','2017-07-04'),('Marcin Prokop','asd123@gazeta.pl','Test integralności ','2017-07-04');
/*!40000 ALTER TABLE `contactmessages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `ClientID` int(11) NOT NULL,
  `DataAplikacji` date NOT NULL,
  `Stanowisko` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (1,'2017-07-05','Pracownik administracyjno-biurowy'),(2,'2017-07-05','Aktuariusz'),(3,'2017-07-05','Asystentka Zarządu'),(4,'2017-07-05','Kierownik produkcji'),(5,'2017-07-05','Księgowy'),(6,'2017-07-05','Magazynier'),(7,'2017-07-05','Młodszy Rekruter'),(8,'2017-07-05','Operator CNC'),(9,'2017-07-05','Operator wózka widłowego'),(10,'2017-07-05','Przedstawiciel Handlowy'),(11,'2017-07-05','Sekretarka'),(12,'2017-07-05','Specjalista ds. Sprzedaży');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maildb`
--

DROP TABLE IF EXISTS `maildb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `maildb` (
  `UserID` int(11) NOT NULL,
  `CompanyID` varchar(255) NOT NULL,
  `Nr_Ref` char(20) NOT NULL,
  PRIMARY KEY (`UserID`,`CompanyID`,`Nr_Ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maildb`
--

LOCK TABLES `maildb` WRITE;
/*!40000 ALTER TABLE `maildb` DISABLE KEYS */;
INSERT INTO `maildb` VALUES (0,'1234567892','SE/GD/SO12'),(1,'9900053217','AB/BV/IN99'),(2,'2299005516','AK/WA/PZ22'),(3,'789003410','AZ/GD/BE78'),(3,'9900053217','AB/BV/IN99'),(4,'3451112095','KP/MA/MA34'),(4,'9900053217','AB/BV/IN99'),(5,'1234567892','KS/WR/SO12'),(6,'2100344587','MA/BY/MA21'),(7,'2100344587','MA/BY/MA21'),(7,'4900751901','MR1/PO/MA49'),(8,'9005346785','OC/WR/MA90'),(9,'8805302221','OW/PŁ/LO88'),(9,'9005346785','OC/WR/MA90'),(10,'5677653210','PH/ZG/IG56'),(11,'1234567892','SE/GD/SO12'),(12,'8770533221','SS/WA/SA87'),(13,'2100344587','MA/BY/MA21'),(14,'','MR1/PO/MA49'),(14,'','OC/PŁ/LO88'),(14,'8770533221','SS/WA/SA87'),(15,'1234567892','SE/GD/SO12');
/*!40000 ALTER TABLE `maildb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messenger`
--

DROP TABLE IF EXISTS `messenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messenger` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Message` text,
  `SendDate` date DEFAULT NULL,
  `UserID` int(11) NOT NULL,
  `CompanyID` varchar(255) NOT NULL,
  `Nr_Ref` char(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Messenger_ids` (`UserID`,`CompanyID`,`Nr_Ref`),
  CONSTRAINT `FK_Messenger_ids` FOREIGN KEY (`UserID`, `CompanyID`, `Nr_Ref`) REFERENCES `maildb` (`UserID`, `CompanyID`, `Nr_Ref`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messenger`
--

LOCK TABLES `messenger` WRITE;
/*!40000 ALTER TABLE `messenger` DISABLE KEYS */;
INSERT INTO `messenger` VALUES (1,'Czy jest możliwość relokacji?','2017-07-06',1,'9900053217','AB/BV/IN99'),(2,'Czy ogłoszenie jest jeszcze aktualne?','2017-07-06',2,'2299005516','AK/WA/PZ22'),(3,'Proszę o podanie widełek','2017-07-06',3,'789003410','AZ/GD/BE78'),(4,'Proszę o podanie widełek','2017-07-06',3,'9900053217','AB/BV/IN99'),(8,'Na kiedy przewidywany jest termin rozpoczęcia?','2017-07-06',4,'3451112095','KP/MA/MA34'),(9,'Czy zapomniano podać wynagrodzenia?','2017-07-06',4,'9900053217','AB/BV/IN99'),(10,'Czy angielski na poziomie B2 wystarczy?','2017-07-06',5,'1234567892','KS/WR/SO12'),(11,'Czy uprawnienia na wózki jest konieczne?','2017-07-06',6,'2100344587','MA/BY/MA21'),(12,'Jaki typ umowy?','2017-07-06',7,'4900751901','MR1/PO/MA49'),(13,'How many years of experience is needed?','2017-07-06',8,'9005346785','OC/WR/MA90'),(14,'Czy zagraniczna licencja jest uwzględniana?','2017-07-06',9,'8805302221','OW/PŁ/LO88'),(15,'Kim są klienci firmy?','2017-07-06',10,'5677653210','PH/ZG/IG56'),(16,'Przewidywany termin rozpoczęcia?','2017-07-06',11,'1234567892','SE/GD/SO12'),(17,'Czy prawo jazdy jest wymagane?','2017-07-06',12,'8770533221','SS/WA/SA87'),(19,'Tak, B2 wystarczy','2017-07-07',5,'1234567892','KS/WR/SO12'),(20,'Do you offer Multisport card?','2017-07-08',9,'9005346785','OC/WR/MA90'),(22,'Czy to jest praca w systemie zmianowym?','2017-07-08',13,'2100344587','MA/BY/MA21');
/*!40000 ALTER TABLE `messenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offers` (
  `Stanowisko` varchar(255) NOT NULL,
  `Miasto` varchar(255) NOT NULL,
  `Nr_Ref` char(20) NOT NULL,
  `Firma` varchar(255) NOT NULL,
  `Wynagrodzenie` varchar(255) DEFAULT NULL,
  `Opis` text,
  `DataWyst` date DEFAULT NULL,
  `DataZakon` date DEFAULT NULL,
  `ClientId` varchar(255) NOT NULL,
  PRIMARY KEY (`Nr_Ref`,`Firma`),
  KEY `ClientId` (`ClientId`),
  CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`ClientId`) REFERENCES `client` (`CompanyId`),
  CONSTRAINT `offers_ibfk_2` FOREIGN KEY (`ClientId`) REFERENCES `client` (`CompanyId`),
  CONSTRAINT `offers_ibfk_3` FOREIGN KEY (`ClientId`) REFERENCES `client` (`CompanyId`),
  CONSTRAINT `offers_ibfk_4` FOREIGN KEY (`ClientId`) REFERENCES `client` (`CompanyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES ('Pracownik administracyjno-biurowy','Bydgoszcz','AB/BV/IN99','Interactive',NULL,'Osoba na tym stanowisku będzie odpowiedzialna za prowadzenia biura,przepływ dokumentów pomiędzy firmą, Klientami, księgowością oraz magazynem. Szukamy osoby samodzielnej, zaangażowanej i operatywnej, \nświetnie zorganizowanej, która lubi wyzwania i chętnie wdraża swoje pomysły.','2017-06-24','2017-07-17','9900053217'),('Aktuariusz','Warszawa','AK/WA/PZ22','PZU S.A.','8000','Podstawowe zadania:udział w kwotacji produktów i monitoringu,badanie adekwatności rezerw techniczno-ubezpieczeniowych na cele statutowe, współudział w ustalaniu metodologii,\nudział w raportowaniu zarządczym,udział w procesie ORSA.','2017-06-30','2017-07-25','2299005516'),('Asystentka Zarządu','Gdańsk','AZ/GD/BE78','BEST',NULL,'Firma proponuje:zatrudnienie na podstawie umowy o pracę w stabilnej finansowo firmie,pracę w biurze w Poznaniu,szkolenia,pracę w młodym i dynamicznym zespole.','2017-06-30','2017-07-16','789003410'),('Kierownik produkcji','Sochaczew','KP/MA/MA34','Mars','6000','Opis stanowiska pracy:Kierowanie i koordynowanie działań mających na celu rozwój firmy, w tym nadzór nad działem Utrzymania Ruchu oraz Kontroli Jakości oraz Działu Produkcji. Nadzór nad zakupami związanymi z potrzebami w danym momencie.\nNadzór Przemysłowy,Zapoznanie się z parkiem maszynowym oraz procesem produkcyjnym w tym przypadku tekstylnym (nici, taśmy)','2017-06-29','2017-07-18','3451112095'),('Księgowy','Wrocław','KS/WR/SO12','Softpol','3500','Wymagamy 3 letniego doświadczenia','2017-07-05','2017-07-30','1234567892'),('Magazynier','Bytom','MA/BY/MA21','Makro','2500','Obowiązki:załadunek, rozładunek, dostawa towarów,kompletowanie i paczkowanie wysyłek,wprowadzanie danych do aplikacji systemowych\nwystawiania dokumentacji magazynowej,Wymagania:prawo jazdy kat B,sumienność, pracowitość oraz obowiązkowość,dobra organizacja pracy \nOferujemy:umowę o pracę,atrakcyjne warunki zatrudnienia','2017-06-24','2017-07-17','2100344587'),('Młodszy Rekruter','Poznań','MR1/PO/MA49','Manpower','4000','Jeżeli :Chcesz rekrutować specjalistów IT w modelu rekrutacji stałej jak i kontraktorów,Lubisz rozmawiać z ludźmi,Lubisz szukać,\nLubisz analizować CV.','2017-06-30','2017-07-31','4900751901'),('Operator CNC','Wrocław','OC/WR/MA90','Manufact','5000','This role will implement the programmes as designed by the CNC programmer to manufacture a component.\nPerforming CNC operations using a variety of different machines - including lathes, milling, EDM, and other CNC equipment. Key Accountabilities and Responsibilities\nOr we will consider an individual who has a can do attitude who has been use to operating high end machines but not necessarily CNC machines','2017-06-30','2017-07-21','9005346785'),('Operator wózka widłowego','Płock','OW/PŁ/LO88','Logitex',NULL,'Opis stanowiska: Praca w firmie produkcyjnej, Praca w systemie zmianowym, Operowanie wózkiem widłowym (rozładowywanie towaru, załadowywanie towaru, przemieszczanie się po hali, inne prace zlecone w dziale logistyki)\nPraca w magazynie wysokiego składowania (pow. 3 m), Współpraca z przewoźnikami, działami produkcji','2017-06-30','2017-08-01','8805302221'),('Przedstawiciel Handlowy','Zielona Góra','PH/ZG/IG56','Ignity','6000','Wybrana osoba będzie odpowiedzialna za:aktywną sprzedaż systemów monitorowania pojazdów GPS,\npozyskiwanie i budowanie bazy klientów,organizacje spotkań biznesowych  i negocjacje handlowe,kreowanie pozytywnego wizerunku firmy','2017-06-28','2017-07-28','5677653210'),('Sekretarka','Gdańsk','SE/GD/SO12','Softpol','2500','Poszukujemy osoby do prowadzenia sekretariatu','2017-07-02','2017-07-13','1234567892'),('Specjalista ds. Sprzedaży','Warszawa','SS/WA/SA87','SaleCom','3500','Szukamy kandydata, który:posiada minimum roczne doświadczenie w pracy w branży hotelarskiej, np. na stanowiskach: recepcjonista, specjalista ds. marketingu, manager, team leader lub zna branżę gastronomiczną\nposiada doświadczenie w sprzedaży, jest samodzielny oraz doskonale organizuje swoją pracę','2017-07-01','2017-07-20','8770533221');
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resume`
--

DROP TABLE IF EXISTS `resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resume` (
  `Id` int(11) NOT NULL,
  `FileName` varchar(255) NOT NULL,
  `FileSize` int(11) DEFAULT NULL,
  `SendDate` date DEFAULT NULL,
  `UserID` int(11) NOT NULL,
  PRIMARY KEY (`Id`,`FileName`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `resume_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`Id`),
  CONSTRAINT `resume_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`Id`),
  CONSTRAINT `resume_ibfk_3` FOREIGN KEY (`UserID`) REFERENCES `users` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume`
--

LOCK TABLES `resume` WRITE;
/*!40000 ALTER TABLE `resume` DISABLE KEYS */;
INSERT INTO `resume` VALUES (1,'cv1.pdf',8512,'2017-07-05',1),(2,'cv2.pdf',8397,'2017-07-05',2),(3,'cv12.pdf',10304,'2017-07-08',3),(3,'cv3.pdf',17237,'2017-07-05',3),(4,'cv4.pdf',9048,'2017-07-05',4),(5,'CV5.pdf',9912,'2017-07-05',5),(6,'cv6.pdf',9877,'2017-07-05',6),(7,'cv7.pdf',9528,'2017-07-05',7),(8,'cv8.pdf',10058,'2017-07-05',8),(9,'cv9.pdf',9588,'2017-07-05',9),(10,'cv10.pdf',9968,'2017-07-05',10),(11,'cv11.pdf',9377,'2017-07-05',11),(12,'cv12.pdf',10304,'2017-07-05',12);
/*!40000 ALTER TABLE `resume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `Imie` varchar(255) NOT NULL,
  `Nazwisko` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Data_ur` date NOT NULL,
  `Data_zal_kon` date NOT NULL,
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Piotr','Nowak','nowak10@gmail.com','p.nowak10','1980-08-13','2017-05-19',1),('Antoni','Antczak','aantek@gmail.com','LAS123u4a','1950-06-04','2017-05-24',2),('Teresa','Jaworska','tjawor@onet.pl','nowypswd84','1984-11-07','2017-06-03',3),('Marta','Steczkowska','mstecz@interia.pl','MAR12_ed','1970-12-04','2017-06-04',4),('Juliusz','Kowalski','jkowa@o2.pl','Jakoow2','1978-08-30','2017-06-28',5),('Jadwiga','Piechocka','jpiechoc@gazeta.pl','Piech123','1985-08-30','2017-06-28',6),('Gerard','Tomczak','gtomy@tar.pl','Tomy83N','1989-03-20','2017-07-04',7),('Beata','Słowacka','b.slaw@gazeta.pl','nowyden32','1996-11-11','2017-07-04',8),('Paweł','Piskorski','psorz@wp.pl','polan12@','1967-09-09','2017-07-04',9),('Józef','Załęcki','zajdel@wp.pl','polzal00','1980-01-01','2017-07-04',10),('Patrycja','Zielińska','pziela@onet.pl','asdfgh11','1979-08-21','2017-07-04',11),('Przemysław','Majdan','internal@interia.pl','majdanpol22','1999-12-12','2017-07-04',12),('Lucjan','Błaszyk','lblasz@yahoo.com','lblasz2012','1990-02-02','2017-07-08',13);
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

-- Dump completed on 2017-07-09 23:14:07
