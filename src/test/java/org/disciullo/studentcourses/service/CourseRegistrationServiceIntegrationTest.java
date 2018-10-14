package org.disciullo.studentcourses.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.disciullo.studentcourses.Application;
import org.disciullo.studentcourses.model.Course;
import org.disciullo.studentcourses.model.Student;
import org.disciullo.studentcourses.repository.CourseRepository;
import org.disciullo.studentcourses.repository.CourseRepositoryTest;
import org.disciullo.studentcourses.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
//@ContextConfiguration(classes = {RegistrationServiceImpl.class}) // can do this instead of @ComponentScan
@ComponentScan(basePackages = {"org.disciullo.studentcourses"}) // this seemed like the best course of action
public class CourseRegistrationServiceIntegrationTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CourseRegistrationService registrationService;

	// can do this instead of the @ContextConfiguration(classes = {RegistrationServiceImpl.class}) above or @ComponentScan
	//	@TestConfiguration
	//	static class RegistrationServiceIntegrationTestContextConfiguration {
	//
	//		@Bean
	//		public RegistrationService registrationService() {
	//			return new RegistrationServiceImpl();
	//		}
	//	}

	@Test
	public void testRegisterForCourses() {
		Student alex = new Student("alex");
		studentRepository.saveAndFlush(alex);
		List<Course> courses = CourseRepositoryTest.createCourses();
		courseRepository.saveAll(courses);
		courseRepository.flush();

		Set<String> courseNames =  new HashSet<>(Arrays.asList(
				CourseRepositoryTest.CHEMISTRY,
				CourseRepositoryTest.FRENCH,
				CourseRepositoryTest.PHYSICS
				));

		alex = registrationService.registerForCourses(alex, courseNames);

		// detach and reload alex so you get the real data from the db
		entityManager.clear();
		alex = studentRepository.findByName(alex.getName());

		assertEquals(3, alex.getCourses().size());

		// verify course list
		final Set<String> expectedCourses = courseNames;
		final Set<String> actualCourses = new HashSet<>();
		alex.getCourses().forEach(course -> actualCourses.add(course.getName()));
		assertEquals(expectedCourses, actualCourses);
	}

}
