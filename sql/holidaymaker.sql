-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.29-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for holidaymaker
CREATE DATABASE IF NOT EXISTS `holidaymaker` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_swedish_ci */;
USE `holidaymaker`;

-- Dumping structure for table holidaymaker.accommodations
CREATE TABLE IF NOT EXISTS `accommodations` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `city` varchar(250) NOT NULL,
  `country` varchar(250) NOT NULL,
  `swimming_pool` bit(1) DEFAULT b'0',
  `night_club` bit(1) DEFAULT b'0',
  `restaurant` bit(1) DEFAULT b'0',
  `kids_club` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.accommodations: ~7 rows (approximately)
/*!40000 ALTER TABLE `accommodations` DISABLE KEYS */;
INSERT INTO `accommodations` (`id`, `name`, `address`, `city`, `country`, `swimming_pool`, `night_club`, `restaurant`, `kids_club`) VALUES
	(1, 'Park Hyatt New York', '153 W 57th St', 'New York', 'USA', b'1', b'1', b'1', b'0'),
	(2, 'Sheraton Kuta', 'Jl. Pantai Kuta', 'Bali', 'Indonesia', b'1', b'1', b'1', b'1'),
	(3, 'Nikko Tokyo', '2 Chome-2-1 Nishishinjuku', 'Tokyo', 'Japan', b'1', b'1', b'1', b'1'),
	(4, 'Four Seasons London', '19 New Bridge St.', 'London', 'UK', b'0', b'1', b'1', b'0'),
	(5, 'President Hotel', '4 Alexander Rd, Bantry Bay', 'Cape Town', 'South Africa', b'1', b'1', b'1', b'1'),
	(6, 'Intercontinental Hotel', 'Teatral\'nyy Proyezd 2', 'Moscow', 'Russia', b'0', b'1', b'1', b'0'),
	(7, 'W Hotel', 'Jl. Pantai Kuta', 'Bali', 'Indonesia', b'0', b'1', b'1', b'1');
/*!40000 ALTER TABLE `accommodations` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.bookings
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `guest_id` int(11) unsigned NOT NULL DEFAULT '0',
  `room_id` int(11) unsigned NOT NULL DEFAULT '0',
  `number_of_guests` int(11) unsigned NOT NULL DEFAULT '0',
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `total_price` double unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_bookings_guests` (`guest_id`),
  KEY `FK_bookings_rooms` (`room_id`),
  CONSTRAINT `FK_bookings_guests` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_bookings_rooms` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.bookings: ~4 rows (approximately)
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` (`id`, `guest_id`, `room_id`, `number_of_guests`, `check_in_date`, `check_out_date`, `total_price`) VALUES
	(3, 2, 4, 2, '2020-07-14', '2020-07-17', 36000),
	(4, 1, 6, 1, '2020-06-01', '2020-06-04', 2100),
	(5, 3, 3, 4, '2020-07-24', '2020-07-26', 7000),
	(6, 5, 7, 2, '2020-06-18', '2020-06-21', 6900);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.guests
CREATE TABLE IF NOT EXISTS `guests` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.guests: ~5 rows (approximately)
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` (`id`, `name`, `email`) VALUES
	(1, 'Jane Doe', 'jane@gmail.com'),
	(2, 'John Depp', 'john@me.com'),
	(3, 'Filip', 'fille@gmail.com'),
	(4, 'Ella', 'ella@mail.ru'),
	(5, 'Milena', 'mile@wow.com');
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.rooms
CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room_type_id` int(11) unsigned NOT NULL DEFAULT '0',
  `accommodation_id` int(11) unsigned NOT NULL DEFAULT '0',
  `room_price` double unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_rooms_accommodations` (`accommodation_id`),
  KEY `FK_rooms_room_types` (`room_type_id`),
  CONSTRAINT `FK_rooms_accommodations` FOREIGN KEY (`accommodation_id`) REFERENCES `accommodations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_rooms_room_types` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.rooms: ~7 rows (approximately)
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` (`id`, `room_type_id`, `accommodation_id`, `room_price`) VALUES
	(1, 2, 4, 2000),
	(2, 1, 1, 2500),
	(3, 3, 2, 3500),
	(4, 4, 3, 12000),
	(5, 2, 5, 2200),
	(6, 1, 7, 700),
	(7, 2, 6, 2300);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;

-- Dumping structure for table holidaymaker.room_types
CREATE TABLE IF NOT EXISTS `room_types` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room_type` enum('Single','Double','Family','VIP Suite') NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table holidaymaker.room_types: ~4 rows (approximately)
/*!40000 ALTER TABLE `room_types` DISABLE KEYS */;
INSERT INTO `room_types` (`id`, `room_type`, `description`) VALUES
	(1, 'Single', 'A standard single room for one person'),
	(2, 'Double', 'A standard double room for two person'),
	(3, 'Family', 'A standard family room for two adults and two kids or max. 4 occupancies'),
	(4, 'VIP Suite', 'A VIP suite room for two person');
/*!40000 ALTER TABLE `room_types` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
