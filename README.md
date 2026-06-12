# 🏭 Factory MES (생산 실적 관리 시스템)

## 프로젝트 소개
제조 IT 도메인 기반의 MES(Manufacturing Execution System) 웹 애플리케이션입니다.
공장의 제품 관리, 작업지시, 생산 실적을 통합 관리하고 대시보드로 현황을 확인할 수 있습니다.

## 서비스 URL
http://43.200.180.81:8080

### 테스트 계정
| 구분 | 아이디 | 비밀번호 |
|------|--------|---------|
| 관리자 | admin | 1234 |
| 일반 사용자 | user | 1234 |

## 기술 스택
| 분류 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot 3.5 |
| Security | Spring Security 6, BCrypt, Role 기반 접근 제어 |
| Database | MariaDB |
| ORM | MyBatis |
| View | Thymeleaf, Thymeleaf Layout Dialect |
| Frontend | Bootstrap 5 (Darkly 테마) |
| Build | Gradle |
| WebSocket | Spring WebSocket, STOMP, SockJS |
| Modbus | jamod, Modbus TCP |
| 컨테이너 | Docker |
| 배포 | AWS EC2 |
| CI/CD | GitHub Actions |

## 주요 기능
- **로그인/로그아웃** : Spring Security 기반 인증, BCrypt 비밀번호 암호화, 아이디 중복 체크
- **제품 관리** : 제품 등록 / 조회 / 수정 / 삭제
- **작업지시 관리** : 작업지시 등록 / 조회 / 상태 변경(대기→진행중→완료) / 삭제 / 진척률 표시
- **생산 실적 관리** : 실적 등록 / 조회 / 수정 / 삭제 / 불량 원인 분류 / 유효성 검사
- **대시보드** : 작업지시 현황, 생산 진척률, 오늘의 생산 실적, 불량률 현황
- **실시간 대시보드** : WebSocket(STOMP) 기반 실시간 업데이트 (작업지시 현황, 생산 실적, 진척률)
- **권한 관리** : 관리자(ROLE_ADMIN) / 일반 사용자(ROLE_USER) 권한 분리, 접근 제어
- **검색/페이징** : 작업지시 및 생산 실적 검색 필터, 페이징 처리
- **PLC 자동 수집** : Modbus TCP 기반 가상 PLC 연동, 5초마다 생산 데이터 자동 수집 및 목표수량 도달 시 자동 완료 처리

## 유효성 검사
- 회원가입 아이디 중복 체크
- 생산 실적 작업자 필수 입력
- 생산 실적 미래 날짜 등록 방지
- 목표수량 초과 실적 등록 방지
- 권한 없는 접근 시 대시보드로 리다이렉트 및 알럿 표시

## 문제 해결 및 기술적 도전

### ✅ WebSocket 실시간 대시보드 구현
여러 사용자가 동시에 사용할 때 데이터 변경 시 새로고침 없이 대시보드가 업데이트되어야 했습니다.
→ Spring WebSocket + STOMP 프로토콜 적용으로 생산 실적 등록/수정/삭제, 작업지시 상태 변경 시 모든 접속 사용자의 대시보드가 실시간으로 업데이트되도록 구현했습니다.

### ✅ RESTful API 설계
SSR 방식의 Thymeleaf 기반 서비스에서 추후 React/Vue 등 CSR 방식으로 확장 가능하도록 별도 REST API 컨트롤러를 구성했습니다.
→ `/api/**` 경로로 SSR과 REST API를 분리하여 기존 화면은 유지하면서 JSON 기반 API를 별도로 제공하는 구조로 설계했습니다.

### ✅ 생산 실적 유효성 검사
목표수량 초과 등록, 미래 날짜 등록, 작업자 미입력 등 비정상적인 데이터가 등록되는 문제가 있었습니다.
→ 프론트엔드(HTML required, max 속성)와 서버 사이드 검증을 이중으로 적용하여 데이터 무결성을 확보했습니다.

### ✅ Modbus TCP 기반 PLC 데이터 자동 수집
실제 제조 현장처럼 PLC 장비에서 생산 데이터를 자동으로 수집하는 구조가 필요했습니다.
→ Docker 기반 가상 PLC 시뮬레이터를 구축하고 Modbus TCP 프로토콜로 Spring Boot MES와 연동하여 5초마다 생산 실적을 자동 수집하도록 구현했습니다. 목표수량 도달 시 스케줄러 자동 중지 및 작업지시 완료 처리까지 구현했습니다.

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
### 로컬 실행
```bash
# 1. 프로젝트 클론
git clone https://github.com/jangheeda/factory-mes.git

# 2. MariaDB 실행 후 DB 및 유저 생성
CREATE DATABASE factory_db;
CREATE USER 'factory'@'localhost' IDENTIFIED BY 'factory1234';
GRANT ALL PRIVILEGES ON factory_db.* TO 'factory'@'localhost';

# 3. 테이블 생성 (DBeaver 또는 MySQL 클라이언트에서 직접 실행)

# 4. Modbus 시뮬레이터 실행 (Docker 필요)
cd ~/modbus-server
docker build -t modbus-server .
docker run -d -p 502:502 --name modbus-simulator modbus-server

# 5. 프로젝트 실행
./gradlew bootRun

# 6. 브라우저 접속
http://localhost:8080
```

### 배포
- AWS EC2 (Ubuntu 26.04 LTS, t3.micro)
- GitHub Actions CI/CD 파이프라인 구축
- main 브랜치 push 시 자동 빌드 및 배포

## 프로젝트 구조
```
src/main/java/com/mes/factory
├── controller
│   └── api
├── service
├── mapper
├── dto
├── scheduler
├── security
└── config

.github
└── workflows
    └── deploy.yml
```