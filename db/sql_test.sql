/*(1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.*/

SELECT `ip_address` FROM `log_entry` WHERE `date` BETWEEN '2017-01-01 13:00:00' AND '2017-01-01 14:00:00' GROUP BY `ip_address` HAVING COUNT(`ip_address`) > 100;

/*(2) Write MySQL query to find requests made by a given IP.*/

SELECT * FROM `log_entry` WHERE `ip_address` = '192.168.11.231';

