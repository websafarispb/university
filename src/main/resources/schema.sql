DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS classrooms CASCADE;
DROP TABLE IF EXISTS students_courses CASCADE;
DROP TABLE IF EXISTS teachers_courses CASCADE;
DROP TABLE IF EXISTS students_groups CASCADE;
DROP TABLE IF EXISTS lectures CASCADE;
DROP TABLE IF EXISTS dailyschedules_lectures CASCADE;
DROP TABLE IF EXISTS dailyschedule CASCADE;

CREATE TABLE groups(
	id serial PRIMARY KEY,
	group_name varchar(15) NOT NULL
);

CREATE TABLE teachers (
	id serial PRIMARY KEY,
	personal_number INT NOT NULL,
    first_name varchar(15) NOT NULL,
    last_name varchar(15) NOT NULL,
    birthday DATE NOT NULL,
	email text NOT NULL,
	gender text NOT NULL,
	address text NOT NULL
);

CREATE TABLE students (
	id serial PRIMARY KEY,
	personal_number INT NOT NULL,
    first_name varchar(15) NOT NULL,
    last_name varchar(15) NOT NULL,
    birthday DATE NOT NULL,
	email text NOT NULL,
	gender text NOT NULL,
	address text NOT NULL
);

CREATE TABLE courses(
	id serial PRIMARY KEY,
	course_name varchar(150) NOT NULL,
	course_description text NOT NULL
);

CREATE TABLE classrooms(
	id serial PRIMARY KEY,
	classroom_address varchar(150) NOT NULL,
	classroom_capacity INT NOT NULL
	
);

CREATE TABLE dailyschedule(
	id serial PRIMARY KEY,
	dailyschedule_date DATE NOT NULL
);

CREATE TABLE lectures (
	id serial PRIMARY KEY,
	dailyschedule_id INT NOT NULL, 
	local_time TIME NOT NULL,
    course_id INT NOT NULL,
    classroom_id INT NOT NULL,
    group_id INT NOT NULL,
    teacher_id INT NOT NULL,
    CONSTRAINT fkd_dailyschedule_id FOREIGN KEY (dailyschedule_id) REFERENCES dailyschedule(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_classroom_id FOREIGN KEY (classroom_id) REFERENCES classrooms(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fkl_group_id FOREIGN KEY (group_id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fkl_teacher_id FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE students_courses(
student_id INT NOT NULL, 
course_id INT NOT NULL,
CONSTRAINT fks_student_id FOREIGN KEY (student_id) REFERENCES students(id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fks_course_id FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT student_course UNIQUE (student_id, course_id)
);

CREATE TABLE teachers_courses(
teacher_id INT NOT NULL, 
course_id INT NOT NULL,
CONSTRAINT fkt_teacher_id FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fkt_course_id FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT teacher_course UNIQUE (teacher_id, course_id)
);

CREATE TABLE students_groups(
student_id INT NOT NULL, 
group_id INT NOT NULL,
CONSTRAINT fksg_student_id FOREIGN KEY (student_id) REFERENCES students(id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fksg_group_id FOREIGN KEY (group_id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT student_group UNIQUE (student_id, group_id)
);