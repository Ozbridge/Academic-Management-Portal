# Unit Test Plan #

There is a Test class corresponding to every class in the Application.

The test classes are placed in SourceCode/src/test/java/org.soft/

The menu classes are only tested for coverage as they are simply responsible for input and output, so checking content
of the inputs and outputs would be redundant. All other classes are tested with assertions for correctness of output.

The functions performing different functions mostly have multiple test cases to cover all different branches. Some area
of the code for example catch block for SQLException remains uncovered because SQLException is never generated in normal
use.
This is responsible for ~5% loss of coverage.

According to the report line coverage of 93% and branch coverage of 89% was achieved.

To run the tests manually open a terminal in the SourceCode folder and run 'mvn clean compile verify'.

List of Test classes:

- AcademicServicesTest
- AdminMenuTest
- AdminServicesTest
- AuthServiceTest
- DatabaseServiceTest
- FacultyMenuTest
- FacultyServicesTest
- MainTest
- StudentMenuTest
- StudentServicesTest

####   ####

Structure of a Test Class:

- @BeforeAll - for statically initializing object of the class being tested. Also setting transactions to not autocommit
  to avoid changes in the database.
- @Test - for defining a unit test for the corresponding function being tested in the original class.
- @AfterAll - for restoring autocommit (not always needed).

Plan for testing Interactive methods:

- For method requiring user-input, System.in was set to be a custom ByteStream which simulated a given string as
  keyboard input.
- After the test System.in was restored to the backed up default stream.

Aim of Test:

- Highest possible line and branch coverage. Some branches coverage is inevitably as explained above.
- Simulating all reasonable use cases.
- Ensuring no bugs exist in those use cases.

