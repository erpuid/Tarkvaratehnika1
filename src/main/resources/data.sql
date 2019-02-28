insert into workout values (next value for seq1, 'pull',
'2019-02-23' );

insert into exercise values (1, 'Bench', 3, 5, 100);
insert into exercise values (1, 'Overhead press', 3, 8, 40)
insert into exercise values (1, 'Incline dumbbell press', 3, 8, 26);
insert into exercise values (1, 'Triceps pushdown', 3, 12, 20);

insert into workout values (next value for seq1, 'push',
'2019-02-25' );

insert into exercise values (2, 'Pull ups', 3, 8, 0);
insert into exercise values (2, 'Barbell row', 3, 8, 60)
insert into exercise values (2, 'Face pull', 3, 12, 15);
insert into exercise values (2, 'Barbell curl', 3, 10, 30);


insert into workout values (next value for seq1, 'legs',
CURRENT_DATE );

insert into exercise values (3, 'Squat', 3, 8, 100);
insert into exercise values (3, 'Romanian deadlift', 3, 8, 60)
insert into exercise values (3, 'Calf raise', 3, 12, 100);
insert into exercise values (3, 'Leg curl', 3, 10, 40);




