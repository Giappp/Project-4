CREATE DATABASE IF NOT EXISTS shop_db;
USE shop_db;

-- Users table
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(15),
    address TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE Products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ProductAttributes table
CREATE TABLE ProductAttributes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    color VARCHAR(50),
    stock INT,
    size INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- Images table
CREATE TABLE Images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255),
    product_attributes_id INT,
    FOREIGN KEY (product_attributes_id) REFERENCES ProductAttributes(id)
);

-- Orders table
CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Pending', 'Processing', 'Shipped', 'Delivered', 'Cancelled'),
    total_amount DECIMAL(10, 2),
    shipping_address TEXT,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- OrderItems table
CREATE TABLE OrderItems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- Categories table
CREATE TABLE Categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100),
    genders enum('NAM','NỮ','UNISEX'),
    productType VARCHAR(100),
    description TEXT
);

-- ProductCategories table (many-to-many relationship)
CREATE TABLE ProductCategories (
    product_id INT,
    category_id INT,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (category_id) REFERENCES Categories(id)
);

-- Admin table
CREATE TABLE Admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(15),
    address TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Promotion table
CREATE TABLE Promotion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100),
    start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    end_date DATETIME DEFAULT NULL
);

-- PromotionProduct table (many-to-many relationship)
CREATE TABLE PromotionProduct (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    promotion_id INT,
    percentage INT,
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (promotion_id) REFERENCES Promotion(id)
);