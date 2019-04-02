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
* [pom.xml](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/pom.xml)
* [application.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/resources/application.properties) - 
customized **[MySQL8UnicodeDialect](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/config/MySQL8UnicodeDialect.java)**, 
**logging**, messages settings, **compression**, static resources [**caching**](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#headers-cache-control) 
[**[1]**](https://stackoverflow.com/a/35640540/7598851)
[**[2]**](https://www.baeldung.com/cachable-static-assets-with-spring-mvc)
___
## Takeaways
* Everything from **Project: Resident Evil** 
[**Part I**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/04.%20Thymeleaf%20and%20Controllers/Exercise/Resident%20Evil) 
and
[**Part II**](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/05.%20JavaScript%20and%20AJAX/Exercise/Resident%20Evil%20Part%20II)
* Spring Security [configuration](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/config/WebSecurityConfig.java)
* GrantedAuthority [implementation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/domain/entities/Role.java):
```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseLongEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @ValidRoleEntityAuthority
    @Column(unique = true, nullable = false, length = ValidRoleEntityAuthority.MAX_LENGTH)
    private String authority;
}
```
* UserDetails [implementation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/domain/entities/User.java),
lazy authorities initialization thanks to a named query:
```java
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findUserEager",
        query = "SELECT u FROM User u LEFT JOIN FETCH u.authorities AS a WHERE u.username = :username")
public class User extends BaseUuidEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @ValidUserUsername
    @Column(unique = true, nullable = false, updatable = false, length = ValidUserUsername.MAX_LENGTH)
    private String username;

    @ValidUserEncryptedPassword
    @Column(nullable = false, length = ValidUserEncryptedPassword.MAX_LENGTH)
    private String password;

    @ValidUserEmail
    @Column(unique = true, nullable = false, length = ValidUserEmail.MAX_LENGTH)
    private String email;

    @ValidUserEntityAuthorities
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> authorities = new HashSet<>();

    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
}
```
* UserDetailsService [implementation](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/service/UserServiceImpl.java):
```java
public interface UserService extends Service<User, UUID>, UserDetailsService {
    // ..
}

@Log
@Service("userDetailsService")
@Validated
@Transactional
@CacheConfig(cacheNames = UserServiceImpl.USERS)
public class UserServiceImpl extends BaseService<User, UUID, UserRepository> implements UserService {
    //..
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = USERS, key = "#username")
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findUserEager(username)
                .orElseThrow(() -> USERNAME_NOT_FOUND_EXCEPTION);
    }
}
```
* [Named queries](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/domain/entities/User.java) 
can be used directly as [repository methods](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/repository/UserRepository.java):
```java
@Table(name = "users")
@NamedQuery(name = "User.findUserEager",
        query = "SELECT u FROM User u LEFT JOIN FETCH u.authorities AS a WHERE u.username = :username")
public class User extends BaseUuidEntity implements UserDetails {
    //..
}

@Validated
@Repository
public interface UserRepository extends GenericRepository<User, UUID> {

    Optional<User> findUserEager(@ValidUserUsername String username);
    
    //...
}
```
* **Remember Me** functionality:
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int REMEMBER_ME_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30; // 30 Days

    private final UserDetailsService userService;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsService") UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
            .rememberMe()
                .userDetailsService(userService)
                .tokenValiditySeconds(REMEMBER_ME_TOKEN_VALIDITY_SECONDS);
                //..
    }
    //..
}

public interface UserService extends Service<User, UUID>, UserDetailsService {
    // ..
}

@Log
@Service("userDetailsService")
@Validated
@Transactional
@CacheConfig(cacheNames = UserServiceImpl.USERS)
public class UserServiceImpl extends BaseService<User, UUID, UserRepository> implements UserService {
    //..
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = USERS, key = "#username")
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findUserEager(username)
                .orElseThrow(() -> USERNAME_NOT_FOUND_EXCEPTION);
    }
}
```
```html
    <form class="mt-5 center-block w-75 mx-auto"
          th:action="@{__${T(org.softuni.residentevil.config.WebConfig).URL_USER_LOGIN}__}"
          th:method="post">
        <!--...-->
        <div class="form-group">
            <input id="remember_me" name="remember-me" type="checkbox"/>
            <label class="inline" for="remember_me" th:text="#{user.login-form.remember-me}"/>
        </div>

        <div class="form-actions mx-auto text-center mt-4">
            <button class="btn re-color my-button" th:text="#{user.login-form.button}" type="submit"/>
        </div>
    </form>
```
* CSRF with [Thymeleaf](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/resources/templates/fragments/head.html) 
and [AJAX](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/resources/templates/views/common/all.html) 
requests:
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String CSRF_ATTRIBUTE_NAME = "_csrf";
    //..
    private static CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName(CSRF_ATTRIBUTE_NAME);
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and();
        //..
    }
}
```
```html
    <!--get values in JS-->
        window.onload = () => {
            csrfToken = /*[[${__${T(org.softuni.residentevil.config.WebSecurityConfig).CSRF_ATTRIBUTE_NAME}__.token}]]*/"token";
            csrfHeader = /*[[${__${T(org.softuni.residentevil.config.WebSecurityConfig).CSRF_ATTRIBUTE_NAME}__.parameterName}]]*/"header";
            $('#virusesRadio').on("click", loadViruses);
            $('#virusesRadioModerator').on("click", loadVirusesModerator);
            $('#capitalsRadio').on("click", loadCapitals);
        };
    
    
    <!--use to create dynamic content-->
    <form class="d-inline mr-3"
          action=[[${T(org.softuni.residentevil.config.WebConfig).URL_VIRUS_DELETE}]]
          method="post">
        <input hidden name="id" value="${virus.id}">
        <input type="hidden" name="${csrfHeader}" value="${csrfToken}"/>
        <button class="btn btn-light btn-sm border-dark table-button" type="submit">
                [(#{viruses.all.table-button.delete})]
         </button>
    </form>
```
* Custom class-type validation annotation [@EqualFields](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/domain/validation/annotations/custom/EqualFields.java):
```java
//..
@EqualFields(message = "{user.password.not-match}", fields = {"password", "confirmPassword"})
public class UserRegisterBindingModel implements Bindable<User>, Serializable {
    //..
    
    @ValidUserPassword
    private String password;

    @ValidUserPassword
    private String confirmPassword;
}
```
* Trim all text input fields except for password/confirmPassword. 
Default behaviour is defined in [BaseController](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/web/controllers/BaseController.java) 
and configured as needed in child classes 
[[1]](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/web/controllers/user/RegisterUserController.java)
[[2]](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/web/controllers/user/LoginUserController.java):
```java
@Log
public class BaseController {

    // .. Javadoc omitted too
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(isEmptyInputStringsNull()));

        preventTextModificationForFields(binder, getUnmodifiedTextFieldsList());
    }

    protected List<String> getUnmodifiedTextFieldsList() {
        return List.of();
    }
}

@Layout
@Controller
@RequestMapping(WebConfig.URL_USER_REGISTER)
public class RegisterUserController extends BaseController {

    //..

    @Override
    protected List<String> getUnmodifiedTextFieldsList() {
        return List.of("password", "confirmPassword");
    }
}
```
* Custom [AccessDeniedHandler](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/06.%20Filters%20and%20User%20Authentication/Exercise/Resident%20Evil%20Part%20III/src/main/java/org/softuni/residentevil/web/handlers/AccessDeniedHandlerImpl.java) 
implementation with proper redirect and HTTP status code:
```java
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //...
        response.sendRedirect(request.getContextPath() + WebConfig.URL_ERROR_UNAUTHORIZED);
    }
}

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //..
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //..
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }
}

@Layout
@Controller
public class UnauthorizedController extends BaseController {

    private static final String VIEW_ERROR_UNAUTHORIZED = "error/unauthorized";

    @GetMapping(WebConfig.URL_ERROR_UNAUTHORIZED)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String get() {
        return VIEW_ERROR_UNAUTHORIZED;
    }
}
```
___
## Notes to myself
* [Disable](https://www.gamefromscratch.com/post/2015/02/01/Preventing-IntelliJ-code-auto-formatting-from-ruining-your-day.aspx) 
code formatting in IntelliJ on places where it fails:
```java
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //...
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .cors()
                .disable()
            .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**")
                    .permitAll()
                .antMatchers(WebConfig.URL_INDEX)
                    .permitAll()
                .antMatchers(WebConfig.URL_USER_REGISTER, WebConfig.URL_USER_LOGIN)
                    .anonymous()
                .antMatchers(WebConfig.URL_USER_ALL)
                    .hasAuthority(Authority.ADMIN.role())
                .antMatchers(WebConfig.URL_VIRUS_EDIT, WebConfig.URL_VIRUS_DELETE, WebConfig.URL_VIRUS_ADD)
                    .hasAuthority(Authority.MODERATOR.role())
                .anyRequest()
                    .authenticated()
                .and()
            .formLogin()
                .loginPage(WebConfig.URL_USER_LOGIN)
                .defaultSuccessUrl(WebConfig.URL_USER_HOME)
                .and()
            .rememberMe()
                .userDetailsService(userService)
                .tokenValiditySeconds(REMEMBER_ME_TOKEN_VALIDITY_SECONDS)
                .key(REMEMBER_ME_KEY)
                .rememberMeCookieName(REMEMBER_ME_COOKIE)
                .and()
            .logout()
                .logoutUrl(WebConfig.URL_USER_LOGOUT)
                .deleteCookies(SESSION_COOKIE, REMEMBER_ME_COOKIE)
                .logoutSuccessUrl(WebConfig.URL_USER_LOGIN + "?logout")
                .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
            .and()
                .sessionManagement()
                .invalidSessionUrl(WebConfig.URL_USER_LOGIN + "?expired");
        // @formatter:on
    }
}
```
