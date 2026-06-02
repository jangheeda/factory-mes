# 🏭 Factory MES (생산 실적 관리 시스템)

## 프로젝트 소개
제조 IT 도메인 기반의 MES(Manufacturing Execution System) 웹 애플리케이션입니다.
공장의 제품 관리, 작업지시, 생산 실적을 통합 관리하고 대시보드로 현황을 확인할 수 있습니다.

## 기술 스택
| 분류 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot 3.5 |
| Security | Spring Security 6 |
| Database | MariaDB |
| ORM | MyBatis |
| View | Thymeleaf, Thymeleaf Layout Dialect |
| Frontend | Bootstrap 5 (Darkly 테마) |
| Build | Gradle |
| WebSocket | Spring WebSocket, STOMP, SockJS |
| 배포 | AWS EC2 (예정) |

## 주요 기능
- **로그인/로그아웃** : Spring Security 기반 인증, BCrypt 비밀번호 암호화, 아이디 중복 체크
- **제품 관리** : 제품 등록 / 조회 / 수정 / 삭제
- **작업지시 관리** : 작업지시 등록 / 조회 / 상태 변경(대기→진행중→완료) / 삭제 / 진척률 표시
- **생산 실적 관리** : 실적 등록 / 조회 / 수정 / 삭제 / 불량 원인 분류 / 유효성 검사
- **대시보드** : 작업지시 현황, 생산 진척률, 오늘의 생산 실적, 불량률 현황
- **실시간 대시보드** : WebSocket(STOMP) 기반 실시간 업데이트 (작업지시 현황, 생산 실적, 진척률)

## 유효성 검사
- 회원가입 아이디 중복 체크
- 생산 실적 작업자 필수 입력
- 생산 실적 미래 날짜 등록 방지
- 목표수량 초과 실적 등록 방지

## API 명세
| Method | URL | 설명 |
|--------|-----|------|
| GET | /api/products | 제품 목록 조회 |
| GET | /api/products/{id} | 제품 단건 조회 |
| POST | /api/products | 제품 등록 |
| PUT | /api/products/{id} | 제품 수정 |
| DELETE | /api/products/{id} | 제품 삭제 |
| GET | /api/work-orders | 작업지시 목록 조회 |
| GET | /api/work-orders/{id} | 작업지시 단건 조회 |
| POST | /api/work-orders | 작업지시 등록 |
| PATCH | /api/work-orders/{id}/status | 작업지시 상태 변경 |
| DELETE | /api/work-orders/{id} | 작업지시 삭제 |
| GET | /api/results | 생산 실적 목록 조회 |
| GET | /api/results/{id} | 생산 실적 단건 조회 |
| POST | /api/results | 생산 실적 등록 |
| PUT | /api/results/{id} | 생산 실적 수정 |
| DELETE | /api/results/{id} | 생산 실적 삭제 |
| GET | /api/dashboard | 대시보드 데이터 조회 |

## DB 설계
```
users (회원)
├── user_id
├── login_id
├── password (BCrypt 암호화)
├── user_name
├── role
└── created_at

product (제품)
├── product_id
├── product_name
├── product_code
├── unit
└── created_at

work_order (작업지시)
├── order_id
├── product_id (FK)
├── target_qty
├── status
├── order_date
└── created_at

production_result (생산실적)
├── result_id
├── order_id (FK)
├── good_qty
├── defect_qty
├── defect_type
├── worker
├── result_date
└── created_at
```

## 실행 방법
```bash
# 1. 프로젝트 클론
git clone https://github.com/jangheeda/factory-mes.git

# 2. MariaDB 실행 후 DB 및 유저 생성
CREATE DATABASE factory_db;
CREATE USER 'factory'@'localhost' IDENTIFIED BY 'factory1234';
GRANT ALL PRIVILEGES ON factory_db.* TO 'factory'@'localhost';

# 3. 테이블 생성 (DBeaver 또는 MySQL 클라이언트)
# /src/main/resources/sql/schema.sql 참고 (추후 추가 예정)

# 4. 프로젝트 실행
./gradlew bootRun

# 5. 브라우저 접속
http://localhost:8080
```

## 프로젝트 구조
```
src/main/java/com/mes/factory
├── controller
│   └── api
├── service
├── mapper
├── dto
├── security
└── config
```