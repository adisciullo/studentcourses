# studentcourses
A simple Spring Boot app which creates courses and students and where the students can register for classes.

I decided to use Spring Boot with Hibernate and an in-memory H2 database. This has the added benefit of making it easy for whomever tries to run this a code.

Import as a maven project into either Eclipse or IntelliJ.

All classes have tests. The CourseRegistrationService has both an integration test (which connects to the database, creates data and tests the results) and a unit test, which mocks the repositories.

Most of the tests use the @DataJpaTest annotation which provides some standard setup needed for testing the persistence layer:
- configuring H2, an in-memory database
- setting Hibernate, Spring Data, and the DataSource
- performing an @EntityScan
- turning on SQL logging
