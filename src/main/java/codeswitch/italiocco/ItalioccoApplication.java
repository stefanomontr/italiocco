package codeswitch.italiocco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "codeswitch.italiocco.repos")
public class ItalioccoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItalioccoApplication.class, args);
	}

}
