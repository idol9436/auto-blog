# 개요
- AI 프롬프트를 이용하여 blog 작성 자동화
- git(md파일 업로드), notion(api) 연동을 목표로 합니다.
- 로그인시 프리뷰 이용가능
- 관리자 승인시 다회 이용가능

## DONE
- 기본 UI 레이아웃 골격
- 기본 html 반영 (preview : mock data)
- 헥사고날 아키텍쳐 준수 
- H2 연동
- 로그인 테이블 설계
- 로그인 프로세스 검토 v
- 비밀번호 암호화 저장

## TODO NOW
- Priview 화면 md파일 모드로 전환
- 프롬프토 정교화
- 블로그/깃 연동
- 예외처리 및 에러 안내
- 세션 적용
- Native 로그인 로직 정리
- 로그인된 사용자만 Prompt 가능 : 로그인안하면 로그인창으로

## TODO LIST
- 요구사항 상세화 (계속...)

### Infra
- 테이블 설계 : user-origin, user-oauth, gen-content
- DB 생성 및 H2, JPA, Mybatis 연동 테스트
- 로그인 DB연동
- 시큐리티 : 비밀번호 해시저장 + 세션 / OAUTH 확장

### Business
- 템플릿 고도화 : Content(text+body+summury+tag+image...) > md 파일로 간소화 및 UI레이아웃 유연성 증대
- core기능 개발 : preview -> your blog (git, notion)
- API 고도화 : Image Req, Image Return, chain prompt 및 response 안정성
- bulk prompt(long text, attach img & txtfile 등) 기능 추가

### Operation
- 이메일/SMS 인증 구현 등 인증인가 추가
- 로그 : 접속기록 + prompt 이용내역 등
- 예외처리 : 로그인관련, api관련, 기본 viewpage관련(404 등) 
- 단위 테스트 추가 : api, db접근 등


## RULES
### 네이밍 정리표
|변환	|위치	|네이밍|
|:---:|:---:|:---:|
DTO → Command|	Controller / DTO    |toCommand()
Command → Domain|	Domain	|from(Command)
Domain → Entity|	Entity	|from(Domain)
Entity → Domain|	Entity	|toDomain()
응답 Domain → DTO|	DTO	|from(Domain)


### 아키텍쳐

Client
    > spring.security(http, UserDetails)        
    > web.in.Controller(http.req.dto)       
    > port.in.Usecase(application.command)      
        < application.Service(application.command) > port.out.Port(domain.model)        
                                                        < web.out.api.Client(domain.model)      
                                                        < web.out.persistence.adapter.Adapter(domain.model) > web.out.persistence.repository.Repository(web.out.persistence.entity)     
                                                                                                                 < web.out.persistence.entity.Entity