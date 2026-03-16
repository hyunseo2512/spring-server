# 실무급(Enterprise-level) 프로젝트 진단 및 아키텍처 제안

## 1. 현재 구조 진단
현재 `server/src` 디렉토리를 확인한 결과, Spring Initializr에서 생성한 **초기 기본 뼈대(`ServerApplication.java`)만 존재하는 상태**입니다.
별도의 비즈니스 로직, 예외 처리, 공통 설정, 도메인 분리 등의 패키지 구조가 전혀 갖춰져 있지 않아 실무 환경에 바로 투입하기엔 무리가 있습니다.

## 2. 제안하는 아키텍처 및 패키지 구조 (도메인 주도/계층형 혼합)

실무급 애플리케이션, 특히 유지보수성과 확장성이 중요한 프로젝트(향후 MSA 전환 고려 등)에 가장 많이 쓰이는 **도메인형 기반 패키지 구조**를 제안합니다.

### 디렉토리 템플릿 (`io.github.hyunseo2512.server` 하위)

```text
server/
├── global/                # 앱 전체에 적용되는 전역 설정 및 공통 모듈
│   ├── config/            # Spring Security, Swagger, JPA, Redis 등 Bean 설정
│   ├── error/             # 전역 예외 처리 (GlobalExceptionHandler, CustomExceptions, ErrorCode)
│   ├── common/            # API 공통 응답 형식(ApiResponse), 유틸(Utils), BaseTimeEntity 등
│   └── filter/            # 로깅, JWT 검증 등 전역 Servlet Filter / Interceptor
│
├── domain/                # 실제 비즈니스 로직이 들어가는 도메인 (단위별 응집도 극대화)
│   ├── member/            # 예: 회원 도메인
│   │   ├── api/           # 프레젠테이션 계층: Controller, 요청/응답 DTO (Record 사용 권장)
│   │   ├── application/   # 애플리케이션 계층: Facade 또는 Service (비즈니스 흐름 제어)
│   │   ├── domain/        # 도메인 계층: Entity, Value Object, 도메인 서비스
│   │   └── repository/    # 인프라 계층: Spring Data JPA Repository 등 영속성 처리
│   │
│   └── portfolio/         # 예: 포트폴리오 도메인 (member와 동일 구조 반복)
│
└── infra/                 # 외부 시스템 연동 전용 모듈 (AWS S3, 외부 API 클라이언트, 메일 전송 등)
```

## 3. 실무급 프로젝트 도약을 위한 5대 핵심 요소

단순히 폴더를 나누는 것을 넘어, 다음과 같은 공통 뼈대가 먼저 세팅되어야 합니다.

1. **공통 응답/에러 스펙 확립**: 프론트엔드와 일관된 통신을 위한 래퍼 클래스 설계. (예: `{ "status": 200, "message": "성공", "data": { ... } }` 형태)
2. **Global Exception Handler**: `@RestControllerAdvice`를 활용해 컨트롤러 로직에 `try-catch`를 없애고 예외를 중앙 집중식으로 로깅/응답 처리.
3. **Data Auditing (`BaseTimeEntity`)**: 모든 데이터베이스 테이블에 공통으로 들어가는 `생성일시`, `수정일시`를 JPA Auditing으로 자동화.
4. **API 문서화**: 프론트엔드 협업을 위해 `springdoc-openapi (Swagger)` 또는 `Spring REST Docs` 세팅.
5. **보안 (Spring Security & JWT)**: 모던 웹/앱 통신에 적합한 Stateless 토큰 기반 인증 구조 확립.

## 향후 진행 방식 (User Review Required)

> [!TIP]
> 위에서 제안한 **공통 기본 구조(global 패키지 및 5대 핵심 요소의 뼈대 코드)**를 제가 먼저 자동으로 작성해 세팅해 드릴까요?
> 동의하시면 프로젝트에 즉시 활용 가능한 실무 템플릿을 생성해 드리겠습니다.
