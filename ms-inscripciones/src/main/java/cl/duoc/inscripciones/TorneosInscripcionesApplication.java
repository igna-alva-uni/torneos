package cl.duoc.inscripciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cl.duoc")
public class TorneosInscripcionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorneosInscripcionesApplication.class, args);
	}

}
