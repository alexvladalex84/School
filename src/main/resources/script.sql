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
