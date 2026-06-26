package cl.duoc.inscripciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "cl.duoc")
@SpringBootApplication(scanBasePackages = {"cl.duoc.inscripciones", "cl.duoc.commons"})
public class TorneosInscripcionesApplication {

	public static void main(String[] args) {
		int defaultPort = 9007;
		int resolvedPort = isPortAvailable(defaultPort) ? defaultPort : 0;
		System.setProperty("server.port", String.valueOf(resolvedPort));
		SpringApplication.run(TorneosInscripcionesApplication.class, args);
	}

	private static boolean isPortAvailable(int port) {
		try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)) {
			return true;
		} catch (java.io.IOException e) {
			return false;
		}
	}
}
