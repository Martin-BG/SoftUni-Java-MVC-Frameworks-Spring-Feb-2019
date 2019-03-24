# Project: Resident Evil - Part II
[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/05.%20JavaScript%20and%20AJAX/05.%20JavaScript%20and%20AJAX%20-%20Exercise.pdf) 
is to extend [**Project: Resident Evil - Part I**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil)
by adding asynchronous requests with JavaScript and AJAX.
___
## Setup
Tested on Ubuntu 18.0.4
### Versions
* Java **11.0.2**
* Spring Boot **2.1.3.RELEASE**
### Other tools:
* [ModelMapper](http://modelmapper.org/)
* [Lombok](https://projectlombok.org/)

System and IDE should be configured to use:
* [Java **11.0.2**](https://docs.oracle.com/cd/E19509-01/820-3208/inst_cli_jdk_javahome_t/) - [IntelliJ](https://stackoverflow.com/questions/18987228/how-do-i-change-the-intellij-idea-default-jdk)
* [mysql-connector-java **8.0.15**](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-installing-classpath.html) - [IntelliJ](https://www.jetbrains.com/help/idea/connecting-to-a-database.html)

Configure IDE to recognize [Lombok](https://projectlombok.org/) - [instructions](https://projectlombok.org/setup/overview)
___
#### Project configuration
* [pom.xml](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/pom.xml)
* [application.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/resources/application.properties) - 
customized **[MySQL8UnicodeDialect](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil/src/main/java/org/softuni/residentevil/config/MySQL8UnicodeDialect.java)**, 
**logging**, messages settings, **compression**, static resources [**caching**](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#headers-cache-control) 
[**[1]**](https://stackoverflow.com/a/35640540/7598851)
[**[2]**](https://www.baeldung.com/cachable-static-assets-with-spring-mvc)
___
## Takeaways
* Everything from [Project: **Resident Evil Part I**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil)
* [Enum to JSON](https://www.baeldung.com/jackson-serialize-enums) format - 
put @JsonValue on getter which return value should be used in enum -> JSON convert.
```java
public enum Magnitude {

    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");
    // ...
    @JsonValue
    public String getLabel() {
        return label;
    }
}
```
* Date/Time to JSON format (any of these could be used) 
[**[1]**](https://stackoverflow.com/questions/29027475/date-format-in-the-json-output-using-spring-boot):
  * **@JsonFormat(pattern="....")** on getter/field:
    ```java
    public final class VirusSimpleViewModel implements Viewable<Virus>, Serializable {
        //...
        @JsonFormat(pattern="dd-MMM-yyyy")
        private Date releasedOn;
    }
    ```
  * [application.properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)
    ```properties
    spring.jackson.date-format=dd-MMM-yyyy
    ```
* Use application.properties values in Thymeleaf:
```properties
#Date format
resident.evil.date.format=dd-MMM-yyyy
spring.jackson.date-format=${resident.evil.date.format}
```
```html
<td th:text="${#dates.format(virus.getReleasedOn(), @environment.getProperty('resident.evil.date.format'))}"/>
```
