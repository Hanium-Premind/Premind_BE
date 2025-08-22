# Premind-BE
Premind Backend Repo입니다.

## 👤 멤버
|       | 함지나    | 
|-------|:---------:|
| 프로필    | <img src="https://avatars.githubusercontent.com/u/156528356?s=400&u=4760801211282592c4eb917efb1e6760b68aec20&v=4" width="100"/> | 
| 깃허브 ID   | [@Hamjina](https://github.com/Hamjina) | 

<br>

## ✍️ 의존성
- Gradle: 8.7
- Java: 17
- Spring Boot: 3.2.5
- Spring Security 
- Spring Data JPA
- MySQL: 8.0.33 
- Redis 
- QueryDSL: 5.0.0 
- JWT: JJWT 0.11.5 
- SMS 인증: Nurigo SDK 2.2 
- Swagger: SpringDoc OpenAPI 2.5.0 
- JUnit 5 
- Lombok 
- AWS SDK for S3
- Validation
- WebClient 

<br>

## 🏠 서비스 구성도
![alt text](서비스그림.png)
## front-end
1. 면접모드, 면접유형, 선택 직무, 면접관 스타일, 자소서, 포트폴리오 등의 옵션을 선택한다.  
2. 사용자가 선택한 옵션을 전달한다.  
6. 생성된 질문을 전달한다.  
7. 질문에 따른 답변을 제공받는다.  
8. 면접을 응시한 사용자의 면접 영상 데이터를 전달한다.  
13. 전달받은 영상 분석 결과를 토대로 분석 리포트를 제공한다.  

## back-end
3. 사용자가 선택한 옵션을 전달한다.  
5. 생성된 질문을 전달한다.  
9. 면접 영상 데이터를 전달한다.  
12. 영상 분석 결과를 전달한다.  

## AI
4. 선택 옵션에 따른 사용자 맞춤형 질문을 생성하여 전달한다.  
10. 전달받은 영상 데이터를 토대로 표정, 음성을 분석한다.  
11. 영상 분석 결과를 전달한다.  


<br>

## 🫙 ERD
![alt text](image-1.png)
<br>


## 🔨 기술 스택
**Framework** -  ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white)

**ORM** -  ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat&logo=spring&logoColor=white)

**Authorization** -  ![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)

**Test** -  ![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=flat&logo=testing-library&logoColor=white)

**Database** -  ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=flat&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white)

**CI/CD** -  ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat&logo=githubactions&logoColor=white)

**AWS** -  ![EC2](https://img.shields.io/badge/EC2-FF9900?style=flat&logo=amazonaws&logoColor=white)
![ECS](https://img.shields.io/badge/ECS-FF9900?style=flat&logo=amazonaws&logoColor=white)
![RDS](https://img.shields.io/badge/RDS-527FFF?style=flat&logo=amazonaws&logoColor=white)
![ElastiCache](https://img.shields.io/badge/ElastiCache-8B42CC?style=flat&logo=amazonaws&logoColor=white)


**Other** -  ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=swagger&logoColor=black)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat&logo=intellijidea&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white)
<br>


## 📜 API 명세서

[🔗 Notion API 문서](https://www.notion.so/API-1cef769b8566805f8043e5f7a45fa240?source=copy_link)
<br>
[🔗 Swagger 문서](http://52.78.218.243:8080/swagger-ui/index.html)

<br>


## 🌊 Commit Message Convention
| command | mean |
| --- | --- |
| feat | 새로운 기능을 추가하는 경우우 |
| fix | 기능을 수정하는 경우 |
| docs | 문서 관련 작업을 진행하는 경우 |
| chore | 프로젝트 설정을 하는 경우우 |
| bug | 기능에 오류가 발생하여 수정하는 경우우 |
| test | 테스트 코드를 추가하는 경우 |
| refactor | 코드 리팩토링을 하는 경우 |
| deploy | 배포 관련 코드를 작성하는 경우|
