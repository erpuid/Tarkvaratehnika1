DROP SCHEMA public CASCADE;


create sequence seq1 start with 1;
create sequence seq2 start with 1;

create table workout(
id bigint not null primary key,
workouttype varchar(255),
date DATE);


create table exercise(
id bigint not null,
exercisename varchar(255),
sets int,
repetitions int,
weight int
);

create table users(
id bigint not null,
username varchar(255),
password varchar(255),
);