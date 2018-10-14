package org.disciullo.studentcourses.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 * Simple model for a course - contain a {@link Set} of {@link Student}s to which it is mapped bi-directionally.
 * @author Anne DiSciullo
 *
 */
@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@ManyToMany(mappedBy = "courses")
	private Set<Student> students = new HashSet<>();

	public Course() {
		super();
	}

	public Course(String name) {
		super();
		this.name = name;
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

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Course: id: " + this.id + ", name: " + this.name;
	}
}
