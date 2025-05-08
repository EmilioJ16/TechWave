package es.uc3m.microblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@EnableCaching
public class TechWaveApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(TechWaveApplication.class, args);
	}
}
