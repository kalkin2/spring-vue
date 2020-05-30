#DOCKER 설치 (MYSQL5.7) 
docker run --name mysql57 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root 
-e MYSQL_ROOT_HOST='%' --restart=unless-stopped -d mysql/mysql-server:5.7 
--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

## DOCKER 접속
docker exec -ti mysql57 bash


## MYSQL 접속 
mysql -uroot -p


## 스키마 생성 
create database taskAgile DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


#application-dev.properties 생성 후 

username, password 추가
 
spring.datasource.url=jdbc:mysql://localhost:3306/task_agile?useSSL=false
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver


#maven build option 
``` 
mvn install -DSpring.profiles.active=dev
```
#intellij springboot edit-configration
acitve-profile 에 dev 추가 


