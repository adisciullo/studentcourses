package org.disciullo.studentcourses.repository;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.disciullo.studentcourses.Application;
import org.disciullo.studentcourses.model.Course;
import org.disciullo.studentcourses.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class CourseRepositoryTest {
	/*
	@DataJpaTest provides some standard setup needed for testing the persistence layer:
		- configuring H2, an in-memory database
		- setting Hibernate, Spring Data, and the DataSource
		- performing an @EntityScan
		- turning on SQL logging
	 */

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	public static final String FRENCH ="French";
	public static final String PHYSICS ="Physics";
	public static final String HISTORY_OF_MUSIC ="History of Music";
	public static final String PYSCHOLOGY ="Pyschology";
	public static final String CHEMISTRY ="Chemistry";
	public static final String POTTERY ="Pottery";

	private static Course frenchCourse = new Course(FRENCH);
	private static Course physicsCourse = new Course(PHYSICS);
	private static Course historyOfMusicCourse = new Course(HISTORY_OF_MUSIC);
	private static Course pyschologyCourse = new Course(PYSCHOLOGY);
	private static Course chemistryCourse = new Course(CHEMISTRY);
	private static Course potteryCourse = new Course(POTTERY);

	private static final String anne = "anne";
	private static final String solange = "solange";
	private static final String alyson = "alyson";
	private static final String ameer = "ameer";

	@Test
	public void testFindByName() {
		// given
		Course prg101 = new Course("Programming 101");
		entityManager.persist(prg101);
		entityManager.flush();
		entityManager.clear();

		// when
		Course found = courseRepository.findByName(prg101.getName());

		// then
		assertEquals(prg101.getName(), found.getName());
	}

	@Test
	public void testFindStudentsByCourseName() {
		courseRepository.saveAll(createCourses());
		courseRepository.flush();
		studentRepository.saveAll(createStudents());
		studentRepository.flush();
		entityManager.clear();
		//french - anne, solange, alyson, ameer
		//physics - anne, robert, adam, ameer
		//historyOfMusic - john, tess, david, celeste
		//pyschology - john, tess
		//chemistry - john, tess, robert, adam
		//pottery - david, solange, celeste, alyson

		List<Student> allInFrench = courseRepository.findOrderedStudentsByCourseName(frenchCourse.getName());
		assertEquals(4, allInFrench.size());
		assertEquals(alyson, allInFrench.get(0).getName());
		assertEquals(ameer, allInFrench.get(1).getName());
		assertEquals(anne, allInFrench.get(2).getName());
		assertEquals(solange, allInFrench.get(3).getName());
	}

	public static List<Course> createCourses() {
		return Arrays.asList(frenchCourse,
				physicsCourse,
				historyOfMusicCourse,
				pyschologyCourse,
				chemistryCourse,
				potteryCourse
				);
	}

	public static List<Student> createStudents() {
		Set<Course> courses1 = new HashSet<>();
		courses1.add(frenchCourse);
		courses1.add(physicsCourse);

		Set<Course> courses2 = new HashSet<>();
		courses2.add(historyOfMusicCourse);
		courses2.add(pyschologyCourse);
		courses2.add(chemistryCourse);

		Set<Course> courses3 = new HashSet<>();
		courses3.add(historyOfMusicCourse);
		courses3.add(potteryCourse);

		Set<Course> courses4 = new HashSet<>();
		courses4.add(frenchCourse);
		courses4.add(potteryCourse);

		Set<Course> courses5 = new HashSet<>();
		courses5.add(chemistryCourse);
		courses5.add(physicsCourse);

		return Arrays.asList(
				new Student(anne, courses1),
				new Student("john", courses2),
				new Student("tess", courses2),
				new Student("david", courses3),
				new Student(solange, courses4),
				new Student("robert", courses5),
				new Student("celeste", courses3),
				new Student(alyson, courses4),
				new Student("adam", courses5),
				new Student(ameer, courses1)
				);

	}

}
