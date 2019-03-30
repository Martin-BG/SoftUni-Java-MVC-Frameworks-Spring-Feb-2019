# Web Layer Integration Tests (Car Dealer)
[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/07.%20Integration%20Testing/07.%20Integration%20Testing%20-%20Exercise.pdf) 
is to write Web layer 
[Integration tests](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/07.%20Integration%20Testing/Exercise/Car%20Dealer/src/test/java/org/softuni/cardealer/web/controllers) 
of the given application.
___
## Setup
Tested on Ubuntu 18.0.4
### Versions
* Java **11.0.2**
___
## Takeaways
* Spring MVC mock
* H2 in-memory database use
* REST API [test](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/07.%20Integration%20Testing/Exercise/Car%20Dealer/src/test/java/org/softuni/cardealer/web/controllers/SuppliersControllerTest.java)
```java
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UsersControllerTest {

    private static final String URL_USERS_BASE = "/users";
    private static final String URL_USERS_LOGIN = URL_USERS_BASE + "/login";
    private static final String URL_USERS_REGISTER = URL_USERS_BASE + "/register";

    private static final String VIEW_LOGIN = "login";
    private static final String VIEW_REGISTER = "register";

    private static final String USER_USERNAME = "username";
    private static final String USER_EMAIL = "mail@email.com";
    private static final String USER_PASSWORD = "password";
    private static final String USER_WRONG_CONFIRM_PASSWORD = "another password";

    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";
    private static final String PARAM_EMAIL = "email";

    private final static MockHttpServletRequestBuilder POST_USER_VALID_DATA = post(URL_USERS_REGISTER)
            .param(PARAM_USERNAME, USER_USERNAME)
            .param(PARAM_PASSWORD, USER_PASSWORD)
            .param(PARAM_CONFIRM_PASSWORD, USER_PASSWORD)
            .param(PARAM_EMAIL, USER_EMAIL);

    private final static MockHttpServletRequestBuilder POST_USER_WRONG_CONFIRM_PASSWORD = post(URL_USERS_REGISTER)
            .param(PARAM_USERNAME, USER_USERNAME)
            .param(PARAM_PASSWORD, USER_PASSWORD)
            .param(PARAM_CONFIRM_PASSWORD, USER_WRONG_CONFIRM_PASSWORD)
            .param(PARAM_EMAIL, USER_EMAIL);

    private static final String EMPTY_VALUE = "";
    private final static MockHttpServletRequestBuilder POST_USER_EMPTY_FIELDS = post(URL_USERS_REGISTER)
            .param(PARAM_USERNAME, EMPTY_VALUE)
            .param(PARAM_PASSWORD, EMPTY_VALUE)
            .param(PARAM_CONFIRM_PASSWORD, EMPTY_VALUE)
            .param(PARAM_EMAIL, EMPTY_VALUE);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @WithAnonymousUser
    public void login_withAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(get(URL_USERS_LOGIN))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_LOGIN));
    }

    @Test
    @WithMockUser
    public void login_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(get(URL_USERS_LOGIN))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void register_get_withAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        mockMvc.perform(get(URL_USERS_REGISTER))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_REGISTER));
    }

    @Test
    @WithMockUser
    public void register_get_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(get(URL_USERS_REGISTER))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void register_post_withAuthenticatedUser_isForbidden() throws Exception {
        mockMvc.perform(post(URL_USERS_REGISTER))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void register_post_validDataWithAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        // This test exposes a flaw in controller redirect logic:
        // use of relative from current url (/user/register) redirect to "login",
        // instead of using absolute url ("/users/login")

        mockMvc.perform(POST_USER_VALID_DATA)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(URL_USERS_LOGIN));
    }

    @Test
    @WithAnonymousUser
    public void register_post_validDataWithAnonymousUser_registersNewUser() throws Exception {
        mockMvc.perform(POST_USER_VALID_DATA);

        User user = userRepository.findByUsername(USER_USERNAME).orElseThrow();

        assertEquals("Incorrect username", USER_USERNAME, user.getUsername());
        assertEquals("Incorrect email", USER_EMAIL, user.getEmail());
        assertTrue("Incorrect password", passwordEncoder.matches(USER_PASSWORD, user.getPassword()));
    }

    @Test(expected = Exception.class)
    @WithAnonymousUser
    public void register_post_duplicateUsernameWithAnonymousUser_throwsException() throws Exception {
        // Currently NestedServletException is thrown because of missing validation logic or custom exception handling.
        // This is FAR from optimal behaviour.
        // org.softuni.cardealer.service.UserServiceImpl.java:43

        mockMvc.perform(POST_USER_VALID_DATA);
        mockMvc.perform(POST_USER_VALID_DATA);
    }

    @Test(expected = Exception.class)
    @WithAnonymousUser
    public void register_post_invalidConfirmPasswordWithAnonymousUser_throwsException() throws Exception {
        // This test exposes a bug in controller/service logic
        // as password is never checked against confirmPassword

        mockMvc.perform(POST_USER_WRONG_CONFIRM_PASSWORD);
    }

    @Test(expected = Exception.class)
    @WithAnonymousUser
    public void register_post_emptyFieldsWithAnonymousUser_throwsException() throws Exception {
        // This test exposes a bug in controller/service logic
        // as input data is not validated at all (empty fields accepted)

        mockMvc.perform(POST_USER_EMPTY_FIELDS);
    }
}
```
___
## Useful resources
* [Spring Integration Testing](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/testing.html#integration-testing)
* [Spring Security Testing](https://docs.spring.io/autorepo/docs/spring-security/current/reference/html/test.html)
* [Spring Security Test: Web Security](https://spring.io/blog/2014/05/23/preview-spring-security-test-web-security)
* [Spring MVC Test Tutorial](https://www.petrikainulainen.net/spring-mvc-test-tutorial/)
  * [Unit Testing of Spring MVC Controllers: REST API](https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-rest-api/)
  * [Integration Testing of Spring MVC Applications: Controllers](https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-controllers/)
  * [Integration Testing of Spring MVC Applications: Security](https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-security/)
* [Spring Security for Spring Boot Integration Tests](https://www.baeldung.com/spring-security-integration-tests)
* [Exploring the Spring Boot TestRestTemplate](https://www.baeldung.com/spring-boot-testresttemplate)
