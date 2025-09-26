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
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.MessageSource
import java.util.Locale

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
class HelloApiController(private val messageSource: MessageSource) {

    private val log = LoggerFactory.getLogger(HelloApiController::class.java)
    private val history = mutableListOf<HelloResponse>()

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
    @Cacheable("greetings")
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(
        @RequestParam(defaultValue = "World") name: String,
        @RequestParam(defaultValue = "en") lang: String
    ): HelloResponse {
        val now = LocalDateTime.now()
        val time = now.toLocalTime()
        val locale = Locale(lang)

        val key = when {
            time.isBefore(LocalTime.NOON) -> "greeting.morning"
            time.isBefore(LocalTime.of(18, 0)) -> "greeting.afternoon"
            else -> "greeting.evening"
        }

        val greeting = messageSource.getMessage(key, null, locale)
        val message = "$greeting, $name!"
        val timestamp = now.format(DateTimeFormatter.ISO_DATE_TIME)

        val response = HelloResponse(message, timestamp)

        // Structured log for debugging and tracing
        log.info("GET /api/hello name={} lang={} greeting={} timestamp={}", name, lang, greeting, timestamp)

        history.add(response)
        return response
    }

    /**
     * REST API endpoint at "/api/hello/history"
     *
     * Returns the full list of greetings made since the application started.
     */
    @GetMapping("/api/hello/history", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getHistory(): List<HelloResponse> {
        log.info("GET /api/hello/history size={}", history.size)
        return history
    }
}

/**
 * Data Transfer Object (DTO) for the /api/hello JSON response.
 */
data class HelloResponse(
    val message: String,
    val timestamp: String
)