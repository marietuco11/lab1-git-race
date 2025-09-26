# Lab 1 Git Race -- Project Report

## Description of Changes
- Modified `/api/hello` endpoint to return greetings depending on the current time of day.
- Added structured logging for every API request, including name, greeting, and timestamp.
- Updated and extended unit tests to validate new behavior.
- Added in-memory greeting history stored in `HelloApiController`.
- Implemented new endpoint `/api/hello/history` to retrieve all greetings.
- Enabled caching with `@EnableCaching` in `Application.kt`.
- Annotated `/api/hello` endpoint with `@Cacheable("greetings")`.
- Implemented multi-languague support for the greetings.


## Technical Decisions
- Chose `LocalDateTime` and `LocalTime` for determining the greeting (morning/afternoon/evening).
- Used `LoggerFactory` to integrate with Spring Boot’s default logging system.
- Used Spring Boot's default ConcurrentMapCacheManager for in-memory caching.
- Chose annotation-based caching (`@Cacheable`) for simplicity and readability.


## Learning Outcomes
- Practiced extending an existing Spring Boot + Kotlin project.
- Understood how to return data classes as JSON automatically in Spring Boot.
- Learned how to adapt unit tests after refactoring API return types.
- Improved familiarity with documenting changes and good Git practices.

## AI Disclosure
### AI Tools Used
- ChatGPT
- Github Copilot

### AI-Assisted Work
 - [30% AI vs 70% not AI]
 - Debugging & Error Resolution: AI was used in order to debug and troubleshoot specific issues related to caching (@Cacheable configuration) and complex dependency injections in Kotlin.
 - Documentation Improvement: Used for the translation, grammar checking, and structural improvement of the final technical documentation (README.md and REPORT.md).
 - Code Snippets: Generated boilerplate code for the time-based greeting logic and the initial configuration for Spring i18n.

### Original Work
 - Core Feature Implementation: Designed and wrote the complete logic for the Greeting History feature, including the new API endpoint (/api/hello/history) and its state management.
 - Integration: Implemented the integration of i18n and caching into the existing controllers and services.
 - Testing: Developed the majority of the unit and integration tests to validate the new features, ensuring correct behavior for time-based logic,    caching, and history tracking.
 - The core learning focused on implementing Spring's caching mechanism (@Cacheable) for performance optimization and managing in-memory application state (Greeting History). This process provided a solid understanding of how to configure and utilize Internationalization (i18n) effectively within a Spring Boot application.