
-- insert into class (id, name, begin_date, speciality_id) values
-- (1, 'group1 math', '2019-09-01', 1),
-- (2, 'group2 math', '2020-04-01', 1),
-- (3, 'group3 physics', '2019-09-01', 2),
-- (4, 'group4 physics', '2020-04-01', 2),
-- (5, 'group5 culture', '2019-09-01', 3),
-- (6, 'group6 culture', '2020-04-01', 3);

-- insert into class_discipline (class_id, discipline_id, teacher_id, begin_date, end_date) values
-- (2, 2, 4, '2020-09-01', '2020-12-01'),
-- (3, 3, 4, '2019-09-01', '2019-12-01'),
-- (4, 6, 2, '2020-09-01', '2020-12-01'),
-- (7, 7, 1, '2020-09-01', '2020-12-011'),
-- (6, 1, 4, '2020-09-01', '2020-12-01'),
-- (7, 6, 3, '2020-09-01', '2020-12-01'),
-- (1, 7, 2, '2019-09-01', '2019-12-01');

-- insert into student values
-- (1, 'Олег', 'Иванов', '1986-12-11', 'male', null, 1),
-- (2, 'Дарья', 'Тяпкина', '1980-12-11', 'famale', null, 2),
-- (3, 'Вячеслав', 'Шумаков', '1981-12-11', 'male', null, 3),
-- (4, 'Юрий', 'Пригодин', '1989-12-11', 'male', null, 4),
-- (5, 'Алеся', 'Юхневич', '1982-12-11', 'famale', null, 5),
-- (6, 'Федор', 'Сумкин', '1986-12-11', 'male', null, 6),
-- (7, 'Ирина', 'Зайцева', '1985-12-11', 'famale', null, 1),
-- (8, 'Леонид', 'Жук', '1988-12-11', 'male', null, 1),
-- (9, 'Мила', 'Руткевич', '1987-12-11', 'famale', null, 1),
-- (10, 'Алексей', 'Петренко', '1986-12-11', 'male', null, 2),
-- (11, 'Наталья', 'Тихановская', '1986-12-11', 'famale', null, 2),
-- (12, 'Михаил', 'Сотников', '1981-12-11', 'male', null, 3),
-- (13, 'Патрик', 'Патрусов', '1980-12-11', 'male', null, 3),
-- (14, 'Анна', 'Шемяка', '1987-12-11', 'famale', null, 6),
-- (15, 'Раиса', 'Сидорович', '1988-12-11', 'famale', null, 6),
-- (16, 'Богдан', 'Хмельницкий', '1989-12-11', 'male', null, 4),
-- (17, 'Барбара', 'Смит', '1989-12-11', 'famale', null, 5);

insert into student_result values
(2, 2, 2, '2019-09-01', 3),
(3, 3, 3, '2020-09-01', 4),
(4, 1, 6, '2020-09-01', 5),
(5, 1, 7, '2020-09-01', 2),
(6, 14, 1, '2020-09-01', 3),
(7, 10, 2, '2019-09-01', 4),
(8, 12, 3, '2020-09-01', 5),
(9, 7, 6, '2020-09-01', 2),
(10, 7, 7, '2020-09-01', 3),
(11, 15, 1, '2020-09-01', 4),
(12, 11, 2, '2019-09-01', 5),
(13, 13, 3, '2020-09-01', 2),
(14, 8, 6, '2020-09-01', 3),
(15, 8, 7, '2020-09-01', 4),
(16, 9, 6, '2020-09-01', 5),
(17, 4, 6, '2019-09-01', 2),
(18, 16, 6, '2020-09-01', 3),
(19, 9, 7, '2020-09-01', 4),
(20, 5, 7, '2020-09-01', 5),
(21, 17, 7, '2020-09-01', 2);