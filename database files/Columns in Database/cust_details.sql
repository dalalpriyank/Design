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
-- Table structure for table `cust_details`
--

CREATE TABLE `cust_details` (
  `id` int(11) NOT NULL,
  `cust_name` varchar(20) NOT NULL,
  `college_id` varchar(20) NOT NULL,
  `cust_password` varchar(20) NOT NULL,
  `cust_email` varchar(20) NOT NULL,
  `cust_phoneno` varchar(20) NOT NULL,
  `cust_institute` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cust_details`
--

INSERT INTO `cust_details` (`id`, `cust_name`, `college_id`, `cust_password`, `cust_email`, `cust_phoneno`, `cust_institute`) VALUES
(14, 'vatsal', '14bca027', '1234', 'vatsal@gmail.com', '1234567890', 'cmpica'),
(15, 'Krisha shah', '14bca073', 'krishaas', 'krishashah@gmail.com', '8905260804', 'cmpica'),
(16, 'Shivam Solanki', '14bca075', 'shivam@77', 'shivsol.52@gmail.com', '9727553772', 'CMPICA'),
(17, 'Priyank Dalal', '14bca010', 'dalal2110', 'priyankd@live.com', '9033893562', 'cmpica'),
(18, 'muskan', '15mca026', 'minaz1810', 'minazvahora1824@gmai', '9033443484', 'cmpica'),
(19, 'sunera', '14bca014', 'sunera', 'sunera@gmail.com', '8080808082', 'cmpica'),
(20, 'Dhrumil Shah', '14bca071', 'loko', 'dhrumilshah8197@gmai', '7575032266', 'CMPICA'),
(21, 'dip Patel', '14bca038', 'abcd', 'pateldip@hotmail.com', '9033928996', 'cmpica'),
(22, 'patelumang5697', '14bca063', '14bc@063', 'umangpatel1434@gmail', '8980850315', 'cmpica'),
(23, 'sell', 'ice', 'chal', 'hshs@hs.com', 'gshhs', '9033863562'),
(24, 'chall', 'bhs', 'ghs', 'vhsh', 'ghs', '1234564890'),
(25, 'gshghs', 'gsg', 'gge', 'ggs', 'vgd', '964'),
(26, 'abcdefg', '14bca001', '1234', 'abc@gamil.com', '1234567890', 'cmpica'),
(27, 'Shaileja', '28shail10', '1234', 'shal@gmail.com', '1234567890', 'stt');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cust_details`
--
ALTER TABLE `cust_details`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cust_details`
--
ALTER TABLE `cust_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
