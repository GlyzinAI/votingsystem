DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;
ALTER SEQUENCE GLOBAL_SEQ
RESTART WITH 100;

INSERT INTO users (name, email, password) VALUES
  ('User1', 'user@gmail.com', 'password'),
  ('User2', 'user2@gmail.com', 'password'),
  ('Admin1', 'admin@gmail.com', 'admin'),
  ('Admin2', 'admin2@gmail.com', 'admin');


INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100),
  ('ROLE_USER', 101),
  ('ROLE_ADMIN', 102),
  ('ROLE_USER', 102),
  ('ROLE_ADMIN', 103);


INSERT INTO restaurants (name) VALUES
  ('Сказка Востока'),
  ('La Cucaracha'),
  ('Жемчужина'),
  ('Taleon'),
  ('Хочу харчо');

-- Сказка Востока 104
-- La Cucaracha 105
-- Жемчужина 106
-- Taleon 107
-- Хочу харчо 108

INSERT INTO dishes (name, price, restaurant_id, date) VALUES
  ('Стейк из баранины', 590, 104, '2019-09-19'),
  ('Чахохбили', 360, 104, '2019-09-19'),
  ('Плов', 470, 104, '2019-09-19'),
  ('Хинкали', 200, 105, '2019-09-19'),
  ('Жареный картофель с грибами', 240, 105, '2019-09-19'),
  ('Форель', 550, 106, '2019-09-19'),
  ('Манты', 390, 106, '2019-09-19'),
  ('Рагу из индейки', 290, 107, '2019-09-19'),
  ('Котлеты пожарские', 330, 107, '2019-09-19'),
  ('Медальоны из говядины', 490, 107, '2019-09-19'),
  ('Харчо', 350, 108, '2019-09-19'),
  ('Паста(Карбонара)', 420, 108, '2019-09-19');


INSERT INTO votes (user_id, restaurant_id, date) VALUES
  (100, 105, '2019-09-17'),
  (101, 104, '2019-09-18'),
  (102, 108, '2019-09-19'),
  (103, 108, '2019-09-19');
