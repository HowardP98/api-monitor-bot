# 🚀 API Health & Performance Monitor

A professional, lightweight automated tool built in **Java 17** to monitor the availability and latency of web services. This project demonstrates clean architecture, modern Java features, and integration with third-party notification systems via Webhooks.

## 🎯 Purpose

In production environments, knowing when an API is down before your users do is critical. This tool automates that process by periodically checking endpoints, measuring response times, and firing instant alerts to **Discord** or **Slack** when issues are detected.

## ✨ Key Features

* **Modern Java Stack:** Utilizes Java 17 `Records` for immutable data models and the native `HttpClient` for high-performance requests.
* **Real-time Alerts:** Seamless integration with Discord/Slack via incoming webhooks.
* **Clean Architecture:** Strict separation of concerns (Models, Monitors, Notifiers).
* **Robust Testing:** Comprehensive test suite using **JUnit 5** to handle timeouts, 500 errors, and connection failures.
* **Configuration Driven:** Easily manage monitored endpoints through a simple `config.json` file.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Build System:** Maven
* **Libraries:** Google GSON (JSON Parsing), JUnit 5 (Testing)

## 🚀 Installation & Usage

### 1. Prerequisites

* Java Development Kit (JDK) 17 or higher
* Apache Maven installed

### 2. Configuration

Edit the configuration file located at `src/main/resources/config.json`:

```json
{
  "webhookUrl": "YOUR_WEBHOOK_URL",
  "endpoints": [
    {
      "name": "Production API",
      "url": "https://api.example.com/status",
      "timeoutSeconds": 5
    }
  ]
}
```

### 3. Build & Run

```bash
# Compile and run tests
mvn clean test

# Build the executable JAR
mvn clean package

# Run the application
java -cp "target/api-monitor-1.0-SNAPSHOT.jar;target/dependency/*" com.apimonitor.Main
```

## 🧪 Testing

The project includes unit tests covering:

* Successful health checks
* HTTP 500 Internal Server Error handling
* Network timeout scenarios
* Malformed URL exceptions

## 📝 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

Developed by **HowardP98** as a full-stack portfolio project.
