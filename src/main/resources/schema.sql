DROP SCHEMA public CASCADE;


create sequence seq1 start with 1;

create table workout(
id bigint not null primary key,
workouttype varchar(255),
date TIMESTAMP);
