## 설계 내용

### Java / spring-boot 이용

### DB h2 , ORM JPA 사용
### API 명세서 spec swagger 이용
### Spring WebFlux - 대량 트랙잭션처리를 위함 : 사용해보진 못했지만, 이번 기회에 사용
### 서비스 Layer 설계  : controller - domain - service - store
- controller : API 구현 객체
- domain : DTO, VO 등 객체 관리
- service : 실제 로직이 수행되는 곳
- store : DB와 접근이 이루어 지는 곳

### 모든 로직은 클래스간 결합도를 낮추고 유지보수성을 위해 Interface를 정의하고 구현해 로직을 처리하는 방식

### AOP 이용해 로깅처리 : 요청 파라미터, 결과값, 수행 시간 출력

### 단위테스트
1. testEndProductAndSoldOutException : 총 투자모집금액 달성시 투자는 마감, 상품은 Sold Out 예외 처리 테스트
2. testAfterInvestIncreasing : 투자 후 투자상품의 누적 투자모집 금액, 투자자 수 증가 테스트
3. testNoProductException : 투자 상품 없을 시 예외 처리 테스트
4. testSaveAndSelectInvestmentProduct : 투자 상품 저장 및 조회  단위테스트
5. testSaveAndSelectInvest : 투자 내역 저장 및 조회 단위테스트
6. testCurrentInvestmentDetails : 투자상품 테이블 조회해서 현재 투자모집 금액 조회 단위 테스트

#### 스웨거 주소 : http://localhost:5555/swagger-ui/
#### h2 디비 Console 주소 : http://localhost:5554

### 사용자 정의 PK 2종 생성(투자상품 테이블 PK: ProductIdGenerator, 투자내역 테이블 PK: DetailsIdGenerator)

### 사용자 정의 예외
1. SoldOutException : Sold Out 예외
2. NoProductException : 투자 제품 미존재 예외
- ExceptionHandler 이용해 사용자 정의 예외 처리(401코드, 메세지) 담아 리턴

### DB Table 설계(2종)
#### 1. INVESTMENT_PRODUCT(투자상품) 테이블
|  항목영문명 | 항목한글명  |
|---|---|
|PRODUCT_ID(PK)|'상품ID'|
|TITLE | '상품명'|
|TOTAL_INVESTING_AMOUNT|'총 투자 모집금액'|
|CURRENT_INVESTING_AMOUNT|'누적 투자 모집금액'|
|INVESTER_CNT|'투자자 수'|
|STARTED_AT|'투자시작일시'|
|FINISHED_AT|'투자종료일시'|
|INVESTMENT_STATUS|'투자모집상태'|
   
#### 2. INVESTMENT_DETAILS(투자내역) 테이블
| 항목영문명 | 항목한글명 |
|---|---|
|SEQUENCE_NUMBER(PK)|'투자내역ID'|
|USER_ID             |'사용자ID'|
|PRODUCT_ID          |'상품ID'|
|INVESTMENT_AMOUNT   |'투자금액'|
|INVESTMENT_DATE     |'투자일시'|

※ PRODUCT_ID로 투자상품 테이블 ManyToOne 연관관계 설정

※ 투자내역테이블을 초기에 UserId + ProductId를 키로 정했으나,
이후 사용자가 여러번 투자할수도 있으니 시퀀스키를 PK로 설정
   
### API 정의서 : 스웨거 참고(http://localhost:5555/swagger-ui/)
1.전체 투자 상품 조회 : 현재 시간으로 모집기간 내 상품만 조회
- InvestmentDto로 리턴(상품ID, 상품제목, 총 모집금액, 현재 모집금액, 투자자 수, 투자모집상태, 상품 모집기간)
- DTO 객체에서 모집상태, 모집기간 계산, 모집상태 ENUM 클래스 이용

※ ENUM으로 처리(모집상태) : ING(모집중), END(모집완료)

2.투자하기
- 투자상품 테이블 조회해서 현재 투자모집 금액 조회
  - if 투자상품 결과 없을시, NoProductException 처리
- if 투자금액 + 현재 투자모집 금액 > 총투자모집금액, SoldOutException 처리
- 투자상품테이블 업데이트 누적투자모집금액 증가, 투자자 수 증가
- 투자상품 마감시 마감처리
- 투자내역 객체 생성(Builder 패턴이용) -> 투자내역 테이블 저장

3.나의 투자상품 조회
- 사용자 ID로 투자내역 테이블 조회









