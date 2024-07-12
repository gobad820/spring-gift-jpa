CREATE TABLE IF NOT EXISTS product (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
price INTEGER NOT NULL,
imageUrl VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS members (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS wishes (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
member_id BIGINT NOT NULL,
product_name VARCHAR(255) NOT NULL,
FOREIGN KEY (member_id) REFERENCES members(id)
);