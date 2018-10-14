package org.disciullo.studentcourses.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class StudentRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private StudentRepository studentRepository;

	private Course french = new Course("French");
	private Course physics = new Course("Physics");
	private Course historyOfMusic = new Course("History of Music");
	private Course pyschology = new Course("Pyschology");
	private Course chemistry = new Course("Chemistry");
	private Course pottery = new Course("Pottery");

	@Test
	public void testFindByName() {
		// given
		Student alex = new Student("alex");
		entityManager.persist(alex);
		entityManager.flush();
		entityManager.clear();

		// when
		Student found = studentRepository.findByName(alex.getName());

		// then
		assertEquals(alex.getName(), found.getName());
	}

	@Test
	public void testAddStudentsWithCoursesAndDelete() {
		Set<Course> courses1 = new HashSet<>();
		courses1.add(french);
		courses1.add(physics);
		courses1.add(pottery);

		Set<Course> courses2 = new HashSet<>();
		courses2.add(historyOfMusic);
		courses2.add(pyschology);
		courses2.add(chemistry);

		Student anne = new Student("anne", courses1);
		Student john = new Student("john", courses2);
		Student tess = new Student("tess", courses2);
		studentRepository.saveAndFlush(anne);
		studentRepository.saveAndFlush(john);
		//logging shows that the courses in set2 do not need to be created on the call below
		studentRepository.saveAndFlush(tess);
		List<Student> students = studentRepository.findAll();
		assertEquals(3, students.size());
		for (Student s : students) {
			String studentName = s.getName();
			if (studentName.equals("anne")) {
				assertTrue(s.getCourses().contains(pottery));
			}
		}

		studentRepository.delete(anne);
		students = studentRepository.findAll();
		assertEquals(2, students.size());

		studentRepository.delete(john);
		students = studentRepository.findAll();
		assertEquals(1, students.size());
		assertTrue(students.get(0).getCourses().contains(chemistry));
	}

}
