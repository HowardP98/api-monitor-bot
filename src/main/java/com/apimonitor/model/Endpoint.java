package com.apimonitor.model;

public record Endpoint(String name, String url, int timeoutSeconds) {}