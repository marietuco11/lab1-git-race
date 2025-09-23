# Lab 1 Git Race -- Project Report

## Description of Changes
- Modified `/api/hello` endpoint to return greetings depending on the current time of day.
- Added structured logging for every API request, including name, greeting, and timestamp.
- Updated and extended unit tests to validate new behavior.
- Added in-memory greeting history stored in `HelloApiController`.
- Implemented new endpoint `/api/hello/history` to retrieve all greetings.
- Extended tests to check that greetings are stored and retrieved correctly.
- Enabled caching with `@EnableCaching` in `Application.kt`.
- Annotated `/api/hello` endpoint with `@Cacheable("greetings")`.



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
- [List specific AI tools used]

### AI-Assisted Work
- [Describe what was generated with AI assistance]
- [Percentage of AI-assisted vs. original work]
- [Any modifications made to AI-generated code]

### Original Work
- [Describe work done without AI assistance]
- [Your understanding and learning process]