package cl.duoc.torneos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "cl.duoc")
@SpringBootApplication
public class TorneosTorneosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorneosTorneosApplication.class, args);
	}

}
