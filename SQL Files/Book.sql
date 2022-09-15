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
-- Table structure for table `Book`
--

CREATE TABLE `Book` (
  `Barcode` varchar(5) NOT NULL,
  `Title` text NOT NULL,
  `Discipline` text NOT NULL,
  `Author1` text NOT NULL,
  `Author2` text NOT NULL,
  `Author3` text NOT NULL,
  `Author4` text NOT NULL,
  `Publisher` text NOT NULL,
  `YearOfPublication` int(11) NOT NULL,
  `ISBN` text NOT NULL,
  `BookCondition` enum('Good','Damaged') NOT NULL,
  `SuggestedPrice` text NOT NULL,
  `Notes` text NOT NULL,
  `Status` enum('Active','Inactive') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Book`
--

INSERT INTO `Book` (`Barcode`, `Title`, `Discipline`, `Author1`, `Author2`, `Author3`, `Author4`, `Publisher`, `YearOfPublication`, `ISBN`, `BookCondition`, `SuggestedPrice`, `Notes`, `Status`) VALUES
('20123', 'A Tale of two cities', 'English', 'Charles Dickens', '', '', '', 'English Press', 1859, '983212342', 'Damaged', '10.00', 'An amazing book', 'Inactive'),
('20201', 'The Great Gatsby', 'English', 'F. Scott Fitzgerald', '', '', '', 'Charles Scribner and Sons', 1925, '123456789', 'Good', '23.22', 'Leonardo DiCaprio was great in the movie', 'Active'),
('40122', '1234', 'Computer Science', '1234', '', '', '', '1234', 1234, '', 'Good', '', '', 'Active'),
('40123', 'temp', 'Computer Science', 'asdf', '', '', '', '', 1920, '324444444', 'Good', '', '', 'Active'),
('40896', 'A Book', 'Computer Science', 'Ethan Baker', '', '', '', 'Brockport', 2022, '842684257', 'Good', '50.00', 'Terrible', 'Active'),
('50001', 'Life of my ancestor John Adams', 'Test', 'Kyle Adams', '', '', '', 'Acme', 2021, '456789098', 'Good', '52.01', 'Only 2 copies left', 'Inactive'),
('50019', 'asdf', 'History', 'asdf', 'asdf', 'asdf', '', '', 2233, 'asdf', 'Good', '', '', 'Active'),
('60001', 'Guitar for absolute Dummies', 'Test', 'Matt Morgan', 'Edward Morgan', '', '', 'George Allen & Unwin', 2020, '45689031', 'Good', '5.00', 'Book is great', 'Active'),
('60020', 'What makes Matt smart', 'Music', 'Adita Kulkarni', 'Melissa Coleman', '', '', 'SUNY Brockport', 2021, '456789034', 'Good', '150', '', 'Active'),
('60082', 'Book of books', 'Music', 'Mr author', '', '', '', '', 2023, '', 'Good', '', '', 'Active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Book`
--
ALTER TABLE `Book`
  ADD PRIMARY KEY (`Barcode`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
