package cl.duoc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TorneosGatewayApplication {

    public static void main(String[] args) {
        int defaultPort = 9000;
        int resolvedPort = isPortAvailable(defaultPort) ? defaultPort : 0;
        System.setProperty("server.port", String.valueOf(resolvedPort));
        SpringApplication.run(TorneosGatewayApplication.class, args);
    }

    private static boolean isPortAvailable(int port) {
        try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)) {
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }
}
