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
	group_id serial PRIMARY KEY,
	group_name varchar(15) NOT NULL
);

CREATE TABLE teachers (
	teacher_id serial PRIMARY KEY,
    first_name varchar(15) NOT NULL,
    last_name varchar(15) NOT NULL,
    birthday text NOT NULL,
	email text NOT NULL,
	gender text NOT NULL,
	address text NOT NULL
);

CREATE TABLE students (
	student_id serial PRIMARY KEY,
    first_name varchar(15) NOT NULL,
    last_name varchar(15) NOT NULL,
    birthday text NOT NULL,
	email text NOT NULL,
	gender text NOT NULL,
	address text NOT NULL
);

CREATE TABLE courses(
	course_id serial PRIMARY KEY,
	course_name varchar(150) NOT NULL,
	course_description text NOT NULL
);

CREATE TABLE classrooms(
	classroom_id serial PRIMARY KEY,
	classroom_address varchar(150) NOT NULL,
	classroom_capacity INT NOT NULL
	
);

CREATE TABLE lectures (
	lecture_id serial PRIMARY KEY,
	local_date varchar(15) NOT NULL,
	local_time varchar(15) NOT NULL,
    course_id INT NOT NULL,
    classroom_id INT NOT NULL,
    group_id INT NOT NULL,
    teacher_id INT NOT NULL,
    CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses(course_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_classroom_id FOREIGN KEY (classroom_id) REFERENCES classrooms(classroom_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_teacher_id FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE dailyschedule(
	dailyschedule_id serial PRIMARY KEY,
	dailyschedule_date text NOT NULL
);

CREATE TABLE dailyschedules_lectures(
dailyschedule_id INT NOT NULL, 
lecture_id INT NOT NULL,
CONSTRAINT fk_dailyschedule_id FOREIGN KEY (dailyschedule_id) REFERENCES dailyschedule(dailyschedule_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_lecture_id FOREIGN KEY (lecture_id) REFERENCES lectures(lecture_id ) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT dailyschedule_lectures UNIQUE (dailyschedule_id, lecture_id)
);

CREATE TABLE students_courses(
student_id INT NOT NULL, 
course_id INT NOT NULL,
CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students(student_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses(course_id ) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT student_course UNIQUE (student_id, course_id)
);

CREATE TABLE teachers_courses(
teacher_id INT NOT NULL, 
course_id INT NOT NULL,
CONSTRAINT fk_teacher_id FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses(course_id ) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT teacher_course UNIQUE (teacher_id, course_id)
);

CREATE TABLE students_groups(
student_id INT NOT NULL, 
group_id INT NOT NULL,
CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES students(student_id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES groups(group_id ) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT student_group UNIQUE (student_id, group_id)
);