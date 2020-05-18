CREATE DATABASE periodicals;

CREATE TABLE users
(
    user_id       BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(100)        NOT NULL,
    last_name     VARCHAR(100)        NOT NULL,
    role          VARCHAR(20)         NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(100)        NOT NULL
);


CREATE TABLE periodicals
(
    periodical_id       BIGSERIAL PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    description         text,
    monthly_price_cents INTEGER      NOT NULL
);



-- data
INSERT INTO users(user_id, first_name, last_name, role, email, password_hash)
VALUES (DEFAULT, 'Jack', 'Nicholson', 'admin', 'jack.nich@gmai.com', '1'),
       (DEFAULT, 'Marlon', 'Brando', 'user', 'marl.brand@gmai.com', '2'),
       (DEFAULT, 'Robert', 'DeNiro', 'user', 'rob.niro@gmai.com', '3'),
       (DEFAULT, 'Dustin', 'Hoffman', 'user', 'dust.hoff@gmai.com', '4'),
       (DEFAULT, 'Al', 'Pacino', 'admin', 'al.pach@gmai.com', '5');

INSERT INTO periodicals(name, description, monthly_price_cents)
VALUES ('Game Informer', '', '999'),
       ('Better Homes and Gardens', '', '999'),
       ('Reader''s Digest', '', '999'),
       ('Good Housekeeping', '', '999'),
       ('Family Circle', '', '999'),
       ('National Geographic', '', '999'),
       ('People Magazine', '', '999'),
       ('Time Magazine', '', '999'),
       ('Sports Illustrated', '', '999'),
       ('Cosmopolitan', '', '999'),
       ('Maxim', '', '999'),
       ('Men''s Health', '', '999'),
       ('Women''s Health', '', '999'),
       ('Newsweek', '', '999'),
       ('Rolling Stone', '', '999'),
       ('Popular Science', '', '999'),
       ('Vogue', '', '999'),
       ('Playboy', '', '999'),
       ('Popular Mechanics', '', '999'),
       ('Forbes Magazine', '', '999'),
       ('Fortune', '', '999'),
       ('The Economist', '', '999'),
       ('Wired', '', '999');


