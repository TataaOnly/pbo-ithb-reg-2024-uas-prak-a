-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2025 at 08:58 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_uas1123027`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `password`, `name`, `address`, `phone`) VALUES
(1, 'hash123', 'John Doe', 'Jl. Sudirman No. 123, Jakarta', '08123'),
(2, 'hash456', 'Jane Smith', 'Jl. Thamrin No. 456, Jakarta', '08456'),
(3, 'hash789', 'Bob Johnson', 'Jl. Gatot Subroto No. 789, Jakarta', '08789');

-- --------------------------------------------------------

--
-- Table structure for table `shipment_details`
--

CREATE TABLE `shipment_details` (
  `id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `status` enum('pending','transit','delivered') NOT NULL,
  `current_position` varchar(100) NOT NULL,
  `evidence` varchar(255) DEFAULT NULL,
  `date` date NOT NULL,
  `updated_by` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shipment_details`
--

INSERT INTO `shipment_details` (`id`, `transaction_id`, `status`, `current_position`, `evidence`, `date`, `updated_by`) VALUES
(1, 1, 'transit', 'Jakarta Sorting Center', 'https://example.com/evidence1.jpg', '2025-01-08', 'Admin1'),
(2, 2, 'pending', 'Drop Point Thamrin', 'https://example.com/evidence2.jpg', '2025-01-09', 'Admin2'),
(3, 3, 'delivered', 'Bandung Hub', 'https://example.com/evidence3.jpg', '2025-01-09', 'Admin3'),
(4, 4, 'transit', 'Semarang Hub', 'https://example.com/evidence4.jpg', '2025-01-09', 'Admin1');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `package_type` varchar(50) NOT NULL,
  `package_weight` double NOT NULL,
  `total_cost` int(11) NOT NULL,
  `created_at` date NOT NULL,
  `receipt_name` varchar(100) NOT NULL,
  `receipt_address` text NOT NULL,
  `receipt_phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `customer_id`, `package_type`, `package_weight`, `total_cost`, `created_at`, `receipt_name`, `receipt_address`, `receipt_phone`) VALUES
(1, 1, 'Regular', 2.5, 50000, '2025-01-08', 'Alice Brown', 'Jl. Malioboro No. 101, Yogyakarta', '08234'),
(2, 2, 'Express', 1.8, 75000, '2025-01-09', 'Charlie Wilson', 'Jl. Pemuda No. 202, Surabaya', '08567'),
(3, 3, 'Regular', 3.2, 65000, '2025-01-09', 'David Lee', 'Jl. Ahmad Yani No. 303, Bandung', '08890'),
(4, 1, 'Express', 0.5, 45000, '2025-01-09', 'Eva Garcia', 'Jl. Diponegoro No. 404, Semarang', '08345');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `shipment_details`
--
ALTER TABLE `shipment_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_transaction_id` (`transaction_id`),
  ADD KEY `idx_shipment_status` (`status`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_customer_id` (`customer_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `shipment_details`
--
ALTER TABLE `shipment_details`
  ADD CONSTRAINT `shipment_details_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
