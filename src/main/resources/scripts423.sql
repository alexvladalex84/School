--left join показать  факультеты  занятые студентами
select s.name ,s.age,f.id ,
from student s left join faculty f on s.faculty_id = f.id

--right join выведет факультеты где есть и где ещё нет студентов
select s.name ,s.age,f.id
from student s right join faculty f on s.faculty_id = f.id

 --студенты у которых есть аватар right join
select*
from student s right join avatar a on s.id  = a.student_id

-- только студенты у которых есть аватар inner join (убрал всех у кого нет аватар)
select *
from avatar a  inner join student s  on  a.student_id = s.id;
----создание инлекс
--CREATE INDEX users_email_index ON users (email);
----удалиить индекс
--DROP INDEX users_email_index;
--Необходимо найти пользователя по его email.
--SELECT * FROM users WHERE email='someuser@mail.com'
--
--Необходимо найти книги одной из двух библиотек:
--SELECT * FROM books WHERE library_id = 5 OR library_id = 10
--
--Необходимо найти количество товаров, купленных в конкретный месяц:
--SELECT COUNT(id) FROM goods WHERE buy_date >= 01.09.2021 AND buy_date <= 31.09.2021
--
--поиск по первой строчке
--SELECT * FROM users WHERE name LIKE 'Алексей%'

--поиск по последней сточке
--SELECT * FROM users WHERE email LIKE '%@mail.ru'

--создание таблицы
--CREATE TABLE users (
--    name   TEXT
--    street TEXT,
--    house  INT
--);

