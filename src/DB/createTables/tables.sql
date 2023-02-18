CREATE TABLE users(
    userid VARCHAR(20) NOT NULL,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    role VARCHAR(5) NOT NULL,
    PRIMARY KEY (userid)
);

CREATE TABLE courses(
    name VARCHAR(30) NOT NULL,
    id VARCHAR(5) NOT NULL,
    dept VARCHAR(5) NOT NULL,
    credits INTEGER NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE semesters(
    id VARCHAR(10),
    str DATE,
    ends DATE,
    add_drop DATE,
    withdraw DATE,
    PRIMARY KEY (id)
);

CREATE TABLE offerings(
    course_id VARCHAR(5) NOT NULL REFERENCES courses(id),
    status VARCHAR(10) NOT NULL,
    semester VARCHAR(10) NOT NULL REFERENCES semesters(id),
    for_dept VARCHAR(5) NOT NULL,
    is_core BOOLEAN NOT NULL,
    PRIMARY KEY (course_id, semester, for_dept)
);

CREATE TABLE offering_instructors(
    course_id VARCHAR(5) REFERENCES courses(id),
    semester VARCHAR(10) REFERENCES semesters(id),
    instructor_id VARCHAR(30) REFERENCES users(userid),
    is_coord BOOLEAN NOT NULL
);

CREATE TABLE enrollments(
    course_id VARCHAR(5),
    student_id VARCHAR(30),
    status VARCHAR(10),
    semester VARCHAR(10),
    grade VARCHAR(2),
    credited_as  VARCHAR(2),
    PRIMARY KEY (course_id, student_id, semester)
);


INSERT INTO users values ('rishabhjain1909', 'Rishabh Jain', 'strongpassword', 'STU');
INSERT INTO semesters values ('2023-W', '2023-01-02', '2023-05-05', '2023-01-16', '2023-02-28');
INSERT INTO courses values ('Machine Learning', 'CS503', 'CSE', 4);
INSERT INTO offerings values ('CS503', 'Enrolling', '2023-W', 'CSE', true);
INSERT INTO offerings values ('CS503', 'Enrolling', '2023-W', 'MNC', true);
INSERT INTO offerings values ('CS503', 'Enrolling', '2023-W', 'EE', false);


SELECT name, id, is_core, credits FROM offerings, courses WHERE id = course_id and semester = '2023-W' and for_dept = 'CSE';