USE DBank;

DROP TABLE IF EXISTS user_product;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS product;

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    ssn CHAR(13) NOT NULL UNIQUE
);

INSERT INTO user ( login_id, password, name,phone,address,ssn) VALUES
    ('Hong01', 'pw1234!', '홍길동', '010-1234-5678', '서울시 강남구 테헤란로 1', '9506211234567'),
    ('Kim02', 'pass5678!', '김철수', '010-5678-1234', '부산시 해운대구 우동 2', '0103052345678'),
    ('Lee03', 'pw91011@', '이수지', '010-1212-3434', '대전광역시 중구 대종로480번길 15', '9812253456789');

SELECT * FROM user;

CREATE TABLE account(
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    account_number CHAR(13) NOT NULL UNIQUE ,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

INSERT INTO account (user_id, balance,account_number) VALUES
    (1, 120000.00, '1101234567890'),
    (2, 500000.00, '3333012345678');

SELECT * FROM account;

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    type ENUM('정기예금', '자유적금', '개인연금', '청약저축') NOT NULL,
    duration_month INT NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL
);

INSERT INTO product (product_id, name, type, duration_month, interest_rate) VALUES
    (1, '정기예금 플랜', '정기예금', 12, 3.25),
    (2, '자유적금 365', '자유적금', 24, 3.50),
    (3, '개인연금 저축', '개인연금', 120, 4.10),
    (4, '청약저축 기본형', '청약저축', 60, 2.30),
    (5, '정기예금 스마트', '정기예금', 6, 2.95),
    (6, '자유적금 플러스', '자유적금', 36, 3.70),
    (7, '연금 모아', '개인연금', 180, 4.25),
    (8, '청약저축 특판', '청약저축', 36, 2.60),
    (9, '정기예금 1년', '정기예금', 12, 3.10),
    (10, '자유적금 우대', '자유적금', 18, 3.40);

CREATE TABLE user_product (
    user_product_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('가입', '해지') NOT NULL DEFAULT '가입',
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

INSERT INTO user_product ( user_id, product_id, start_date, end_date, status)
VALUES
    (1, 1, '2024-05-01', '2025-01-01', '가입'),
    (1, 2, '2024-06-01', '2025-02-01', '가입'),
    (1, 3, '2024-07-01', '2025-03-01', '가입'),
    (1, 4, '2024-08-01', '2025-04-01', '가입'),
    (2, 1, '2024-05-01', '2025-05-01', '가입'),
    (2, 2, '2024-01-01', '2024-12-31', '가입'),
    (3, 1, '2024-05-01', '2025-05-01', '가입');

SELECT * FROM account;
SELECT * FROM product;
SELECT * FROM user_product;
