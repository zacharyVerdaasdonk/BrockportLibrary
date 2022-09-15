-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 15, 2022 at 03:59 PM
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
-- Table structure for table `Rental`
--

CREATE TABLE `Rental` (
  `Id` int(11) NOT NULL,
  `BorrowerId` varchar(9) NOT NULL,
  `BookId` varchar(8) NOT NULL,
  `CheckoutDate` varchar(10) NOT NULL DEFAULT '',
  `CheckoutWorkerId` varchar(9) NOT NULL,
  `DueDate` varchar(10) NOT NULL DEFAULT '',
  `CheckinDate` varchar(10) NOT NULL DEFAULT '',
  `CheckinWorkerId` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Rental`
--

INSERT INTO `Rental` (`Id`, `BorrowerId`, `BookId`, `CheckoutDate`, `CheckoutWorkerId`, `DueDate`, `CheckinDate`, `CheckinWorkerId`) VALUES
(1, '800111999', '50001', '2022-04-21', '1', '2022-04-28', '2022-04-21', '1'),
(2, '800111999', '60001', '2022-04-21', '1', '2022-05-05', '2022-04-21', '1'),
(3, '80088432', '20123', '2022-04-21', '1', '2022-05-18', '2022-04-21', '1'),
(4, '111111112', '60082', '2022-04-21', '1', '2022-04-22', '2022-04-21', '1'),
(5, '111111112', '50019', '2022-04-21', '1', '2022-04-18', '2022-04-21', '1'),
(6, '800342432', '60001', '2022-04-21', '1', '2022-04-21', '2022-04-21', '1'),
(7, '111111112', '50001', '2022-04-21', '1', '2022-04-28', '2022-04-26', '1'),
(8, '800324743', '60001', '2022-04-25', '1', '2022-04-12', '2022-04-27', '1'),
(9, '800111999', '20123', '2022-04-26', '1', '2022-05-06', '2022-04-26', '1'),
(10, '800111999', '20123', '2022-04-26', '1', '2022-04-04', '2022-04-27', '1'),
(11, '111111112', '60001', '2022-04-28', '1', '2022-05-06', '2022-04-28', '1'),
(12, '111111112', '20123', '2022-04-28', '1', '2022-04-30', '2022-04-28', '1'),
(13, '111111112', '20123', '2022-04-28', '1', '2022-04-12', '2022-04-28', '1'),
(14, '111111112', '20123', '2022-05-10', '1', '2022-05-10', '2022-05-10', '1'),
(15, '111111112', '40123', '2022-05-10', '1', '2022-05-19', '2022-05-10', '1'),
(16, '800555777', '20201', '2022-05-10', '1', '2022-05-24', '2022-05-10', '1'),
(17, '800555777', '20201', '2022-05-10', '1', '2022-05-09', '2022-05-10', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Rental`
--
ALTER TABLE `Rental`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Rental`
--
ALTER TABLE `Rental`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
