DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;
ALTER SEQUENCE GLOBAL_SEQ
RESTART WITH 100;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@gmail.com', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin');


INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100),
  ('ROLE_ADMIN', 101),
  ('ROLE_USER', 101);


INSERT INTO restaurants (name) VALUES
  ('Сказка Востока'),
  ('La Cucaracha'),
  ('Жемчужина'),
  ('Taleon'),
  ('Хочу харчо');

-- Сказка Востока 102
-- La Cucaracha 103
-- Жемчужина 104
-- Taleon 105
-- Хочу харчо 106

INSERT INTO dishes (name, price, restaurant_id, date) VALUES
  ('Стейк из баранины', 590, 102, '2019-09-25'),
  ('Чахохбили', 360, 102, '2019-09-25'),
  ('Плов', 470, 102, '2019-09-25'),
  ('Хинкали', 200, 103, '2019-09-25'),
  ('Жареный картофель с грибами', 240, 103, '2019-09-25'),
  ('Форель', 550, 104, '2019-09-25'),
  ('Манты', 390, 104, '2019-09-25'),
  ('Рагу из индейки', 290, 105, '2019-09-25'),
  ('Котлеты пожарские', 330, 105, '2019-09-25'),
  ('Медальоны из говядины', 490, 105, '2019-09-25'),
  ('Харчо', 350, 106, '2019-09-25'),
  ('Паста(Карбонара)', 420, 106, '2019-09-25');


INSERT INTO votes (user_id, restaurant_id, date) VALUES
  (100, 102, '2019-09-24'),
  (100, 105, '2019-09-25'),
  (101, 104, '2019-09-25');
