package org.disciullo.studentcourses.service;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.disciullo.studentcourses.model.Course;
import org.disciullo.studentcourses.model.Student;
import org.disciullo.studentcourses.repository.CourseRepository;
import org.disciullo.studentcourses.repository.CourseRepositoryTest;
import org.disciullo.studentcourses.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CourseRegistrationServiceImpl.class})
public class CourseRegistrationServiceUnitTest {

	// In the console for this test, you will not see any database logging - it does not connect to the database since the repositories are mocked
	// and not using @DataJpaTest.
	// If @DataJpaTest and @SpringBootTest(classes = Application.class) were used, it creates the database but does not insert or retrieve any data

	@MockBean
	private CourseRepository courseRepository;

	@MockBean
	private StudentRepository studentRepository;

	@Autowired
	private CourseRegistrationService registrationService;

	@Test
	public void testRegisterForCourses() {
		Student alex = new Student("alex");
		assertEquals(0, alex.getCourses().size());
		Course frenchCourse = new Course(CourseRepositoryTest.FRENCH);
		Course physicsCourse = new Course(CourseRepositoryTest.PHYSICS);
		Course chemistryCourse = new Course(CourseRepositoryTest.CHEMISTRY);

		Mockito.when(courseRepository.findByName(CourseRepositoryTest.FRENCH)).thenReturn(frenchCourse);
		Mockito.when(courseRepository.findByName(CourseRepositoryTest.PHYSICS)).thenReturn(physicsCourse);
		Mockito.when(courseRepository.findByName(CourseRepositoryTest.CHEMISTRY)).thenReturn(chemistryCourse);

		Mockito.when(studentRepository.saveAndFlush(alex)).thenReturn(alex);

		Set<String> courseNames = new HashSet<>();
		courseNames.add(CourseRepositoryTest.CHEMISTRY);
		courseNames.add(CourseRepositoryTest.FRENCH);
		courseNames.add(CourseRepositoryTest.PHYSICS);

		alex = registrationService.registerForCourses(alex, courseNames);
		assertEquals(3, alex.getCourses().size());

		// verify course list
		final Set<String> expectedCourses = courseNames;
		final Set<String> actualCourses = new HashSet<>();
		alex.getCourses().forEach(course -> actualCourses.add(course.getName()));
		assertEquals(expectedCourses, actualCourses);
	}

}
