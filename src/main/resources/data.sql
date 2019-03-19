insert into workout values (next value for seq1,'user', 'pull',
'2019-02-23' );

insert into exercise values (next value for exseq, 'Bench', 3, 5, 100, 1);
insert into exercise values (next value for exseq, 'Overhead press', 3, 8, 40, 1);
insert into exercise values (next value for exseq, 'Incline dumbbell press', 3, 8, 26, 1);
insert into exercise values (next value for exseq, 'Triceps pushdown', 3, 12, 20, 1);

insert into workout values (next value for seq1,'user', 'push',
'2019-02-25' );

insert into exercise values (next value for exseq, 'Pull ups', 3, 8, 0, 2);
insert into exercise values (next value for exseq, 'Barbell row', 3, 8, 60, 2)
insert into exercise values (next value for exseq, 'Face pull', 3, 12, 15, 2);
insert into exercise values (next value for exseq, 'Barbell curl', 3, 10, 30, 2);


insert into workout values (next value for seq1,'user', 'legs',
CURRENT_DATE );

insert into exercise values (next value for exseq, 'Squat', 3, 8, 100, 3);
insert into exercise values (next value for exseq, 'Romanian deadlift', 3, 8, 60, 3)
insert into exercise values (next value for exseq, 'Calf raise', 3, 12, 100, 3);
insert into exercise values (next value for exseq, 'Leg curl', 3, 10, 40, 3);


insert into workout_Plan values (1, 'PPL');

insert into plan_Workout values (1, 'Pull', 1);
insert into plan_Exercise values (1, 'Bench', 3, 5, 1);
insert into plan_Exercise values (2, 'OHP', 3, 6, 1);
insert into plan_Exercise values (3, 'Triceps', 3, 8, 1);

insert into plan_Workout values (2, 'Push', 1);
insert into plan_Exercise values (4, 'Barbell row', 3, 5, 2);
insert into plan_Exercise values (5, 'Cable row', 3, 6, 2);
insert into plan_Exercise values (6, 'Biceps', 3, 8, 2);



