package cl.duoc.equipos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cl.duoc")
public class TorneosEquiposApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorneosEquiposApplication.class, args);
	}

}
