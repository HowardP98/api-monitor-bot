package com.apimonitor.monitor;

import com.apimonitor.model.Endpoint;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiChecker {

    public static class CheckResult {
        public final boolean isUp;
        public final double responseTimeSeconds;
        public final String errorMessage;

        public CheckResult(boolean isUp, double responseTimeSeconds, String errorMessage) {
            this.isUp = isUp;
            this.responseTimeSeconds = responseTimeSeconds;
            this.errorMessage = errorMessage;
        }
    }

    public static CheckResult check(Endpoint endpoint) {
        long startTime = System.currentTimeMillis();
        
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(endpoint.timeoutSeconds()))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint.url()))
                    .timeout(Duration.ofSeconds(endpoint.timeoutSeconds()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            double responseTime = (System.currentTimeMillis() - startTime) / 1000.0;

            if (response.statusCode() == 200) {
                return new CheckResult(true, responseTime, "");
            } else {
                return new CheckResult(false, responseTime, "Status Code: " + response.statusCode());
            }

        } catch (java.net.http.HttpTimeoutException e) {
            return new CheckResult(false, endpoint.timeoutSeconds(), "Timeout excedido");
        } catch (Exception e) {
            return new CheckResult(false, 0.0, e.getMessage());
        }
    }
}