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
