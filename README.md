# todo

------

Spring Boot를 이용한 간단한 To Do List 웹 어플리케이션



## Description

------

- Spring Boot 를 이용한 간단한 ToDo List 프로젝트
- Spring Boot에 대한 학습 및  JPA 사용법 숙달
- Spring Boot Security를 이용한 credential login 구현



## Installation

------

|     도구     |             버전              |
| :----------: | :---------------------------: |
|    Spring    |   Spring Boot 2.1.3.RELEASE   |
|   운영체제   |           Windows10           |
|   개발 툴    | IntelliJ IDEA Ultimate 2018.3 |
|     JDK      |             JDK 8             |
| 데이터베이스 |          MySQL 8.0.3          |
|   빌드 툴    |         Gradle 5.2.1          |



## Dependencies

------

```basic
compile 'org.springframework.boot:spring-boot-starter-security'
compile 'org.springframework.boot:spring-boot-starter-data-jpa'
compile 'org.springframework.boot:spring-boot-starter-thymeleaf'
compile 'org.springframework.boot:spring-boot-starter-web'
testCompile 'org.springframework.boot:spring-boot-starter-test'

compileOnly 'org.projectlombok:lombok'
runtimeOnly 'org.springframework.boot:spring-boot-devtools'
runtimeOnly 'mysql:mysql-connector-java'
annotationProcessor 'org.projectlombok:lombok'        
```





## Progress

------

1. 프로젝트 생성

   - Lombok, Jpa, Thymeleaf, Web, MySql 의존성 추가
   - Lombok plugin 설치 후 setting / 'Build, Excution, Deployment' / Compile / Annotaion Processors에 Enavle annotation processing check (해당 설정을 하지 않으면 Lombok 사용 시 오류 발생)

2. User와 ToDo 양방향 관계성 설정

   - User DAO

   ```java
   public class User implements Serializable {
       ...
       @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
       private List<ToDo> toDos = new ArrayList<>();
       ...
   }
   ```

   - ToDo DAO

   ```java
   public class ToDo implements Serializable {
   	...
       @ManyToOne(fetch = FetchType.LAZY)
       private User user;
       ...
   }
   ```

3. User, ToDo Repository 생성

   - UserRepository

   ```java
   public interface UserRepository extends JpaRepository<User, Long> {
       User findById(String id);
   }
   ```

   - ToDoRepository

   ```java
   public interface ToDoRepository extends JpaRepository<ToDo, Long> {
       List<ToDo> findByUserOrderByIdx(User user);
   }
   ```

4. Spring Security 추가
   - SecurityConfig 구현 (상속 WebSecurityConfigurerAdapter)

    ```java
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter { ... }
    ```

    - Security 제외 패턴 등록 (in SecurityConfig )

    ```java
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().
        antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**", "/js/**");
    }
    ```

    - Security 설정 (in SecurityConfig)

    ```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 인증 설정 및 필터 등록 */
    }
    ```

5. userdetails.User 를 상속받은 SecurityUser 객체 생성

  - Spring Security에서 Custom User를 사용하기 위해서는 security의 User class를 상속 받아서 구현하거나, detailUser interface를 이용해 구현해야 한다.

6. UserDeatileService interface를 구현한 CustomUserDetailsService 생성

   - loadUserByUsername (로그인 로직을 처리한다.)

   ```java
   @Service
   public class CustomUserDeatilsService implements UserDetailsService {
       @Autowired
       UserRepository userRepository;
   
       @Override
       public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           User user = userRepository.findById(username);
           if (user == null) {
              throw new UsernameNotFoundException(username);
           }
           return new SecurityUser(user);
       }
   }
   ```

7. CustomLoginSuccessHandler 생성 (SavedRequestAwareAuthenticationSuccessHandler 상속)

   - 권한 없는 페이지에 접속했을 때, 로그인페이지로 이동하고 인증을 거치면 이전 페이지로 이동되게 한다.

8. ErrorController interface를 구현한 CustomErrorController 생성

   - application.poolidentity에 server.error.whitelabel.enable=false (white label page 속성 변경)
   - 404 error, 500 error 를 custompage가 나타나게 한다.

   ```java
   @Controller
   public class CustomErrorController implements ErrorController {
       @RequestMapping(value = "/error")
       public String handleError(HttpServletRequest request) {
           Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
   
           if (status != null) {
               Integer statusCode = Integer.valueOf(status.toString());
   
               if(statusCode == HttpStatus.NOT_FOUND.value()) {
                   return "/errors/404";
               }
               else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                   return "/errors/500";
               }
           }
           return "error";
       }
       @Override
       public String getErrorPath() {
           return "/error";
       }
   }
   ```



## With

------

참여인원 : [민경환](https://www.github.com/ber01), [박동현](https://www.github.com/pdh6547), [신무곤](https://www.github.com/mkshin96), [신재홍](https://www.github.com/woghd9072), [양기석](https://www.github.com/yks095), [엄태균](https://www.github.com/etg6550), [임동훈](https://www.github.com/dongh9508), [최광민](https://www.github.com/rhkd4560), [하상엽](https://www.github.com/hagome0)