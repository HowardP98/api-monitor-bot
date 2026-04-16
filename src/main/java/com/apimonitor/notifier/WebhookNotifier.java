package com.apimonitor.notifier;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebhookNotifier {

    public static void sendAlert(String webhookUrl, String message) {
        if (webhookUrl == null || webhookUrl.equals("TU_URL_DE_WEBHOOK_AQUI")) {
            return; // No intentar enviar si no hay webhook configurado
        }

        try {
            // Payload simple en formato JSON para Discord/Slack
            String jsonPayload = "{\"content\": \"" + message + "\"}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(webhookUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Alerta enviada: " + message);

        } catch (Exception e) {
            System.out.println("Fallo al enviar la alerta: " + e.getMessage());
        }
    }
}