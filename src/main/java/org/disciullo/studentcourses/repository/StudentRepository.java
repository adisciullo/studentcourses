package org.disciullo.studentcourses.repository;

import org.disciullo.studentcourses.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that performs basic CRUD functions on {@link Student}s as well as those defined by convention.
 * @author Anne DiSciullo
 *
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	/**
	 * Find a {@link Student} by name.
	 * @param name
	 * @return
	 */
	public Student findByName(String name);

}
