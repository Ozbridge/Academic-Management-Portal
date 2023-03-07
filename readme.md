# Academic Management Portal #

## Rishabh Jain 2020CSB1198 ##

- Instructions for running:

    - First step is to create a database.
      Open postgres in terminal and copy all the queries from createTable.sql and inserts.sql into the console.

    - Make sure that there is a user with read/write privileges with name = "postgres" and password = "postgres";

    - Open terminal in the directory containing the jar file and enter the following:

      java -jar ./AcademicPortal.jar


- Guide for using the menu:

    - Immediately upon entering it will ask for a username and password.

    - After a successful login the user will be show a different menu dependent on their role.

    - After every request (valid/invalid) the user is prompted to either log out or continue.

    - After every log out the user is prompted to either quit or continue so another user can log in.

- Assumptions:
    - Course code uniquely identifies a particular course. For example CP301 cannot be offered by both CSE and CHE
      departments. To allow this a simple method is to append department prefix to the course code like CP301-CS,
      CP301-CH. (Note: I have simply used CP301 in testing offered by CSE department)
    - A particular course (given by course code) can only be offered once in a semester.
    - CSV for uploading grades in the correct format which is the pair studentid,grade seperated by newlines.
    - Semester will be in form of "2022-II", "2023-I", and so on.