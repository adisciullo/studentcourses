package org.disciullo.studentcourses.service;

import java.util.HashSet;
import java.util.Set;

import org.disciullo.studentcourses.model.Course;
import org.disciullo.studentcourses.model.Student;
import org.disciullo.studentcourses.repository.CourseRepository;
import org.disciullo.studentcourses.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Transactional
	@Override
	public Student registerForCourses(Student student, Set<String> courses) {
		Set<Course> courseObjects = new HashSet<Course>();
		for (String name : courses) {
			Course course = courseRepository.findByName(name);
			if (course != null) {
				courseObjects.add(course);
			}
		}
		student.setCourses(courseObjects);
		return studentRepository.saveAndFlush(student);
	}
	/*
	 I debated what method I should put in here..
	 I could have done:
	 Student registerForCourses(Student student, Set<Course> courses)
	 Then this would have been a simple pass through to
	 student.setCourses(courseObjects);
	 studentRepository.saveAndFlush(student)
	 Which leads me to think, if I had the set of course objects at the time of making the call,
	 why wouldn't I just set it on the student and save then?
	 It would depend on how the "front end" implemented the students choosing their courses.
	 */

}
