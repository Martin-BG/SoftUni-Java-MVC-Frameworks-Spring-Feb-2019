# EXODIA
[Simple](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/02.%20Spring%20Essentials%20-%20Exercise.pdf) Spring Boot application, custom security, MD to PDF conversion and file download, Thymeleaf templating and fragments

[Solution with JSF](https://github.com/Martin-BG/SoftUni-Java-Web-Development-Basics-Jan-2019/blob/master/12.%20Exam/Final%20Exam/README.md) - Java Web Basics final exam
___
## Setup
Tested on Ubuntu 18.0.4
### Versions
* Java **11.0.2**
* Spring Boot **2.1.3.RELEASE**
### Other tools:
* [ModelMapper](http://modelmapper.org/)
* [Lombok](https://projectlombok.org/)
* [Jargon2](https://github.com/kosprov/jargon2-api) - Fluent Java API for Argon2 password hashing
* [Markdown2Pdf](https://mvnrepository.com/artifact/eu.de-swaef.pdf/Markdown2Pdf) - .md to .pdf converter

System and IDE should be configured to use:
* [Java **11.0.2**](https://docs.oracle.com/cd/E19509-01/820-3208/inst_cli_jdk_javahome_t/) - [IntelliJ](https://stackoverflow.com/questions/18987228/how-do-i-change-the-intellij-idea-default-jdk)
* [mysql-connector-java **8.0.15**](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-installing-classpath.html) - [IntelliJ](https://www.jetbrains.com/help/idea/connecting-to-a-database.html)

Configure IDE to recognize [Lombok](https://projectlombok.org/) - [instructions](https://projectlombok.org/setup/overview)
___
#### Project configuration
* [pom.xml](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/pom.xml)
* [application.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/resources/application.properties) - customized **[MySQL8UnicodeDialect](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/java/org/softuni/realestate/config/MySQL8UnicodeDialect.java)**, **logging**

___
## Takeaways
* Project structure based on [Real Estate Agency](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency) project
* [Customized MySQL dialect](https://stackoverflow.com/a/54993738/7598851) - changed default charset and collation 
* Entities and view models without setters (getters are optional too) - better encapsulation and less boilerplate code
* [Customized](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/config/ApplicationConfig.java) ModelMapper to support field mapping
* [Optimized](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/domain/entities/BaseUuidEntity.java) UUID primary keys representation in database - use BINARY(16) type instead of VARCHAR(36)
* Exported common repository work logic to [BaseService](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/service/BaseService.java) class, using of generics for type-safety
* Password hashing with [Jargon2](https://github.com/kosprov/jargon2-api)
* Thymeleaf [templating](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/resources/templates) and fragments
```html
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head th:insert="~{fragments/head :: head}"></head>
<body>
<div class="container-fluid">
    <header th:insert="~{fragments/header :: header}"></header>
    <main class="mt-3" th:insert="${view}"></main>
    <footer th:insert="~{fragments/footer :: footer}"></footer>
</div>
<th:block th:replace="fragments/scripts :: scripts"/>
</body>
</html>
```
* Custom [@Layot](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/annotations/Layout.java) annotation and [Interceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/ThymeleafLayoutInterceptor.java) for templating and fragments insert:
```html
<!--Sample layout (resources/templates/layouts/default.html):-->
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>...</head>
<body>
    <header>...</header>
    <main th:insert="${view}"></main>
    <footer>...</footer>
</body>
</html>

<!--Sample view (resources/templates/views/hello.html):-->
<th:block xmlns:th="http://www.thymeleaf.org">
    <h1>Hello World!</h1>
</th:block>
```
Sample controller method:
```java
@Controller
class HomeController {
    @Layout
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
```
* Simple security by custom class/method [annotation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/annotations/AuthenticatedUser.java) and [interceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/AuthenticatedInterceptor.java)
```java
@Controller
@AuthenticatedUser // Only authenticated users are allowed to access methods in this controller
public class DocumentController extends BaseController {
    //...
}
```
* Convert MD to PDF format with [Markdown2Pdf](https://mvnrepository.com/artifact/eu.de-swaef.pdf/Markdown2Pdf)
* [PDF file](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/controllers/DocumentController.java) download
* Implemented Builder Pattern for [AuthenticatedInterceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/AuthenticatedInterceptor.java) and [ThymeleafLayoutInterceptor](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/interceptors/ThymeleafLayoutInterceptor.java):

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //...

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                AuthenticatedInterceptor
                        .builder()
                        .withSessionAttributeName(SESSION_ATTRIBUTE_USERNAME)
                        .withAuthenticatedRedirectUrl(URL_INDEX)
                        .withGuestRedirectUrl(URL_LOGIN)
                        .build());

        registry.addInterceptor(
                ThymeleafLayoutInterceptor
                        .builder()
                        .withDefaultLayout("/layouts/default")
                        .withViewAttribute("view")
                        .withViewPrefix("/views/")
                        .build());
    }
}
```
* [Composite](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/annotations/validation/composite) annotations for entity/model validation
```java
@NotBlank
@Size(min = 1, max = 255)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ValidDocumentTitle {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

@Entity
@Table(name = "documents")
public class Document extends BaseUuidEntity {

    @ValidDocumentTitle
    @Column(nullable = false)
    private String title;
    
    //...
}

public class DocumentScheduleBindingModel implements Bindable<Document> {

    @ValidDocumentTitle
    private String title;

    //...
}
```
* Validate parameters on repository methods to filter-out invalid requests
```java
@Validated
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(@ValidUserUsername String username);

    long countAllByUsernameEquals(@ValidUserUsername String username);

    long countAllByEmailEquals(@ValidUserEmail String email);
}

@Configuration
public class ApplicationConfig {
    // This @Bean could be necessary depending on project setup
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
```
* Use of transactions (read/write) for all public service methods to promote data integrity 
and as optimization for methods that make multiple DB calls (ex. [User registration](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/service/UserServiceImpl.java)):
```java
@Log
@Service
@Transactional // applied to all public methods in this class, by default "readOnly = false"
public class UserServiceImpl extends BaseService<User, UUID, UserRepository> implements UserService {

    @Override // Transactional, readOnly = false
    public boolean register(UserRegisterBindingModel bindingModel) {
        if (!validator.validate(bindingModel).isEmpty()) {
            log.log(Level.WARNING, "[User Registration failed] Constraint violations detected");
            return false;
        }

        if (repository.countAllByUsernameEquals(bindingModel.getUsername()) > 0) {
            log.log(Level.WARNING, "[User Registration failed] Username already used: " + bindingModel.getUsername());
            return false;
        }

        if (repository.countAllByEmailEquals(bindingModel.getEmail()) > 0) {
            log.log(Level.WARNING, "[User Registration failed] Email already used: " + bindingModel.getEmail());
            return false;
        }

        UserHashedPasswordBindingModel user = mapper.map(bindingModel, UserHashedPasswordBindingModel.class);
        String encodedPassword = passwordHasher.encodedHash(bindingModel.getPassword().toCharArray());
        user.setPassword(encodedPassword);

        return create(user);
    }

    @Override
    @Transactional(readOnly = true) // readOnly = true : optimization hint for methods that do not modify DB
    public <V extends Viewable<User>> Optional<V> findByUsername(String username, Class<V> viewModelClass) {
        return repository
                .findUserByUsername(username)
                .map(user -> mapper.map(user, viewModelClass));
    }
}
```
* Use @SessionAttributes for store and reuse of @ModelAttribute by [Controller](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/web/controllers/DocumentController.java), 
avoiding unnecessary Service/Repository calls for getting the same data
```java
@Controller
@SessionAttributes(DocumentController.DOCUMENT) // Define @ModelAttribute name that should be saved to session
public class DocumentController extends BaseController {

    public static final String DOCUMENT = "document";

    @ModelAttribute(DOCUMENT) // Creates DocumentDetailsViewModel instance if not present in session
    public DocumentDetailsViewModel documentDetailsViewModel() {
        return new DocumentDetailsViewModel();
    }

    @PostMapping(WebConfig.URL_SCHEDULE)
    public String schedulePost(@ModelAttribute DocumentScheduleBindingModel bindingModel,
                               @ModelAttribute(name = DOCUMENT, binding = false) DocumentDetailsViewModel document,
                               Model model) {
        // ..
        model.addAttribute(DOCUMENT, doc); // Create new document and store its view model
    }

    @GetMapping(WebConfig.URL_DETAILS + "/{id}")
    public String details(@PathVariable String id,
                          @ModelAttribute(name = DOCUMENT, binding = false) DocumentDetailsViewModel document,
                          Model model) {
        // Use saved session attribute or update it if another document is required (ID) 
    }

    @GetMapping(WebConfig.URL_PRINT + "/{id}")
    public String print(@PathVariable String id,
                        @ModelAttribute(name = DOCUMENT, binding = false) DocumentDetailsViewModel document,
                        Model model) {
        // Use saved session attribute or update it if another document is required (ID) 
    }

    @PostMapping(WebConfig.URL_PRINT + "/{id}")
    public String printPost(@PathVariable String id, SessionStatus sessionStatus) {
        // ..
        sessionStatus.setComplete(); // Remove stored session attribute as it is no longer needed
    }
    //..
}
```
* Use **caching** on selected [service](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/java/org/softuni/exodia/service/DocumentServiceImpl.java) methods (@EnableCaching + @CacheConfig + @Cacheable / @CacheEvict)
```java
@Configuration
@EnableCaching // enable caching
public class ApplicationConfig {
    //..
}

@Service
@Transactional
@CacheConfig(cacheNames = "documents") // set default cache name
public class DocumentServiceImpl extends BaseService<Document, UUID, DocumentRepository> implements DocumentService {

    //...
    @Override
    @CacheEvict(allEntries = true) // clear cache
    public Optional<DocumentDetailsViewModel> schedule(DocumentScheduleBindingModel bindingModel) {
        //..
    }

    @Override
    @CacheEvict(allEntries = true) // clear cache 
    public boolean print(String id) {
        //..
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(sync = true) // save result into cache, prevent simultaneous calls until cache is populated 
    public List<DocumentTitleAndIdViewModel> findAllShortView() {
        //..
    }
}
```
