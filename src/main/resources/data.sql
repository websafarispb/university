INSERT INTO groups ( group_name) VALUES ('a2a2');
INSERT INTO groups ( group_name) VALUES ('b2b2');
INSERT INTO groups ( group_name) VALUES ('c2c2');
INSERT INTO groups ( group_name) VALUES ('d2d2');
INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '123', 'Peter', 'Petrov', '2020-09-03', 'webPP@mail.ru', 'MALE', 'City17');
INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '124', 'Ivan', 'Petrov', '2020-09-04', 'webIP@mail.ru', 'MALE', 'City18');
INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '125', 'Peter', 'Ivanov', '2020-09-05', 'webPI@mail.ru', 'FEMALE', 'City19');
INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '126', 'Peter', 'Smirnov', '2020-09-06', 'webPS@mail.ru', 'MALE', 'City17');
INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '227', 'Irina', 'Stepanova', '2020-09-07', 'Stepanova@mail.ru', 'FEMALE', 'City11');
INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '123', 'Peter', 'Petrov', '2020-09-03', 'webPP@mail.ru', 'MALE', 'City17');
INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '124', 'Ivan', 'Petrov', '2020-09-04', 'webIP@mail.ru', 'MALE', 'City18');
INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '125', 'Peter', 'Ivanov', '2020-09-05', 'webPI@mail.ru', 'MALE', 'City19');
INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '126', 'Peter', 'Smirnov', '2020-09-06', 'webPS@mail.ru', 'MALE', 'City17');
INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '227', 'Irina', 'Stepanova', '2020-09-07', 'Ivanov@mail.ru', 'FEMALE', 'City11');
INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '527', 'Daria', 'Ivanova', '2020-09-07', 'Ivanova@mail.ru', 'FEMALE', 'City20');
INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( '528', 'Igor', 'Stepanov', '2020-09-07', 'Stepanov@mail.ru', 'MALE', 'City20');
INSERT INTO courses (course_name, course_description) values ('Mathematics', 'Math');
INSERT INTO courses (course_name, course_description) values ('Biology', 'Bio');
INSERT INTO courses (course_name, course_description) values ('Chemistry', 'Chem');
INSERT INTO courses (course_name, course_description) values ('Physics', 'Phy');
INSERT INTO classrooms (classroom_address, classroom_capacity) values ('101', '50');
INSERT INTO classrooms (classroom_address, classroom_capacity) values ('102', '40');
INSERT INTO classrooms (classroom_address, classroom_capacity) values ('103', '30');
INSERT INTO classrooms (classroom_address, classroom_capacity) values ('104', '20');
INSERT INTO students_groups (student_id, group_id) values (1, 1);
INSERT INTO students_groups (student_id, group_id) values (2, 2);
INSERT INTO students_groups (student_id, group_id) values (3, 3);
INSERT INTO students_groups (student_id, group_id) values (4, 3);
INSERT INTO students_groups (student_id, group_id) values (5, 4);
INSERT INTO dailyschedule (dailyschedule_date) values ('2020-09-07');
INSERT INTO dailyschedule (dailyschedule_date) values ('2020-09-08');
INSERT INTO dailyschedule (dailyschedule_date) values ('2020-09-09');
INSERT INTO dailyschedule (dailyschedule_date) values ('2020-09-11');
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (1, '09:00:00', 1, 1, 2, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (1, '09:00:00', 1, 3, 4, 1);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (1, '10:00:00', 2, 2, 2, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (1, '13:00:00', 2, 2, 3, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (1, '15:00:00', 4, 2, 3, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (2, '11:00:00', 3, 2, 2, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (2, '11:00:00', 2, 1, 3, 3);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (3, '13:00:00', 2, 2, 3, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (3, '15:00:00', 4, 2, 3, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (4, '13:00:00', 2, 2, 3, 2);
INSERT INTO lectures (dailyschedule_id, local_time, course_id, classroom_id, group_id, teacher_id) values (4, '15:00:00', 4, 2, 3, 2);
INSERT INTO teachers_courses (teacher_id, course_id) values (1,1);
INSERT INTO teachers_courses (teacher_id, course_id) values (1,2);
INSERT INTO teachers_courses (teacher_id, course_id) values (1,3);
INSERT INTO teachers_courses (teacher_id, course_id) values (1,4);
INSERT INTO teachers_courses (teacher_id, course_id) values (2,1);
INSERT INTO teachers_courses (teacher_id, course_id) values (2,2);
INSERT INTO teachers_courses (teacher_id, course_id) values (2,3);
INSERT INTO teachers_courses (teacher_id, course_id) values (2,4);
INSERT INTO teachers_courses (teacher_id, course_id) values (3,1);
INSERT INTO teachers_courses (teacher_id, course_id) values (3,2);
INSERT INTO teachers_courses (teacher_id, course_id) values (3,3);
INSERT INTO teachers_courses (teacher_id, course_id) values (3,4);
INSERT INTO teachers_courses (teacher_id, course_id) values (4,1);
INSERT INTO teachers_courses (teacher_id, course_id) values (4,2);
INSERT INTO teachers_courses (teacher_id, course_id) values (4,3);
INSERT INTO teachers_courses (teacher_id, course_id) values (4,4);
INSERT INTO teachers_courses (teacher_id, course_id) values (5,1);
INSERT INTO teachers_courses (teacher_id, course_id) values (5,2);
INSERT INTO teachers_courses (teacher_id, course_id) values (5,3);
INSERT INTO teachers_courses (teacher_id, course_id) values (5,4);
INSERT INTO students_courses (student_id, course_id) values (1,1);
INSERT INTO students_courses (student_id, course_id) values (1,2);
INSERT INTO students_courses (student_id, course_id) values (1,3);
INSERT INTO students_courses (student_id, course_id) values (1,4);
INSERT INTO students_courses (student_id, course_id) values (2,1);
INSERT INTO students_courses (student_id, course_id) values (2,2);
INSERT INTO students_courses (student_id, course_id) values (2,3);
INSERT INTO students_courses (student_id, course_id) values (2,4);
INSERT INTO students_courses (student_id, course_id) values (3,1);
INSERT INTO students_courses (student_id, course_id) values (3,2);
INSERT INTO students_courses (student_id, course_id) values (3,3);
INSERT INTO students_courses (student_id, course_id) values (3,4);
INSERT INTO students_courses (student_id, course_id) values (4,1);
INSERT INTO students_courses (student_id, course_id) values (4,2);
INSERT INTO students_courses (student_id, course_id) values (4,3);
INSERT INTO students_courses (student_id, course_id) values (4,4);
INSERT INTO students_courses (student_id, course_id) values (5,1);
INSERT INTO students_courses (student_id, course_id) values (5,2);
INSERT INTO students_courses (student_id, course_id) values (5,3);
INSERT INTO students_courses (student_id, course_id) values (5,4);
INSERT INTO students_courses (student_id, course_id) values (6,1);
INSERT INTO students_courses (student_id, course_id) values (6,2);
INSERT INTO students_courses (student_id, course_id) values (6,3);
INSERT INTO students_courses (student_id, course_id) values (6,4);
INSERT INTO students_courses (student_id, course_id) values (7,1);
INSERT INTO students_courses (student_id, course_id) values (7,2);
INSERT INTO students_courses (student_id, course_id) values (7,3);
INSERT INTO students_courses (student_id, course_id) values (7,4);