# Carog Server

## 기술 스택

- **Framework**: Spring Boot 3.5.3
- **Java**: 21
- **Database**: PostgreSQL
- **Cache**: Redis
- **ORM**: JPA (Hibernate), Mybatis
- **Build Tool**: Gradle
- **소셜 로그인**: 카카오 OAuth 2.0

## 주요 기능

- 카카오 OAuth 2.0 로그인
- JWT 기반 인증/인가
- 사용자 정보 관리
- CORS 지원


## 카카오 개발자 콘솔 설정

1. [카카오 개발자 콘솔](https://developers.kakao.com/)에서 애플리케이션 생성
2. **플랫폼 설정** → **Web 플랫폼 등록**
   - 사이트 도메인: `http://localhost:3000`
3. **카카오 로그인** 활성화
4. **Redirect URI 설정**: `http://localhost:3000/auth/login/kakao`
5. **동의항목 설정**:
   - 닉네임: 필수
   - 카카오계정(이메일): 필수

# JVM Options
```aiexclude
-Dspring.profiles.active=local
-Djwt.secret=secretValue
-Djasypt.encryptor.password=encryptorPassword
```