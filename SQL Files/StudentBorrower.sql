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
-- Table structure for table `StudentBorrower`
--

CREATE TABLE `StudentBorrower` (
  `BannerId` varchar(9) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Phone` varchar(12) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `BorrowerStatus` enum('Good Standing','Delinquent') NOT NULL,
  `DateOfLatestBorrowerStatus` varchar(10) NOT NULL,
  `DateOfRegistration` varchar(10) NOT NULL,
  `Notes` varchar(300) NOT NULL,
  `Status` enum('Active','Inactive') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `StudentBorrower`
--

INSERT INTO `StudentBorrower` (`BannerId`, `FirstName`, `LastName`, `Phone`, `Email`, `BorrowerStatus`, `DateOfLatestBorrowerStatus`, `DateOfRegistration`, `Notes`, `Status`) VALUES
('111111112', 'PhoneMan', 'EmailSir', '1234567890', 'afegrshdtjj', 'Good Standing', '2022-04-28', '2022-04-08', 'Error Works', 'Inactive'),
('12341234', 'asdf', 'asdf', '', '', 'Good Standing', '2022-05-09', '2022-05-09', '', 'Active'),
('1235', '1', '1', '1', '1', 'Delinquent', '2022-04-28', '2022-03-27', '1', 'Inactive'),
('12356', 'asdf', 'asdf', '', '', 'Good Standing', '2022-05-09', '2022-05-09', '', 'Active'),
('800111999', 'Matthew', 'Morgan', '585-395-2234', 'mmorg9@brockport.edu', 'Good Standing', '2022-04-26', '2022-04-06', 'What an amazing student he is!', 'Active'),
('800123456', 'Jeremiah', 'Biden', '2025551212', 'njbiden@wh.gov', 'Delinquent', '2022-04-28', '2022-04-07', 'good rich person', 'Inactive'),
('800324743', 'a', 's', '', '', 'Delinquent', '2022-04-26', '2022-04-05', '', 'Active'),
('800342432', 'Rob', 'Cot', '3159382345', 'rcot@gmail.com', 'Good Standing', '2022-03-25', '2022-03-25', 'Notes', 'Active'),
('800555777', 'Melissa', 'Coleman', '5853952146', 'mcoleman@brockport.edu', 'Delinquent', '2022-05-10', '2022-05-10', 'study hard!', 'Active'),
('800734985', 'Zachary', 'Verdaasdonk', '7358091234', 'zverd1@brockport.edu', 'Good Standing', '2022-03-31', '2022-03-31', 'Noter', 'Inactive'),
('80088432', 'Keith', 'Boro', '', '', 'Delinquent', '2022-04-07', '2022-04-07', '', 'Active'),
('800985985', 'a', 'tesadf', '', '', 'Good Standing', '2022-03-28', '2022-03-28', '', 'Inactive');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `StudentBorrower`
--
ALTER TABLE `StudentBorrower`
  ADD PRIMARY KEY (`BannerId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
