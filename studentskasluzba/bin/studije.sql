-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 27, 2020 at 05:30 PM
-- Server version: 10.4.10-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studije`
--

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

DROP TABLE IF EXISTS `korisnici`;
CREATE TABLE IF NOT EXISTS `korisnici` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Ime` varchar(30) DEFAULT NULL,
  `Prezime` varchar(30) DEFAULT NULL,
  `Uloga` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` ( `Ime`, `Prezime`, `Uloga`) VALUES
('Ana', 'Kaplarević-Mališić', 'Profesor'),
( 'Vladimir', 'Cvjetković', 'Profesor'),
('Bojana', 'Borovićanin', 'Profesor'),
('Aleksa', 'Mrkšić', 'Student'),
('Andrija', 'Eremić', 'Student'),
('Aleksandar', 'Milutinović', 'Student'),
('Darko', 'Đurđević', 'Student');

-- --------------------------------------------------------

--
-- Table structure for table `predmeti`
--

DROP TABLE IF EXISTS `predmeti`;
CREATE TABLE IF NOT EXISTS `predmeti` (
  `idPredmeta` int(11) NOT NULL AUTO_INCREMENT,
  `nazivPredmeta` varchar(30) DEFAULT NULL,
  `nastavnik` int(11) NOT NULL,
  PRIMARY KEY (`idPredmeta`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `predmeti`
--

INSERT INTO `predmeti` ( `nazivPredmeta`, `nastavnik`) VALUES
( 'Objektno-orjentisano prog.', 1),
( 'Klijentske web tehnologije', 2),
( 'Matematika', 3);

-- --------------------------------------------------------

--
-- Table structure for table `slusanje`
--

DROP TABLE IF EXISTS `slusanje`;
CREATE TABLE IF NOT EXISTS `slusanje` (
  `idStudenta` int(11) NOT NULL,
  `idPredmeta` int(11) NOT NULL,
  `nazivPredmeta` varchar(30) NOT NULL,
  `nastavnik` int(11) NOT NULL,
  PRIMARY KEY (`idPredmeta`,`idStudenta`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `slusanje`
--

INSERT INTO `slusanje` (`idStudenta`,`idPredmeta`, `nazivPredmeta`, `nastavnik`) VALUES
(1,3, 'Matematika', 3),
(2,3, 'Matematika', 3),
(3,3, 'Matematika', 3);

-- --------------------------------------------------------

--
-- Table structure for table `prijave`
--

DROP TABLE IF EXISTS `prijave`;
CREATE TABLE IF NOT EXISTS `prijave` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `idStudenta` int(11) NOT NULL,
  `idPredmeta` int(11) NOT NULL,
  `nazivPredmeta` varchar(30) NOT NULL,
  `nastavnik` int(11) NOT NULL,
  `ocena` int(1),
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prijave`
--

INSERT INTO `prijave` (`idStudenta`,`idPredmeta`, `nazivPredmeta`, `nastavnik`,`ocena`) VALUES
(1,3, 'Matematika', 3,0 ),
(2,3, 'Matematika', 3,0 ),
(3,3, 'Matematika', 3,0 );

COMMIT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
