drop TABLE IF EXISTS employee_delete;
create memory temporary table employee_delete
  (first varchar(15),
  last varchar(20),
  age number(3),
  address varchar(30),
  city varchar(20),
  state varchar(20));

insert into employee_delete (first, last, age, address, city, state)
values ('Luke', 'Duke', 45, '2130 Boars Nest', 'Hazard Co', 'Georgia');

insert into employee_delete (first, last, age, address, city, state)
values ('John', 'Smith', 40, '2130 Boars Nest', 'London', 'Alabama');

insert into employee_delete (first, last, age, address, city, state)
values ('Luke', 'Smith', 35, '2130 Boars Nest', 'Hazard Co', 'Georgia');

insert into employee_delete (first, last, age, address, city, state)
values ('Arnold', 'Single', 22, '1234 Home', 'Hazard Co', 'Alabama');
