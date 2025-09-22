package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Controller
class HelloController(
    @param:Value("\${app.message:Hello World}") 
    private val message: String
) {
    
    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val greeting = if (name.isNotBlank()) "Hello, $name!" else message
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        return "welcome"
    }
}

@RestController
class HelloApiController {

    private val log = LoggerFactory.getLogger(HelloApiController::class.java)

    /**
     * REST API endpoint at "/api/hello"
     *
     * Returns a JSON object with a time-based greeting and an ISO timestamp.
     * The greeting changes depending on the current hour:
     * - Good morning before 12:00
     * - Good afternoon before 18:00
     * - Good evening afterwards
     *
     */
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): HelloResponse {
        val now = LocalDateTime.now()
        val time = now.toLocalTime()

        val greeting = when {
            time.isBefore(LocalTime.NOON) -> "Good morning"
            time.isBefore(LocalTime.of(18, 0)) -> "Good afternoon"
            else -> "Good evening"
        }

        val message = "$greeting, $name!"
        val timestamp = now.format(DateTimeFormatter.ISO_DATE_TIME)

        // Structured log for debugging and tracing
        log.info("GET /api/hello name={} greeting={} timestamp={}", name, greeting, timestamp)

        return HelloResponse(message, timestamp)
    }
}

/**
 * Data Transfer Object (DTO) for the /api/hello JSON response.
 */
data class HelloResponse(
    val message: String,
    val timestamp: String
)