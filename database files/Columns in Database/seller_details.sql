-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 03, 2017 at 05:59 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `design`
--

-- --------------------------------------------------------

--
-- Table structure for table `seller_details`
--

CREATE TABLE `seller_details` (
  `id` int(4) NOT NULL,
  `seller_id` varchar(5) NOT NULL,
  `restaurant_name` varchar(30) NOT NULL,
  `owner_name` varchar(40) NOT NULL,
  `owner_email` varchar(40) NOT NULL,
  `password` varchar(20) NOT NULL,
  `seller_phoneno` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `seller_details`
--

INSERT INTO `seller_details` (`id`, `seller_id`, `restaurant_name`, `owner_name`, `owner_email`, `password`, `seller_phoneno`) VALUES
(5, 'icebe', 'Ice berg', 'Vatsal Vohra', 'vatsu@gmail.com', '1234', 1235467890),
(6, 'danny', 'dannys', 'paggu', 'chall@chall.com', '12345', 1234567890),
(8, 'kchat', 'krishna chat', 'krishna', 'krishna@gmail.com', '1234', 1234567890),
(9, '12345', '123456789', 'abcdefg', 'dalla@hhhd.com', '12345', 2147483647);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `seller_details`
--
ALTER TABLE `seller_details`
  ADD PRIMARY KEY (`id`,`seller_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `seller_details`
--
ALTER TABLE `seller_details`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
