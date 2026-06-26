package cl.duoc.juegos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cl.duoc.juegos", "cl.duoc.commons"})
public class TorneosJuegosApplication {

	public static void main(String[] args) {
		int defaultPort = 9003;
		int resolvedPort = isPortAvailable(defaultPort) ? defaultPort : 0;
		System.setProperty("server.port", String.valueOf(resolvedPort));
		SpringApplication.run(TorneosJuegosApplication.class, args);
	}

	private static boolean isPortAvailable(int port) {
		try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)) {
			return true;
		} catch (java.io.IOException e) {
			return false;
		}
	}
}
