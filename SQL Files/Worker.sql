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
-- Table structure for table `Worker`
--

CREATE TABLE `Worker` (
  `BannerId` varchar(9) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Phone` varchar(12) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Credentials` enum('Ordinary','Administrator') NOT NULL,
  `DateOfLatestCredentialsStatus` varchar(10) NOT NULL,
  `DateOfHire` varchar(10) NOT NULL,
  `Status` enum('Active','Inactive') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Worker`
--

INSERT INTO `Worker` (`BannerId`, `Password`, `FirstName`, `LastName`, `Phone`, `Email`, `Credentials`, `DateOfLatestCredentialsStatus`, `DateOfHire`, `Status`) VALUES
('1', '1', 'Matteo', 'Morgan', '5852034908', 'mattmorgan113@gmail.com', 'Administrator', '2022-04-28', '2022-03-23', 'Active'),
('123213', '1123', 'john', 'adams', '5859582345', 'jadams@gmail.com', 'Administrator', '2022-03-29', '2022-03-29', 'Inactive'),
('1232133', 'sadf', '123', '123', '', '', 'Ordinary', '2022-05-09', '2022-05-09', 'Active'),
('12354', 'asdf', 'asdf', 'fdas', '', '', 'Ordinary', '2022-05-09', '2022-05-09', 'Active'),
('2', 'pass', 'a', 'b', '', '', 'Ordinary', '2022-04-06', '2022-04-06', 'Inactive'),
('800444888', 'brockport', 'Heidi S', 'Macpherson', '5853955842', 'president@brockport.edu', 'Administrator', '2022-04-07', '2022-04-07', 'Inactive'),
('800832123', 'Password', 'Michael', 'Kraut', '', '', 'Administrator', '2022-04-07', '2022-04-07', 'Inactive'),
('800999111', '1234', 'T M', 'Rao', '5853955176', 'trao@brockport.edu', 'Administrator', '2022-05-10', '2022-05-10', 'Active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Worker`
--
ALTER TABLE `Worker`
  ADD PRIMARY KEY (`BannerId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
