package cl.duoc.autenticaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TorneosAutenticacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorneosAutenticacionesApplication.class, args);
	}

}
