package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import java.util.*

class TestMessageSource : MessageSource {
    override fun getMessage(code: String, args: Array<out Any>?, defaultMessage: String?, locale: Locale): String {
        return try {
            getMessage(code, args, locale)
        } catch (ex: NoSuchMessageException) {
            defaultMessage ?: throw ex
        }
    }

    override fun getMessage(code: String, args: Array<out Any>?, locale: Locale): String {
        return when (locale.language) {
            "es" -> when (code) {
                "greeting.morning" -> "Buenos días"
                "greeting.afternoon" -> "Buenas tardes"
                "greeting.evening" -> "Buenas noches"
                else -> throw NoSuchMessageException(code, locale)
            }
            "fr" -> when (code) {
                "greeting.morning" -> "Bonjour"
                "greeting.afternoon" -> "Bon après-midi"
                "greeting.evening" -> "Bonsoir"
                else -> throw NoSuchMessageException(code, locale)
            }
            else -> when (code) {
                "greeting.morning" -> "Good morning"
                "greeting.afternoon" -> "Good afternoon"
                "greeting.evening" -> "Good evening"
                else -> throw NoSuchMessageException(code, locale)
            }
        }
    }

    override fun getMessage(resolvable: org.springframework.context.MessageSourceResolvable, locale: Locale): String {
        val codes = resolvable.codes
        if (codes != null) {
            for (code in codes) {
                try {
                    return getMessage(code, resolvable.arguments, locale)
                } catch (_: NoSuchMessageException) {
                }
            }
        }
        if (resolvable.defaultMessage != null) {
            return resolvable.defaultMessage!!
        }
        throw NoSuchMessageException(resolvable.codes?.firstOrNull() ?: "unknown", locale)
    }
}

class HelloControllerUnitTests {
    private lateinit var controller: HelloController
    private lateinit var model: Model
    
    @BeforeEach
    fun setup() {
        controller = HelloController("Test Message")
        model = ExtendedModelMap()
    }
    
    @Test
    fun `should return welcome view with default message`() {
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Test Message")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message`() {
        val view = controller.welcome(model, "Developer")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Hello, Developer!")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }
    
    @Test
    fun `should return API response with greeting and timestamp`() {
        val apiController = HelloApiController(TestMessageSource())
        val response = apiController.helloApi("Test", "en")
           
        assertThat(response.message).matches("(Good morning|Good afternoon|Good evening), Test!")
        assertThat(response.timestamp).isNotNull()
        assertThat(response.timestamp).isNotEmpty()
    }

    @Test
    fun `should store greetings in history`() {
        val apiController = HelloApiController(TestMessageSource())
        apiController.helloApi("Alice", "en")
        apiController.helloApi("Bob", "en")

        val history = apiController.getHistory()

        assertThat(history).hasSize(2)
        assertThat(history[0].message).endsWith(", Alice!")
        assertThat(history[1].message).endsWith(", Bob!")
    }

    @Test
    fun `should cache greetings for the same name`() {

        val apiController = HelloApiController(TestMessageSource())
        
        val first = apiController.helloApi("CacheTest", "en")
        val second = apiController.helloApi("CacheTest", "en")

        assertThat(first.message).isEqualTo(second.message)
        
        val history = apiController.getHistory()
        assertThat(history).hasSize(2)
        assertThat(history[0].message).isEqualTo(first.message)
        assertThat(history[1].message).isEqualTo(second.message)
    }

    @Test
    fun `should return greeting in Spanish`() {
        val apiController = HelloApiController(TestMessageSource())

        val response = apiController.helloApi("Ana", "es")

        assertThat(response.message).matches("(Buenos días|Buenas tardes|Buenas noches), Ana!")
        assertThat(response.message).contains("Ana")
    }

    @Test
    fun `should return greeting in French`() {
        val apiController = HelloApiController(TestMessageSource())

        val response = apiController.helloApi("Jean", "fr")

        assertThat(response.message).matches("(Bonjour|Bon après-midi|Bonsoir), Jean!")
        assertThat(response.message).contains("Jean")
    }
}