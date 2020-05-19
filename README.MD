#application-dev.properties 생성 후 

username, password 추가
 
spring.datasource.url=jdbc:mysql://localhost:3306/task_agile?useSSL=false
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver


#maven build option 
``` 
mvn install -DSpring.profiles.dev=dev
```
#intellij springboot edit-configration
acitve-profile 에 dev 추가 


