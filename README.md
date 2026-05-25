# 🏭 Factory MES (생산 실적 관리 시스템)

## 프로젝트 소개
제조 IT 도메인 기반의 MES(Manufacturing Execution System) 웹 애플리케이션입니다.
공장의 제품 관리, 작업지시, 생산 실적을 통합 관리하고 대시보드로 현황을 확인할 수 있습니다.

## 기술 스택
| 분류 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot 3.5 |
| Database | MariaDB |
| ORM | MyBatis |
| View | Thymeleaf |
| Build | Gradle |
| 배포 | Railway |

## 주요 기능
- **제품 관리** : 제품 등록 / 조회 / 수정 / 삭제
- **작업지시 관리** : 작업지시 등록 / 조회 / 상태 변경(대기→진행중→완료) / 삭제
- **생산 실적 관리** : 실적 등록 / 조회 / 삭제
- **대시보드** : 작업지시 현황, 오늘의 생산 실적, 불량률 현황

## DB 설계
```
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
├── worker
├── result_date
└── created_at
```

## 실행 방법
```bash
# 1. 프로젝트 클론
git clone https://github.com/jangheeda/factory-mes.git

# 2. MariaDB 실행 후 DB 생성
CREATE DATABASE factory_db;
CREATE USER 'factory'@'localhost' IDENTIFIED BY 'factory1234';
GRANT ALL PRIVILEGES ON factory_db.* TO 'factory'@'localhost';

# 3. 프로젝트 실행
./gradlew bootRun

# 4. 브라우저 접속
http://localhost:8080
```

## 프로젝트 구조
```
src/main/java/com/mes/factory
├── controller
├── service
├── mapper
└── dto
```