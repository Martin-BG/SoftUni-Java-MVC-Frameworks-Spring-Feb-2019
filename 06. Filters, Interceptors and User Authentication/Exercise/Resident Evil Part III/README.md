# Project: Resident Evil - Part III
[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%2C%20Interceptors%20and%20User%20Authentication/06.%20Filters%2C%20Interceptors%20and%20User%20Authentication%20-%20Exercises.pdf) 
is to extend [**Project: Resident Evil - Part II**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/05.%20JavaScript%20and%20AJAX/Exercise/Resident%20Evil%20Part%20II)
by implementing **Spring Security**.
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
* [pom.xml](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%2C%20Interceptors%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/pom.xml)
* [application.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%2C%20Interceptors%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/resources/application.properties) - 
customized **[MySQL8UnicodeDialect](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%2C%20Interceptors%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/config/MySQL8UnicodeDialect.java)**, 
**logging**, messages settings, **compression**, static resources [**caching**](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#headers-cache-control) 
[**[1]**](https://stackoverflow.com/a/35640540/7598851)
[**[2]**](https://www.baeldung.com/cachable-static-assets-with-spring-mvc)
___
## Takeaways
* Everything from **Project: Resident Evil** 
[**Part I**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil) 
and
[**Part II**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/05.%20JavaScript%20and%20AJAX/Exercise/Resident%20Evil%20Part%20II)
