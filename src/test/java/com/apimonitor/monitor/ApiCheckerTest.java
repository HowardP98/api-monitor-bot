package com.apimonitor.monitor;

import com.apimonitor.model.Endpoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiCheckerTest {

    @Test
    void testCheckEndpointIsUp() {
        // Arrange
        Endpoint google = new Endpoint("Google", "https://www.google.com", 5);

        // Act
        ApiChecker.CheckResult result = ApiChecker.check(google);

        // Assert
        assertTrue(result.isUp, "El endpoint debería estar activo");
        assertEquals("", result.errorMessage, "No debería haber mensaje de error");
        assertTrue(result.responseTimeSeconds > 0, "El tiempo de respuesta debe ser mayor a 0");
    }

    @Test
    void testCheckEndpointReturnsError() {
        // Arrange: Usamos httpbin.org para simular un Error 500
        Endpoint badEndpoint = new Endpoint("Error 500", "https://httpbin.org/status/500", 5);

        // Act
        ApiChecker.CheckResult result = ApiChecker.check(badEndpoint);

        // Assert
        assertFalse(result.isUp, "El endpoint debería ser marcado como caído");
        assertTrue(result.errorMessage.contains("500"), "El error debe mencionar el status code 500");
    }

    @Test
    void testCheckEndpointTimeout() {
        // Arrange: httpbin.org/delay/3 tarda exactamente 3 segundos en responder
        // Configuramos nuestro monitor con un límite estricto de 1 segundo
        Endpoint slowEndpoint = new Endpoint("Lento", "https://httpbin.org/delay/3", 1);

        // Act
        ApiChecker.CheckResult result = ApiChecker.check(slowEndpoint);

        // Assert
        assertFalse(result.isUp, "Debería fallar porque superó el tiempo máximo");
        assertEquals("Timeout excedido", result.errorMessage);
    }
    
    @Test
    void testCheckEndpointInvalidUrl() {
        // Arrange
        Endpoint invalidEndpoint = new Endpoint("Inválido", "esto-no-es-una-url", 5);

        // Act
        ApiChecker.CheckResult result = ApiChecker.check(invalidEndpoint);

        // Assert
        assertFalse(result.isUp);
        assertNotEquals("", result.errorMessage, "Debería capturar la excepción y mostrarla");
    }
}