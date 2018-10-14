package org.disciullo.studentcourses.service;

import java.util.Set;

import org.disciullo.studentcourses.model.Course;
import org.disciullo.studentcourses.model.Student;

/**
 * Service to register {@link Student}s for their {@link Course}s.
 * @author Anne DiSciullo
 *
 */
public interface CourseRegistrationService {

	/**
	 * Look up each {@link Course} by name, then add to the set belonging to the student and save.
	 * @param student
	 * @param courses names of courses
	 * @return
	 */
	Student registerForCourses(Student student, Set<String> courses);

}
