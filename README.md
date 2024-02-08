# Ticket Auction(T.A)

## 소개
**🎟️ 서비스 주소** : [Ticket Auction 서비스 주소](https://ticket-auction.shop/index.html)

암표 문제를 방지하기 위해 양도 불가능하며 어차피 비싸게 살 티켓을 합법적으로 경매로 살 수 있는 티켓 예매 서비스 개발

## 🧑‍💻팀원
|임시|<img src="https://github.com/jshstar/ticket-auction-backend/assets/17760465/337d188f-af3f-4e78-9345-14023612c855" style="width:200px; height:200px;">|임시|<img src="https://github.com/jshstar/ticket-auction-backend/assets/50236501/61a3d287-d8bf-4004-8f8e-274a5027c88b" style="width:200px; height:200px;">|
|:--:|:--:|:--:|:--:|
|[정성호](https://github.com/jshstar?tab=repositories)|[김진훈](https://github.com/ouohoon?tab=repositories)|[김민중](https://github.com/kmiss?tab=repositories)|[김혜윤](https://github.com/kimhyeyun?tab=repositories)|
|리더|부리더|팀원|팀원|
|공연장/공연/공연 정보/등급<br>API성능<br>테스트 캐싱 기능|예매 API<br>동시성 테스트<br>성능 테스트<br>캐싱 기능|경매/입찰 API<br>동시성 테스트<br>CI/CD 구성<br>인프라 설계/구성|회원/결제 API<br>인증/인가 처리<br>전반 프론트 구현<br>성능 테스트|


## 🏗 아키텍쳐
![t-a_구조_최종 drawio](https://github.com/jshstar/ticket-auction-backend/assets/50236501/1616782d-1939-42e2-a4d8-dc72488ecf60)

## Backend CI/CD
![image](https://github.com/jshstar/ticket-auction-backend/assets/50236501/ffb381e7-5ac9-4b87-94e5-076e017a8275)


## 🛠️ 사용 기술

### Backend

- Java 17
- Spring Boot 3.2.1
- Spring Data JPA
- Spring Data Redis
- QueryDsl

- Lombok
- Jwt
- Zxing (QR Code)
- Spring Security
- Spring Validation

### Frontend

- HTML 5
- CSS
- JQuery
- Javascript

- Bootstrap
- sweetalert
- js-cookie
- fullcalendar
- jQuery Seat Charts

### Infrastructure

- EC2
- Application Load Balancer
- S3
- CloudFront
- RDS
- Docker

- Elastic Cache for Redis
- Prometheus
- Grafana
- Promtail
- Loki

## 🍀 주요 기술

### **서비스**

- 동시성 제어 - (Unique Index, Distribution Lock)
- Redis 캐시 서버
- Server-Sent-Events
- 토스 결제 API

### 성능 개선

- 서비스 성능 테스트 - nGrinder
- CI/CD - Github Actions

### 인프라

- CI/CD
    - GitHubActions
    - Code Deploy - Blue Green Deploy
    - ECR
- 모니터링
    - Prometheus
    - grafana
- 분산처리
    - Application Load Balancer
    - Auto Scaling group
