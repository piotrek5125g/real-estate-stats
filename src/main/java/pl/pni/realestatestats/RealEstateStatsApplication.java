package pl.pni.realestatestats;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RealEstateStatsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateStatsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
