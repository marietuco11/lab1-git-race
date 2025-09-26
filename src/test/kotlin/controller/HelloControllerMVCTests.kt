package es.unizar.webeng.hello.controller

import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.context.MessageSource
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.assertj.core.api.Assertions.assertThat


@TestConfiguration
@EnableCaching
class TestMessageSourceConfig {
    @Bean
    @Primary
    fun messageSource(): MessageSource {
        return TestMessageSource()
    }
    
    @Bean
    @Primary
    fun cacheManager(): org.springframework.cache.CacheManager {
        return org.springframework.cache.concurrent.ConcurrentMapCacheManager("greetings")
    }
}

@WebMvcTest(HelloController::class, HelloApiController::class)
@Import(TestMessageSourceConfig::class)
class HelloControllerMVCTests {

    @Value("\${app.message:Hello World}")
    private lateinit var message: String

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return home page with default message`() {
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", equalTo(message)))
            .andExpect(model().attribute("name", equalTo("")))
    }

    @Test
    fun `should return home page with personalized message`() {
        mockMvc.perform(get("/").param("name", "Developer"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", equalTo("Hello, Developer!")))
            .andExpect(model().attribute("name", equalTo("Developer")))
    }

    @Test
    fun `should return API response as JSON`() {
        mockMvc.perform(get("/api/hello").param("name", "Test"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(Matchers.containsString("Test")))

            .andExpect(jsonPath("$.message").value(Matchers.anyOf(
                Matchers.containsString("Good morning"),
                Matchers.containsString("Good afternoon"), 
                Matchers.containsString("Good evening")
            )))
            .andExpect(jsonPath("$.timestamp").exists())
    }

    @Test
    fun `should return cached response for the same name`() {

        val result1 = mockMvc.perform(get("/api/hello").param("name", "CacheUser").param("lang", "en"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()

        val body1 = result1.response.contentAsString

 
        val result2 = mockMvc.perform(get("/api/hello").param("name", "CacheUser").param("lang", "en"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            
        val body2 = result2.response.contentAsString
        
        assertThat(body1).isEqualTo(body2)
    }

    @Test
    fun `should return greeting in Spanish via API`() {
        mockMvc.perform(get("/api/hello").param("name", "Ana").param("lang", "es"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.message").value(Matchers.anyOf(
                Matchers.containsString("Buenos días"),
                Matchers.containsString("Buenas tardes"),
                Matchers.containsString("Buenas noches")
            )))
            .andExpect(jsonPath("$.message").value(Matchers.containsString("Ana")))
    }

    @Test
    fun `should return greeting in French via API`() {
        mockMvc.perform(get("/api/hello").param("name", "Jean").param("lang", "fr"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            .andExpect(jsonPath("$.message").value(Matchers.anyOf(
                Matchers.containsString("Bonjour"),
                Matchers.containsString("Bon après-midi"),
                Matchers.containsString("Bonsoir")
            )))
            .andExpect(jsonPath("$.message").value(Matchers.containsString("Jean")))
    }
}