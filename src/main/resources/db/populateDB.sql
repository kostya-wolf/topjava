DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (TIMESTAMP '2020.01.30 10:00', 'Завтрак', 500, 100000),
       (TIMESTAMP '2020.01.30 12:00', 'Обед', 500, 100000),
       (TIMESTAMP '2020.01.30 20:00', 'Ужин', 500, 100000),
       (TIMESTAMP '2021.05.11 20:00', 'Ужин', 500, 100001),
       (TIMESTAMP '2021.05.11 22:00', 'Ням-ням', 500, 100001);