package org.disciullo.studentcourses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class that Spring Boot looks for to run.
 * @author Anne DiSciullo
 *
 */
@EntityScan("org.disciullo.studentcourses.model")
@EnableJpaRepositories("org.disciullo.studentcourses.repository")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
