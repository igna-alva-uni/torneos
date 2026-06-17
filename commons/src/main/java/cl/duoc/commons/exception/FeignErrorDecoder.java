package cl.duoc.commons.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.InputStream;
import java.util.Map;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = "Communication error between microservices";
        try {
            if (response.body() != null) {
                try (InputStream inputStream = response.body().asInputStream()) {
                    Map<?, ?> body = objectMapper.readValue(inputStream, Map.class);
                    if (body != null && body.containsKey("message")) {
                        message = body.get("message").toString();
                    } else if (body != null && body.containsKey("error")) {
                        message = body.get("error").toString();
                    }
                }
            }
        } catch (Exception e) {
            // Fallback to default message on parsing error
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException(message);
            case 404 -> new ResourceNotFoundException(message);
            case 409 -> new ConflictException(message);
            default -> defaultErrorDecoder.decode(methodKey, response);
        };
    }
}
