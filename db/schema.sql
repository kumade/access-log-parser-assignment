CREATE TABLE `log_entry` (
  `id` bigint(20) NOT NULL,
  `date` datetime DEFAULT NULL,
  `ip_address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `method` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `response_code` int(11) DEFAULT NULL,
  `user_agent` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `log_entry`
ADD PRIMARY KEY (`id`);

ALTER TABLE `log_entry`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


CREATE TABLE `blocked_ip_address` (
  `id` bigint(20) NOT NULL,
  `date` datetime DEFAULT NULL,
  `ip_address` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reason` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `blocked_ip_address`
ADD PRIMARY KEY (`id`);

ALTER TABLE `blocked_ip_address`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
