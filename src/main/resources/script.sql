--возраст между 20 и 35
select *
from student s
where s.age  between 20 and 35;

--вызвать всех по имени
select s."name"
from student s ;

-- в имени присудствует данный символ
select *
from student s
where s."name"  like '%d%';

--все у кого возраст меньше id
select *
from student s
where s.age < s.id ;

--по возростанию возраст
select *
from student s
order by s.age desc  ;
--заполнение табицы
INSERT INTO public.faculty  (id, name,color) VALUES (4, 'FFFF','bleack');
   --распределение по возрастанию(DESC по убыванию)
select * from expenses e
 ORDER BY id asc

 --подсчет строк в таблице
select  COUNT(*) FROM expenses

--подсчет строк в таблице без NULL ,categoryе название столбца где null
select COUNT(category) FROM expenses

--мин,макс,сред значения из столбца amount из таблицы expenses
select  MIN(amount), MAX(amount), AVG(amount) from expenses

--для посчета общей суммы из столбца amount из таблицы expenses
select  SUM(amount) FROM expenses

--последние 5 строк
 SELECT * FROM student s  ORDER BY id DESC LIMIT 5