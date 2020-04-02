-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.29-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for holidaymaker
CREATE DATABASE IF NOT EXISTS `holidaymaker` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `holidaymaker`;

-- Dumping structure for table holidaymaker.accommodations
CREATE TABLE IF NOT EXISTS `accommodations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `facility` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_accommodation_facilities` (`facility`) USING BTREE,
  CONSTRAINT `FK_accommodation_facilities` FOREIGN KEY (`facility`) REFERENCES `facilities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.accommodations: ~4 rows (approximately)
/*!40000 ALTER TABLE `accommodations` DISABLE KEYS */;
INSERT INTO `accommodations` (`id`, `name`, `address`, `city`, `country`, `facility`) VALUES
	(1, 'Park Hyatt New York', '153 W 57th St', 'New York', 'USA', 2),
	(2, 'Sheraton Kuta', 'Jl. Pantai Kuta', 'Bali', 'Indonesia', 1),
	(3, 'Nikko Tokyo', '2 Chome-2-1 Nishishinjuku', 'Tokyo', 'Japan', 3),
	(4, 'Four Seasons London', '19 New Bridge St.', 'London', 'UK', 2),
	(10, 'Scandic Continental', 'Vasagatan 22', 'Stockholm', 'Sweden', 4),
	(11, 'Disneyland Paris', 'Rue de la Marnière', 'Chessy', 'France', 1),
	(12, 'Hotel Indonesia Kempinski', 'Jl. M.H. Thamrin No.1', 'Jakarta', 'Indonesia', 1),
	(13, 'Holiday Inn Express', '11250 Santa Monica Blvd', 'West Los Angeles', 'USA', 4),
	(14, 'Maasai Lodge', 'Nairobi National Park', 'Nairobi', 'Kenya', 3),
	(15, 'The Westin Gurgaon', 'Number 1 Mg Road, Sector 29', 'New Delhi', 'India', 2);
/*!40000 ALTER TABLE `accommodations` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.bookingrooms_date
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `bookingrooms_date` (
	`check_in` DATE NOT NULL,
	`check_out` DATE NOT NULL,
	`room_id` INT(11) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.bookings
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  `guest` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK__guests` (`guest`) USING BTREE,
  CONSTRAINT `FK_bookings_guests` FOREIGN KEY (`guest`) REFERENCES `guests` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.bookings: ~39 rows (approximately)
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` (`id`, `check_in`, `check_out`, `guest`) VALUES
	(1, '2020-06-01', '2020-06-04', 1),
	(4, '2020-07-09', '2020-07-31', 6),
	(6, '2020-06-11', '2020-06-15', 9),
	(7, '2020-06-18', '2020-06-13', 11),
	(8, '2020-05-31', '2020-06-02', 11),
	(9, '2020-06-05', '2020-09-02', 11),
	(10, '2020-06-02', '2020-06-05', 11),
	(12, '2020-06-02', '2020-06-05', 11),
	(13, '2020-06-02', '2020-06-06', 11),
	(14, '2020-06-01', '2020-06-04', 11),
	(15, '2020-05-31', '2020-06-04', 11),
	(16, '2020-06-02', '2020-06-04', 11),
	(17, '2020-06-02', '2020-06-04', 11),
	(18, '2020-06-02', '2020-06-05', 11),
	(19, '2020-06-01', '2020-06-04', 11),
	(20, '2020-06-02', '2020-06-05', 11),
	(21, '2020-06-01', '2020-06-04', 11),
	(22, '2020-06-03', '2020-06-04', 11),
	(23, '2020-06-02', '2020-06-08', 11),
	(24, '2020-06-01', '2020-06-05', 11),
	(25, '2020-06-05', '2020-06-07', 11),
	(26, '2020-06-03', '2020-06-06', 11),
	(27, '2020-06-02', '2020-06-04', 11),
	(29, '2020-06-03', '2020-06-07', 11),
	(31, '2020-06-02', '2020-06-06', 11),
	(32, '2020-06-02', '2020-06-05', 11),
	(33, '2020-06-02', '2020-06-06', 11),
	(34, '2020-06-01', '2020-06-04', 11),
	(35, '2020-05-31', '2020-06-29', 1),
	(36, '2020-06-02', '2020-06-05', 2),
	(37, '2020-06-02', '2020-06-08', 9),
	(38, '2020-06-03', '2020-06-02', 12),
	(39, '2020-05-31', '2020-06-01', 12),
	(40, '2020-06-04', '2020-06-05', 12),
	(41, '2020-06-07', '2020-06-09', 1),
	(42, '2020-05-31', '2020-06-02', 4),
	(43, '2020-05-31', '2020-06-02', 3),
	(44, '2020-05-31', '2020-06-02', 5),
	(45, '2020-06-30', '2020-07-03', 6),
	(46, '2020-05-31', '2020-06-19', 13),
	(47, '2020-06-01', '2020-05-31', 8),
	(48, '2020-06-02', '2020-05-31', 8),
	(49, '2020-06-06', '2020-06-30', 8);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.booking_x_rooms
CREATE TABLE IF NOT EXISTS `booking_x_rooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room` int(11) unsigned NOT NULL DEFAULT '0',
  `booking` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_booking_x_rooms_bookings` (`booking`) USING BTREE,
  KEY `FK_bookingxrooms_accommodation` (`room`) USING BTREE,
  KEY `rooms_id` (`room`) USING BTREE,
  CONSTRAINT `FK_booking_x_rooms_bookings` FOREIGN KEY (`booking`) REFERENCES `bookings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_booking_x_rooms_rooms` FOREIGN KEY (`room`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.booking_x_rooms: ~10 rows (approximately)
/*!40000 ALTER TABLE `booking_x_rooms` DISABLE KEYS */;
INSERT INTO `booking_x_rooms` (`id`, `room`, `booking`) VALUES
	(2, 30, 1),
	(3, 6, 4),
	(5, 2, 40),
	(6, 25, 41),
	(7, 2, 41),
	(8, 11, 42),
	(9, 11, 43),
	(10, 36, 43),
	(11, 36, 44),
	(12, 25, 45),
	(13, 25, 45),
	(14, 46, 46),
	(15, 39, 46),
	(16, 33, 49);
/*!40000 ALTER TABLE `booking_x_rooms` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.facilities
CREATE TABLE IF NOT EXISTS `facilities` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pool` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `restaurant` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `kids_club` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `night_club` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.facilities: ~4 rows (approximately)
/*!40000 ALTER TABLE `facilities` DISABLE KEYS */;
INSERT INTO `facilities` (`id`, `pool`, `restaurant`, `kids_club`, `night_club`) VALUES
	(1, 1, 1, 1, 1),
	(2, 1, 1, 1, 0),
	(3, 1, 1, 0, 0),
	(4, 0, 1, 0, 1);
/*!40000 ALTER TABLE `facilities` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.guests
CREATE TABLE IF NOT EXISTS `guests` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `email` varchar(50) NOT NULL DEFAULT '0',
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.guests: ~12 rows (approximately)
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` (`id`, `name`, `email`, `username`) VALUES
	(1, 'Kurnia', 'kurnia@gmail.com', 'kanios'),
	(2, 'Filip', 'fille@yahoo.com', 'file'),
	(3, 'Kai', 'kai@yandex.ru', 'kai3'),
	(4, 'Stella', 'stella@hotmail.com', 'stella96'),
	(5, 'Ron', 'ron@gmail.com', 'ron23'),
	(6, 'Pippi', 'pi@gmail.com', 'pippiStrumpor'),
	(7, 'Tim', 'tim@yahoo.com', 'tim24'),
	(8, 'Jen', 'jen@rambler.ru', 'jen3'),
	(9, 'Britney', 'brit@y.com', 'babyonemoretime'),
	(10, 'Karl', 'karl@gmail.com', 'karl90'),
	(11, 'Lady', 'lad@gmail.com', 'ladyday'),
	(12, 'Lee', 'lee@g.com', 'lee1'),
	(13, 'Boy', 'boyd@gmail.com', 'boy123'),
	(14, 'Maria', 'mar@g.com', 'mar123');
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.rooms
CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room_type` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_rooms_room_types` (`room_type`) USING BTREE,
  CONSTRAINT `FK_rooms_room_types` FOREIGN KEY (`room_type`) REFERENCES `room_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.rooms: ~19 rows (approximately)
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` (`id`, `room_type`) VALUES
	(1, 1),
	(30, 2),
	(28, 3),
	(31, 3),
	(35, 3),
	(25, 5),
	(2, 6),
	(29, 6),
	(6, 7),
	(23, 7),
	(34, 7),
	(15, 8),
	(33, 9),
	(7, 10),
	(11, 13),
	(13, 13),
	(47, 13),
	(4, 14),
	(12, 14),
	(40, 14),
	(36, 15),
	(46, 25),
	(39, 26),
	(45, 29),
	(51, 30),
	(44, 31),
	(38, 32),
	(55, 33),
	(50, 34),
	(43, 35),
	(37, 36),
	(54, 37),
	(49, 38),
	(42, 39),
	(53, 40),
	(48, 41),
	(41, 42),
	(52, 43);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.roomsxroom_types
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `roomsxroom_types` (
	`id` INT(11) UNSIGNED NOT NULL,
	`guests_capacity` INT(10) UNSIGNED NOT NULL,
	`description` TEXT(65535) NULL COLLATE 'latin1_swedish_ci',
	`accommodation` INT(10) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.room_types
CREATE TABLE IF NOT EXISTS `room_types` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `accommodation` int(10) unsigned NOT NULL,
  `description` text,
  `guests_capacity` int(10) unsigned NOT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_room_types_accommodation` (`accommodation`) USING BTREE,
  CONSTRAINT `FK_room_types_accommodation` FOREIGN KEY (`accommodation`) REFERENCES `accommodations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.room_types: ~15 rows (approximately)
/*!40000 ALTER TABLE `room_types` DISABLE KEYS */;
INSERT INTO `room_types` (`id`, `accommodation`, `description`, `guests_capacity`, `price`) VALUES
	(1, 1, 'Single', 1, 4500),
	(2, 1, 'Double', 2, 5500),
	(3, 1, 'Family', 4, 6500),
	(4, 1, 'VIP', 3, 20000),
	(5, 2, 'Single', 1, 2000),
	(6, 2, 'Double', 2, 3000),
	(7, 2, 'Family', 4, 4000),
	(8, 2, 'VIP', 3, 8325),
	(9, 3, 'Single', 1, 3700),
	(10, 3, 'Double', 2, 4800),
	(11, 3, 'VIP', 3, 12000),
	(12, 4, 'Single', 1, 4000),
	(13, 4, 'Double', 2, 5000),
	(14, 4, 'Family', 4, 7200),
	(15, 4, 'VIP', 3, 15000),
	(25, 11, 'Double', 2, 2200),
	(26, 11, 'Family', 4, 3500),
	(27, 11, 'Single', 1, 1500),
	(28, 13, 'Single', 1, 1700),
	(29, 13, 'Double', 2, 3000),
	(30, 12, 'Single', 1, 1200),
	(31, 12, 'Double', 2, 2300),
	(32, 12, 'Family', 4, 3200),
	(33, 12, 'VIP', 3, 7600),
	(34, 14, 'Single', 1, 800),
	(35, 14, 'Double', 2, 1300),
	(36, 14, 'Family', 4, 2000),
	(37, 14, 'VIP', 3, 5000),
	(38, 15, 'Single', 1, 1000),
	(39, 15, 'Double', 2, 1600),
	(40, 15, 'VIP', 3, 8000),
	(41, 10, 'Single', 1, 1600),
	(42, 10, 'Double', 2, 2400),
	(43, 10, 'VIP', 3, 8700);
/*!40000 ALTER TABLE `room_types` ENABLE KEYS */;

-- Dumping structure for view holidaymaker.bookingrooms_date
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `bookingrooms_date`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `bookingrooms_date` AS select `bookings`.`check_in` AS `check_in`,`bookings`.`check_out` AS `check_out`,`booking_x_rooms`.`id` AS `room_id` from (`bookings` join `booking_x_rooms` on((`bookings`.`id` = `booking_x_rooms`.`booking`)));

-- Dumping structure for view holidaymaker.roomsxroom_types
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `roomsxroom_types`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `roomsxroom_types` AS select `rooms`.`id` AS `id`,`room_types`.`guests_capacity` AS `guests_capacity`,`room_types`.`description` AS `description`,`room_types`.`accommodation` AS `accommodation` from (`rooms` join `room_types` on((`rooms`.`room_type` = `room_types`.`id`)));

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
