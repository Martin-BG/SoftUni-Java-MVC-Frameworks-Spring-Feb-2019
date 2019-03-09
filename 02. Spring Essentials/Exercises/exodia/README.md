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
* [application.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/02.%20Spring%20Essentials/Exercises/exodia/src/main/resources/application.properties) - customized **[MySQL8UnicodeDialect](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/java/org/softuni/realestate/config/MySQL8UnicodeDialect.java)**

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
