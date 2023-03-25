package nuvemdesoftware.ceramicpro.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
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
