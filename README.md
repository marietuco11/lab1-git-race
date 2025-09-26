# Modern Web Application

A modern Spring Boot application built with Kotlin, featuring a responsive web interface and REST API endpoints.

## 🚀 Features

- **Modern Tech Stack**: Spring Boot 3.5.3, Kotlin 2.2.10, Java 17 LTS
- **Responsive UI**: Bootstrap 5.3.3 with modern design
- **REST API**: JSON endpoints with timestamp support
- **Health Monitoring**: Spring Boot Actuator for application health
- **Live Development**: Spring Boot DevTools for automatic reload
- **Interactive HTTP Debugging**: Client-side HTTP request/response visualization
- **Containerization**: Docker support with multi-stage builds
- **Comprehensive Testing**: Unit, integration, and MVC tests
- **Modern Kotlin**: Constructor injection, data classes, and modern syntax

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.5.3
- **Language**: Kotlin 2.2.10
- **Java Version**: 17 LTS
- **Frontend**: Bootstrap 5.3.3, Thymeleaf
- **Build Tool**: Gradle 9.0.0
- **Testing**: JUnit 5, AssertJ, MockMvc
- **Containerization**: Docker

## 📋 Prerequisites

- Java 21 or higher
- Gradle 9.0.0 or higher
- Docker (optional)

## 🏃‍♂️ Quick Start

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd template-lab1-git-race
   ```

2. **Build the application**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the application**
   - Web Interface: http://localhost:8080
   - API Endpoint: http://localhost:8080/api/hello
   - Health Check: http://localhost:8080/actuator/health

### Using Docker for Development

1. **Using Docker Compose** (Recommended):
   ```bash
   docker-compose -f docker-compose.dev.yml up --build
   ```

2. **Build and run development container**:
   ```bash
   docker build -f Dockerfile.dev -t modern-web-app-dev .
   docker run -p 8080:8080 -p 35729:35729 -v $(pwd):/app modern-web-app-dev
   ```

The development Docker setup includes:
- **LiveReload Support**: Automatic browser refresh on code changes
- **Volume Mounting**: Source code changes are immediately reflected
- **Spring Boot DevTools**: Automatic application restart on file changes
- **Health Monitoring**: Built-in health checks via Spring Boot Actuator

## 🧪 Testing

Run all tests:
```bash
./gradlew test
```

Run specific test classes:
```bash
./gradlew test --tests "HelloControllerUnitTests"
./gradlew test --tests "IntegrationTest"
```

## 📡 API Endpoints

### Web Endpoints
- `GET /` - Main web page with interactive HTTP debugging tools
- `GET /?name={name}` - Personalized greeting page
- `GET /?lang={locale}` - Change the language of the web interface (e.g., ?lang=es).

### REST API Endpoints
- `GET /api/hello` - Returns JSON greeting (Good morning/afternoon/evening) with timestamp (cached per name)
- `GET /api/hello?name={name}` - Returns personalized JSON greeting with timestamp (cached per name)
- `GET /api/hello/history` - Returns the list of all greetings made since the server started


### Monitoring Endpoints
- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Application metrics

### Interactive HTTP Debugging
- **Web Page Testing**: Test the main page with personalized greetings
- **API Testing**: Test REST endpoints with real-time request/response display
- **Health Check Testing**: Monitor application health status
- **Live Reload**: Spring Boot DevTools automatically reloads on file changes

## 🏗️ Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   ├── controller/
│   │   │   └── HelloController.kt      # Web and API controllers
│   │   └── HelloWorld.kt               # Main application class
│   └── resources/
│       ├── application.properties      # Application configuration
|       ├── messages.properties         # Messages language  
|       ├── messages_es.properties
|       ├── messages_fr.properties
│       ├── templates/
│       │   └── welcome.html           # Thymeleaf template
│       └── public/
│           └── assets/
│               └── logo.svg           # Application logo
└── test/
    └── kotlin/
        ├── controller/
        │   ├── HelloControllerUnitTests.kt    # Unit tests
        │   └── HelloControllerMVCTests.kt     # MVC tests
        └── IntegrationTest.kt                 # Integration tests
```

## ⚙️ Configuration

Key configuration options in `application.properties`:

```properties
# Application settings
spring.application.name=modern-web-app
server.port=8080

# Custom message
app.message=Welcome to the Modern Web App!

# Actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics
```

## 🐳 Docker Details

The application includes a development-focused Docker setup:

- **Development Dockerfile**: Uses JDK 21 Alpine for development with live reload
- **Docker Compose**: Orchestrates the development environment with volume mounting
- **LiveReload**: Spring Boot DevTools automatically reloads on file changes
- **Volume Mounting**: Source code changes are immediately reflected in the container
- **Health Checks**: Built-in health monitoring via Spring Boot Actuator
- **Development Tools**: Includes wget for health checks and debugging utilities

## 🔧 Development
### Caching
The `/api/hello` endpoint now uses Spring Boot caching. 
Repeated requests with the same `name` parameter return the cached greeting 
instead of recalculating it. This improves performance and reduces logging.

### Adding New Features

1. **Controllers**: Add new endpoints in the controller package
2. **Templates**: Add new Thymeleaf templates in `src/main/resources/templates/`
3. **Tests**: Add corresponding tests in the test package
4. **Configuration**: Update `application.properties` for new settings

### Code Style

- Use modern Kotlin features (constructor injection, data classes)
- Follow Spring Boot best practices
- Write comprehensive tests for all functionality
- Use descriptive test method names with backticks

## 📊 Monitoring

The application includes Spring Boot Actuator for monitoring:

- **Health**: Application and dependency health status
- **Info**: Application metadata and build information
- **Metrics**: JVM and application metrics

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆕 What's New in This Modern Version

- ✅ Upgraded to Java 21 LTS for better performance
- ✅ Modern Kotlin syntax with constructor injection
- ✅ Separated web and API controllers for better organization
- ✅ Added comprehensive test coverage
- ✅ Implemented Spring Boot Actuator for monitoring
- ✅ Created responsive Bootstrap 5.3.3 UI
- ✅ Added Docker support with multi-stage builds
- ✅ Fixed Bootstrap version inconsistencies
- ✅ Enhanced error handling and validation
- ✅ Added interactive features and API endpoints
- ✅ Time-based greetings (Good morning / Good afternoon / Good evening)
- ✅ Logging added to API requests with timestamp
- ✅ Implemented Internationalization (i18n) for multi-language support
- ✅ Added API Caching (@Cacheable) for greeting responses
- ✅ Implemented Greeting History with a new /api/hello/history endpoint
