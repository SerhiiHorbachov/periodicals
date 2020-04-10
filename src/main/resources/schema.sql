

CREATE TABLE users
(
user_id BIGSERIAL PRIMARY KEY,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
role VARCHAR(20) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password_hash VARCHAR(100) NOT NULL
);

CREATE TABLE categories
(
category_id BIGSERIAL PRIMARY KEY,
name  VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE images
(
image_id BIGSERIAL PRIMARY KEY,
url  VARCHAR(100)       NOT NULL
);

CREATE TABLE periodics
(
periodics_id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description text,
monthly_price_cents INTEGER NOT NULL,
category_id BIGINT references categories(category_id) on delete set null,
image_id BIGINT references images(image_id) on delete set null
);



-- data
INSERT INTO users(user_id, first_name, last_name, role, email, password_hash)
VALUES (DEFAULT, 'Jack', 'Nicholson', 'admin', 'jack.nich@gmai.com', '1'),
(DEFAULT, 'Marlon', 'Brando', 'reader', 'marl.brand@gmai.com', '2'),
(DEFAULT, 'Robert', 'DeNiro', 'reader', 'rob.niro@gmai.com', '3'),
(DEFAULT, 'Dustin', 'Hoffman', 'reader', 'dust.hoff@gmai.com', '4'),
(DEFAULT, 'Al', 'Pacino', 'admin', 'al.pach@gmai.com', '5');


