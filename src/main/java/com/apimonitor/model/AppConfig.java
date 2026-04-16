package com.apimonitor.model;
import java.util.List;

public record AppConfig(String webhookUrl, List<Endpoint> endpoints) {}