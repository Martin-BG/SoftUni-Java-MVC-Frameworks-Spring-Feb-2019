# Real Estate Agency 
[Simple](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/01.%20Spring%20Boot%20Introduction%20-%20Exercises.pdf) Spring Boot application using Spring Data.

[JavaScript](https://github.com/Martin-BG/SoftUni-JavaScript-Advanced/tree/master/12.%20Exam/04.%20Real%20Estate%20Agency) solution from JS Advanced exam
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
* [pom.xml](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/pom.xml)
* [application.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/resources/application.properties) - customized **[MySQL8UnicodeDialect](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/java/org/softuni/realestate/config/MySQL8UnicodeDialect.java)**
```
spring.jpa.properties.hibernate.dialect=org.softuni.realestate.config.MySQL8UnicodeDialect
```
```java
import org.hibernate.dialect.MySQL8Dialect;

public class MySQL8UnicodeDialect extends MySQL8Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
    }
}
```
___
## Takeaways
* [Customized MySQL dialect](https://stackoverflow.com/a/54993738/7598851) - changed default charset and collation 
* Entities and view models without setters (getters are optional too) - better encapsulation and less boilerplate code
* [Customized](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/java/org/softuni/realestate/config/ApplicationConfig.java) ModelMapper to support field mapping
```java
    @Bean
    ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
```
* [Optimized](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/java/org/softuni/realestate/domain/enities/BaseUuidEntity.java) UUID primary keys representation in database - use BINARY(16) type instead of VARCHAR(36)
```java
@Setter
@Getter
@MappedSuperclass
abstract class BaseUuidEntity implements Identifiable<UUID> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    @Access(AccessType.PROPERTY)
    private UUID id;
}
```
* Exported common repository work logic to [BaseService](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/01.%20Spring%20Boot%20Introduction/Exercises/Real%20Estate%20Agency/src/main/java/org/softuni/realestate/service/BaseService.java) class, using of generics for type-safety
 