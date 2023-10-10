
--ограничить возраст
alter  table student add constraint constraint_age check(age >16);

--уникальное имя
alter table student add constraint unique_name unique(name);


--имя не равно null
alter table student alter column name set not null;

--пара "значение названия " - "цвет факультета " должны быть уникальными(name и color в комбинации уникальны)
alter table faculty add constraint unique_name unique(name,color);

--при создании студента без возраста ,автоматически присваивать 20 лет
alter table student alter column age set default 20;
