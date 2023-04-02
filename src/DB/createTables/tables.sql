CREATE TABLE DEPARTMENTS
(
    dept_name VARCHAR(5) NOT NULL,
    PRIMARY KEY (dept_name)
);

CREATE TABLE users
(
    userid   VARCHAR(20) NOT NULL,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    role     VARCHAR(5)  NOT NULL,
    dept     VARCHAR(5)  NOT NULL REFERENCES DEPARTMENTS (dept_name),
    phone    VARCHAR(15),
    email    VARCHAR(30),
    PRIMARY KEY (userid)
);


CREATE TABLE students
(
    student_id VARCHAR(20) NOT NULL REFERENCES users (userid) PRIMARY KEY

);

CREATE TABLE courses
(
    name    VARCHAR(50) NOT NULL,
    id      VARCHAR(10) NOT NULL,
    dept    VARCHAR(5)  NOT NULL,
    credits INTEGER     NOT NULL,
    active  BOOLEAN     NOT NUll,
    isBTP   BOOLEAN     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE prerequisites
(
    courseid    VARCHAR(10) REFERENCES courses (id),
    precourseid VARCHAR(10) REFERENCES courses (id),
    PRIMARY KEY (courseid, precourseid)
);

CREATE TABLE semesters
(
    id       VARCHAR(10),
    str      DATE,
    ends     DATE,
    add_drop DATE,
    withdraw DATE,
--     cred_limit INTEGER NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE offerings
(
    course_id VARCHAR(10)      NOT NULL,
    status    VARCHAR(10)      NOT NULL,
    semester  VARCHAR(10)      NOT NULL REFERENCES semesters (id),
    for_dept  VARCHAR(5)       NOT NULL,
    is_core   BOOLEAN          NOT NULL,
    cgpa      DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (course_id, semester, for_dept)
);



CREATE TABLE offering_instructors
(
    course_id     VARCHAR(10) REFERENCES courses (id),
    semester      VARCHAR(10) REFERENCES semesters (id),
    instructor_id VARCHAR(20) REFERENCES users (userid),
    is_coord      BOOLEAN NOT NULL
);

CREATE TABLE grades
(
    grade  VARCHAR(2) NOT NULL,
    weight INTEGER    NOT NULL,
    PRIMARY KEY (grade)
);

CREATE TABLE graduating_requirements
(
    credit_type  VARCHAR(2) NOT NULL,
    num_required INTEGER    NOT NULL,
    PRIMARY KEY (credit_type)
);

CREATE TABLE enrollments
(
    course_id   VARCHAR(10),
    student_id  VARCHAR(20) REFERENCES students (student_id),
    status      VARCHAR(10),
    semester    VARCHAR(10),
    grade       VARCHAR(2) REFERENCES grades (grade),
    credited_as VARCHAR(2) REFERENCES graduating_requirements (credit_type),
    PRIMARY KEY (course_id, student_id, semester)
);


CREATE TABLE logs
(
    userid  VARCHAR(20) references users (userid),
    time_in TIMESTAMP
);

