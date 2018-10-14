package org.disciullo.studentcourses.repository;

import java.util.List;

import org.disciullo.studentcourses.model.Course;
import org.disciullo.studentcourses.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository that performs basic CRUD functions on {@link Course}s as well as those defined by convention.
 * @author Anne DiSciullo
 *
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
	/*
	 https://spring.io/guides/gs/accessing-data-jpa/
	 Spring Data JPA focuses on using JPA to store data in a relational database.
	 Its most compelling feature is the ability to create repository implementations
	 automatically, at runtime, from a repository interface.
	 */
	/*
	 Comments below adapted from:
	 https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa/
	 When Spring Data JPA creates the Spring bean instance for the CourseRepository interface
	 it inspects all query methods defined in it and derives a query for each of them.
	 By default Spring Data JPA will automatically parse the method name and creates a query from it.
	 The query is implemented using the JPA criteria API. In this case the findByName(…)
	 method is logically equivalent to the JPQL query: "select c from Course c where c.name = ?1".
	 The parser that analyzes the method name supports quite a large set of
	 keywords such as And, Or, GreaterThan, LessThan, Like, IsNull, Not and so on.
	 */

	/**
	 * Find a {@link Course} by its name.
	 * @param name
	 * @return
	 */
	public Course findByName(String name);

	/**
	 * Get all students, sorted by their name, for a given course with course name as input.
	 * @param name
	 * @return
	 */
	@Query("SELECT s FROM Course c inner join c.students as s WHERE c.name = :name order by s.name")
	public List<Student> findOrderedStudentsByCourseName(@Param("name") String name);

}
