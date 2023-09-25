--создать таблицу car
create table car
(
id serial primary key,
brand varchar(25),
model varchar(25),
price numeric(20,2)
)

--создать таблицу людей
create table users
(
id serial primary key,
name varchar (20)not null,
age serial not null,
hes_driver_s_license boolean default false,
--поле содержит  id таблицы car ( привязка один ко многим )
car_id serial references car (id)
)