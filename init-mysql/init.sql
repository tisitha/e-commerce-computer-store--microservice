CREATE DATABASE IF NOT EXISTS productdb;
CREATE DATABASE IF NOT EXISTS inventorydb;
CREATE DATABASE IF NOT EXISTS orderdb;
CREATE DATABASE IF NOT EXISTS userdb;

CREATE USER IF NOT EXISTS 'ecommerce_user'@'%' IDENTIFIED BY 'ecommerce_pass';

GRANT ALL PRIVILEGES ON productdb.* TO 'ecommerce_user'@'%';
GRANT ALL PRIVILEGES ON inventorydb.* TO 'ecommerce_user'@'%';
GRANT ALL PRIVILEGES ON orderdb.* TO 'ecommerce_user'@'%';
GRANT ALL PRIVILEGES ON userdb.* TO 'ecommerce_user'@'%';

FLUSH PRIVILEGES;