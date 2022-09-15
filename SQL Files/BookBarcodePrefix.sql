-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 15, 2022 at 03:58 PM
-- Server version: 10.3.36-MariaDB
-- PHP Version: 8.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spr22_csc429_mmorg9`
--

-- --------------------------------------------------------

--
-- Table structure for table `BookBarcodePrefix`
--

CREATE TABLE `BookBarcodePrefix` (
  `PrefixValue` varchar(2) NOT NULL,
  `Discipline` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `BookBarcodePrefix`
--

INSERT INTO `BookBarcodePrefix` (`PrefixValue`, `Discipline`) VALUES
('40', 'Computer Science'),
('70', 'Dance'),
('20', 'English'),
('50', 'History'),
('30', 'Math'),
('60', 'Music');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `BookBarcodePrefix`
--
ALTER TABLE `BookBarcodePrefix`
  ADD PRIMARY KEY (`PrefixValue`),
  ADD UNIQUE KEY `Discipline` (`Discipline`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
