package com.apimonitor;

import com.apimonitor.model.AppConfig;
import com.apimonitor.model.Endpoint;
import com.apimonitor.monitor.ApiChecker;
import com.apimonitor.notifier.WebhookNotifier;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando monitorización...");

        AppConfig config = loadConfig();
        if (config == null || config.endpoints() == null) {
            System.err.println("Error: No se pudo cargar la configuración.");
            return;
        }

        for (Endpoint endpoint : config.endpoints()) {
            ApiChecker.CheckResult result = ApiChecker.check(endpoint);

            if (result.isUp) {
                System.out.printf("[OK] %s - %.2fs%n", endpoint.name(), result.responseTimeSeconds);
            } else {
                String alertMsg = String.format("⚠️ **ALERTA:** %s (%s) está caída. Error: %s",
                        endpoint.name(), endpoint.url(), result.errorMessage);
                
                System.out.println(alertMsg);
                WebhookNotifier.sendAlert(config.webhookUrl(), alertMsg);
            }
        }
    }

    private static AppConfig loadConfig() {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream("/config.json")))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, AppConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}