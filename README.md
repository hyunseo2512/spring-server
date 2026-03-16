Spring Boot Portfolio Server
============================

본 프로젝트는 spring boot 웹 서비스 예제 입니다.
배포 환경(VM 등)으로의
이식성을 극대화하기 위해 전체 인프라를 Docker 및 Docker Compose를 기반으로 구성하였습니다.
이를 통해 복잡한 환경 설정 없이 손쉽게 서비스를 실행할 수 있습니다.


Quick Start
-----------

* 소스 코드 복제: git clone <repository_url> && cd spring-server
* 서비스 빌드 및 실행: docker compose up -d --build
* 서비스 상태 확인: docker compose ps
* 서비스 종료: docker compose down


Infrastructure
--------------

이 프로젝트는 다음과 같은 주요 컨테이너로 구성되어 있습니다:

* Nginx (nginx) - 웹 서버 및 리버시 프록시 로드밸런서 (80 포트 사용)
* Spring Boot App (app) - Java 21 기반 메인 백엔드 API 서버 (8080 포트 사용)
* PostgreSQL (postgres) - 관계형 데이터베이스 (Docker Volume으로 데이터 영속성 보장)


Directory Structure
-------------------

주요 설정 파일 및 애플리케이션 코드 위치는 다음과 같습니다:

* docker-compose.yml   - 전체 서비스 프로비저닝 및 네트워크/볼륨 구성 파일
* README.md            - 현 프로젝트 설명서 (본 문서)
* server/              - Spring Boot 애플리케이션 루트 디렉토리
* server/build.gradle  - 의존성 및 빌드 설정 스크립트
* server/Dockerfile    - Multi-stage 기반 컨테이너 이미지 빌드 명세서
* server/src/          - 실제 비즈니스 로직 코드가 위치한 디렉토리


Service Access Info
-------------------

각 서비스 환경별 접근 방법은 아래를 참고하십시오:

* Web Server (Nginx) : http://localhost:80 (VM 배포 후에는 해당 인스턴스의 공인 IP)
* API Server (Spring): http://localhost:8080 (주로 내부 통신용, 직접 접근시 사용)
* Database (Postgres): localhost:5432 (DB: mydatabase / User: root / Pwd: password)
