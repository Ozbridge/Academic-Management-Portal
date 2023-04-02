INSERT INTO public.semesters (id, str, ends, add_drop, withdraw)
VALUES ('2023-I', '2023-07-12', '2024-01-03', '2023-08-18', '2023-10-03');
INSERT INTO public.semesters (id, str, ends, add_drop, withdraw)
VALUES ('2022-II', '2022-12-09', '2023-05-30', '2023-01-16', '2023-03-20');
INSERT INTO public.semesters (id, str, ends, add_drop, withdraw)
VALUES ('2022-I', '2022-07-12', '2022-12-08', '2022-07-20', '2022-07-31');



INSERT INTO public.departments (dept_name)
VALUES ('CSE');
INSERT INTO public.departments (dept_name)
VALUES ('HS');
INSERT INTO public.departments (dept_name)
VALUES ('CHE');
INSERT INTO public.departments (dept_name)
VALUES ('ADM');


INSERT INTO public.grades (grade, weight)
VALUES ('A', 10);
INSERT INTO public.grades (grade, weight)
VALUES ('A-', 9);
INSERT INTO public.grades (grade, weight)
VALUES ('B', 8);
INSERT INTO public.grades (grade, weight)
VALUES ('B-', 7);
INSERT INTO public.grades (grade, weight)
VALUES ('C', 6);
INSERT INTO public.grades (grade, weight)
VALUES ('C-', 5);
INSERT INTO public.grades (grade, weight)
VALUES ('D', 4);
INSERT INTO public.grades (grade, weight)
VALUES ('F', 0);
INSERT INTO public.grades (grade, weight)
VALUES ('AP', 0);
INSERT INTO public.grades (grade, weight)
VALUES ('AF', 0);


INSERT INTO public.graduating_requirements (credit_type, num_required)
VALUES ('A', 0);
INSERT INTO public.graduating_requirements (credit_type, num_required)
VALUES ('C', 8);
INSERT INTO public.graduating_requirements (credit_type, num_required)
VALUES ('E', 0);
INSERT INTO public.graduating_requirements (credit_type, num_required)
VALUES ('B', 3);


INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('cse001', 'Dr Shweta Jain', 'faculty', 'FAC', 'CSE', null, null);
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('cse002', 'Dr Anil Shukla', 'faculty', 'FAC', 'CSE', null, null);
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('cse003', 'Dr Sujata Pal', 'faculty', 'FAC', 'CSE', null, null);
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('cse004', 'Dr Sudarshan Iyengar', 'faculty', 'FAC', 'CSE', null, null);
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('che001', 'Dr Sarang Gumfekar', 'faculty', 'FAC', 'CHE', null, null);
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('ad001', 'Dean Acad', 'dean', 'ADM', 'ADM', null, null);
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('hs001', 'Dr Aparna N', 'faculty', 'FAC', 'HS', null, null);
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('2020csb1198', 'Rishabh Jain', 'rishabh', 'STU', 'CSE', '+6392396338', '2020csb1198@iitrpr.ac.in');
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('2020csb1153', 'Aman Kumar', 'aman', 'STU', 'CSE', null, '2020csb1153@iitrpr.ac.in');
INSERT INTO public.users (userid, username, password, role, dept, phone, email)
VALUES ('hs002', 'Dr Sreekumar Jayadevan', 'faculty', 'FAC', 'HS', null, null);



INSERT INTO public.students (student_id)
VALUES ('2020csb1198');
INSERT INTO public.students (student_id)
VALUES ('2020csb1153');

INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Machine Learning', 'CS503', 'CSE', 4, true, false);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Computer Networks', 'CS304', 'CSE', 4, true, false);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Theory of Computation', 'CS306', 'CSE', 4, true, false);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Social Computing', 'CS522', 'CSE', 4, true, false);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Introduction to Environment', 'GE111', 'CHE', 3, true, false);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Fantasy and Fiction', 'HS303', 'HS', 3, true, false);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('DEP', 'CP301', 'CSE', 3, true, true);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Capstone-I', 'CP302', 'CSE', 3, true, true);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Capstone-II', 'CP303', 'CSE', 3, true, true);
INSERT INTO public.courses (name, id, dept, credits, active, isbtp)
VALUES ('Optimization', 'CS401', 'CSE', 3, false, false);



INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('GE111', 'Offering', '2023-I', 'HS', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('HS303', 'Offering', '2023-I', 'CSE', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('HS303', 'Offering', '2023-I', 'CHE', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('HS303', 'Offering', '2023-I', 'HS', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CP302', 'Offering', '2023-I', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CP303', 'Offering', '2023-I', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS522', 'Offering', '2023-I', 'CSE', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CP301', 'Offering', '2023-I', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS522', 'Offering', '2023-I', 'CHE', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('GE111', 'Offering', '2023-I', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('GE111', 'Offering', '2023-I', 'CHE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS503', 'Offering', '2022-II', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS503', 'Offering', '2022-II', 'CHE', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS306', 'Offering', '2022-II', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS306', 'Offering', '2022-II', 'HS', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS304', 'Offering', '2022-II', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS304', 'Offering', '2022-II', 'CHE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS304', 'Offering', '2022-II', 'HS', false, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CP301', 'Offering', '2022-I', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('HS303', 'Offering', '2022-I', 'CSE', true, 0);
INSERT INTO public.offerings (course_id, status, semester, for_dept, is_core, cgpa)
VALUES ('CS522', 'Offering', '2022-I', 'CSE', true, 0);



INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CS522', '2022-I', 'cse004', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('HS303', '2022-I', 'hs001', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CS306', '2022-II', 'cse002', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CS304', '2022-II', 'cse003', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CS503', '2022-II', 'cse001', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CP302', '2023-I', 'cse003', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CP303', '2023-I', 'cse003', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CS522', '2023-I', 'cse004', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CP301', '2023-I', 'cse001', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('CP301', '2023-I', 'cse001', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('GE111', '2023-I', 'che001', true);
INSERT INTO public.offering_instructors (course_id, semester, instructor_id, is_coord)
VALUES ('HS303', '2023-I', 'hs001', true);


INSERT INTO public.prerequisites (courseid, precourseid)
VALUES ('CP303', 'CP302');


INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('CP301', '2020csb1198', 'Enrolled', '2022-I', 'A-', 'B');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('CS522', '2020csb1198', 'Enrolled', '2022-I', 'A', 'C');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('HS303', '2020csb1198', 'Enrolled', '2022-I', 'A', 'C');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('CS503', '2020csb1198', 'Enrolled', '2022-II', null, 'C');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('CS304', '2020csb1198', 'Enrolled', '2022-II', null, 'C');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('CS503', '2020csb1153', 'Enrolled', '2022-II', null, 'C');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('CP302', '2020csb1198', 'Enrolled', '2023-I', null, 'B');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('CP303', '2020csb1198', 'Enrolled', '2023-I', null, 'B');
INSERT INTO public.enrollments (course_id, student_id, status, semester, grade, credited_as)
VALUES ('HS303', '2020csb1153', 'Enrolled', '2023-I', null, 'E');
