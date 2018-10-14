package org.disciullo.studentcourses.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * A simple model of a Student - contains a {@link Set} of {@link Course}s that the student has registered for  (or may register for  in the future).
 * Any {@link Course} in this {@link Set} with persist to the database if it is not already there.
 * @author Anne DiSciullo
 *
 */
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "student_course",
			joinColumns = { @JoinColumn(name = "student_id") },
			inverseJoinColumns = { @JoinColumn(name = "course_id") }
			)
	private Set<Course> courses = new HashSet<>();

	public Student() {
		super();
	}

	public Student(String name) {
		super();
		this.name = name;
	}

	public Student(String name, Set<Course> courses) {
		super();
		this.name = name;
		this.courses = courses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		String result = String.format(
				"Student [id=%d, name='%s']%n",
				id, name);
		if (courses != null) {
			for(Course course : courses) {
				result += String.format(
						"Course[id=%d, name='%s']%n",
						course.getId(), course.getName());
			}
		}

		return result;
	}

}
