package nuvemdesoftware.ceramicpro.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan({ "nuvemdesoftware.ceramicpro" })
@EnableJpaRepositories("nuvemdesoftware.ceramicpro.repository")
@EntityScan("nuvemdesoftware.ceramicpro.model")
@EnableWebSecurity(debug = true)
public class CeramicproApplication{

	public static void main(String[] args) {
		 SpringApplication.run(CeramicproApplication.class, args);
	}
}
