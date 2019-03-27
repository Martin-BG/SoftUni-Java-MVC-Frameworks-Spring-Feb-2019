# Java MVC Frameworks - Spring - Feb 2019
[**Java MVC Frameworks - Spring** course at SoftUni - February 2019](https://softuni.bg/trainings/2295/java-mvc-frameworks-spring-february-2019)

## Projects

### [**Real Estate Agency**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency)

[Simple](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/01.%20Spring%20Boot%20Introduction%20-%20Exercises.pdf) Spring Boot application using Spring Data.
* [Customized MySQL dialect](https://stackoverflow.com/a/54993738/7598851) - changed default charset and collation 
* [Optimized](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/java/org/softuni/realestate/domain/enities/BaseUuidEntity.java) UUID primary keys representation in database - use BINARY(16) type instead of VARCHAR(36)
___
### [**EXODIA**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/02.%20Spring%20Essentials/Exercises/exodia)

[Simple](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/02.%20Spring%20Essentials%20-%20Exercise.pdf) Spring Boot application, custom security, MD to PDF conversion and file download, Thymeleaf templating and fragments.
* Password hashing with [Jargon2](https://github.com/kosprov/jargon2-api)
* Thymeleaf [templating](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/resources/templates) and fragments
* Simple security by custom class/method [annotation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/annotations/AuthenticatedUser.java) and [interceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/AuthenticatedInterceptor.java)
* Convert MD to PDF format with [Markdown2Pdf](https://mvnrepository.com/artifact/eu.de-swaef.pdf/Markdown2Pdf)
* [PDF file](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/controllers/DocumentController.java) download
* Custom [@Layot](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/annotations/Layout.java) annotation and [Interceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/ThymeleafLayoutInterceptor.java) for templating and fragments insert
* Implemented Builder Pattern for [AuthenticatedInterceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/AuthenticatedInterceptor.java) and [ThymeleafLayoutInterceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/ThymeleafLayoutInterceptor.java):
* [**Composite**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/annotations/validation/composite) annotations for entity/model validation
* Use of **transactions** (read/write) for all public service methods to promote data integrity 
and as optimization for methods that make multiple DB calls (ex. [User registration](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/service/UserServiceImpl.java))
* Use **@SessionAttributes** for store and reuse of @ModelAttribute by [Controller](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/controllers/DocumentController.java), avoiding unnecessary Service/Repository calls for getting the same data
* Use **caching** on selected [service](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/service/DocumentServiceImpl.java) methods (@EnableCaching + @CacheConfig + @Cacheable / @CacheEvict)
___
### [Service Layer **Unit Tests** (Car Dealer)](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer)
[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/03.%20Unit%20Testing%20and%20Isolation%20-%20Exercise.pdf) is to write unit tests for CRUD-like [services](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/src/main/java/org/softuni/cardealer/service) with effectively no [service](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/src/main/java/org/softuni/cardealer/service/CarServiceImpl.java) logic in the given skeleton, so tests are based on 100% [mocking](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/src/test/java/org/softuni/cardealer/service/CarServiceTests.java) of service dependencies and evaluation of method calls, argument and return values.
* Mocking
___
### [**Project: Resident Evil**, Part I](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil)

[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/04.%20Thymeleaf%20and%20Controllers%20-%20Exercises.pdf) 
is to create a 5-page Spring Boot application with input forms, using Thymeleaf.

Project structure is similar to the one used in [EXODIA](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/02.%20Spring%20Essentials/Exercises/exodia), 
but greatly extended and improved by applying best practices and new techniques:
* Base Entity class equals and hashCode methods implemented as recommended by [Vlad Mihalcea](https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate)
* Custom ENUM mapping in entities by [converters](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/domain/converters)
* Custom HQL queries with direct mapping to view models to avoid unnecessary data pooling
* Simplified [controllers](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/web/controllers) 
by moving of common logic to parent classes and using of Spring's goodies like @SessionAttributes
* Use of [converters](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/web/converters) 
for mapping of custom types between Spring controllers and Thymeleaf
* Use text messages from external [messages.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/resources/languages/messages.properties) file
* Use validation messages from external [validation.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/resources/languages/validation.properties) file
* Define validation constraints as constants into [composite validation annotations](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/domain/validation/annotations/composite) 
and use these for setting up entities columns, ensuring integrity between actual validation and DB constraints.
* Trim string values from input forms with **@InitBinder** and **StringTrimmerEditor**.
Further [customize](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/web/controllers/BaseController.java) 
this functionality to prevent trimming of certain fields like **"password"** 
or treating empty texts as null, by protected methods allowing redefinition of the rules by child classes.
* Use **Javadoc** whenever it is appropriate to ([config files](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/config/ApplicationConfig.java), 
[protected methods](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/web/controllers/BaseController.java), 
[base classes](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/web/controllers/BaseController.java), 
[interfaces](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/domain/api/Bindable.java) 
etc.)
* Responce compression and static resources caching [enabled](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/resources/application.properties)
* [Generic repository](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/repository/GenericRepository.java) - 
provides generic methods for work with [projections](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections)
___
### [**Project: Resident Evil**, Part II](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/05.%20JavaScript%20and%20AJAX/Exercise/Resident%20Evil%20Part%20II)

[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/05.%20JavaScript%20and%20AJAX/05.%20JavaScript%20and%20AJAX%20-%20Exercise.pdf) 
is to extend [**Project: Resident Evil - Part I**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil)
by adding asynchronous requests with JavaScript and AJAX.

Build over a copy of [Project: Resident Evil Part I](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil)
___
### [**Project: Resident Evil**, Part III](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III)

[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%2C%20Interceptors%20and%20User%20Authentication/06.%20Filters%2C%20Interceptors%20and%20User%20Authentication%20-%20Exercises.pdf) 
is to extend [**Project: Resident Evil - Part II**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/05.%20JavaScript%20and%20AJAX/Exercise/Resident%20Evil%20Part%20II)
by implementing **Spring Security**.

Build over a copy of [Project: Resident Evil Part I](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil) && [II](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/05.%20JavaScript%20and%20AJAX/Exercise/Resident%20Evil%20Part%20II):
* **Spring Security** [configuration](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/config/WebSecurityConfig.java)
* GrantedAuthority [implementation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/domain/entities/Role.java)
* UserDetails [implementation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/domain/entities/User.java),
lazy authorities initialization thanks to a named query
* UserDetailsService [implementation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/service/UserServiceImpl.java)
* **Remember Me** functionality
* **CSRF** with [Thymeleaf](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/resources/templates/fragments/head.html) 
and [AJAX](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/resources/templates/views/common/all.html) 
requests
