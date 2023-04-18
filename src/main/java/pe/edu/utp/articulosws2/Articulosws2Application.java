package pe.edu.utp.articulosws2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Articulosws2Application {

	public static void main(String[] args) {
		SpringApplication.run(Articulosws2Application.class, args);
	}

}
