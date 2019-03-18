# Service Layer Unit Tests (Car Dealer)
[Task](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/03.%20Unit%20Testing%20and%20Isolation%20-%20Exercise.pdf) is to write unit tests for CRUD-like [services](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/tree/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/src/main/java/org/softuni/cardealer/service).

There is effectively no [service](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/src/main/java/org/softuni/cardealer/service/CarServiceImpl.java) logic in the given skeleton, so tests are based on 100% [mocking](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/src/test/java/org/softuni/cardealer/service/CarServiceTests.java) of service dependencies and evaluation of method calls, argument and return values.
___
## Setup
Tested on Ubuntu 18.0.4
### Versions
* Java **11.0.2**
* Spring Boot **2.1.3.RELEASE**
### Other tools:
* [ModelMapper](http://modelmapper.org/)

System and IDE should be configured to use:
* [Java **11.0.2**](https://docs.oracle.com/cd/E19509-01/820-3208/inst_cli_jdk_javahome_t/) - [IntelliJ](https://stackoverflow.com/questions/18987228/how-do-i-change-the-intellij-idea-default-jdk)
___
#### Project configuration
* [pom.xml](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/pom.xml)
* [application.properties](https://github.com/Martin-BG/SoftUni-Java-MVC-Frameworks-Spring-Feb-2019/blob/master/03.%20Unit%20Testing%20and%20Isolation/Exercise/Car%20Dealer/src/main/resources/application.properties)

___
## Takeaways
* Mocking
```java
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    
    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public CarServiceModel editCar(CarServiceModel carServiceModel) {
        Car car = this.carRepository.findById(carServiceModel.getId()).orElse(null);
        car.setMake(carServiceModel.getMake());
        car.setModel(carServiceModel.getModel());
        car.setTravelledDistance(carServiceModel.getTravelledDistance());
        
        car = this.carRepository.saveAndFlush(car);
        
        return this.modelMapper.map(car, CarServiceModel.class);
    }
    
    //..
}
```
```java
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CarServiceTests {

    @Mock
    private CarRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarServiceImpl service;

    @Before
    public void initTests() {
        Mockito.when(modelMapper.map(eq(null), any()))
                .thenThrow(new IllegalArgumentException());
    }

    @Test
    public void editCar_validInputData_correctMethodsAndArgumentsUsed() {
        Car car = mock(Car.class);
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(model.getMake()).thenReturn("make");
        Mockito.when(model.getModel()).thenReturn("model");
        Mockito.when(model.getTravelledDistance()).thenReturn(1L);
        Mockito.when(repository.findById(eq("id"))).thenReturn(Optional.of(car));
        Mockito.when(repository.saveAndFlush(car)).thenReturn(car);
        Mockito.when(modelMapper.map(car, CarServiceModel.class)).thenReturn(model);

        CarServiceModel result = service.editCar(model);

        Mockito.verify(repository).findById("id");
        Mockito.verify(car).setMake("make");
        Mockito.verify(car).setModel("model");
        Mockito.verify(car).setTravelledDistance(1L);
        Mockito.verify(repository).saveAndFlush(car);
        Mockito.verify(modelMapper).map(car, CarServiceModel.class);
        Assert.assertEquals(model, result);
    }

    @Test(expected = NullPointerException.class)
    public void editCar_invalidId_throwsNullPointerException() {
        CarServiceModel model = mock(CarServiceModel.class);
        Mockito.when(model.getId()).thenReturn("id");
        Mockito.when(repository.findById("id")).thenReturn(Optional.empty());

        service.editCar(model);
    }

    @Test(expected = NullPointerException.class)
    public void editCar_nullInput_throwsNullPointerException() {
        service.editCar(null);
    }
    
    //...
}
```
