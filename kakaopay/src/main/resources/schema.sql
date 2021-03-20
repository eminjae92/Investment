--
-- 회원
--
DROP TABLE IF EXISTS INVESTMENT_PRODUCT;

CREATE TABLE INVESTMENT_PRODUCT COMMENT '투자상품' (
    PRODUCT_ID                  NUMBER(13)     NOT NULL     COMMENT '상품ID'
  , TITLE                       VARCHAR(100)                COMMENT '상품명'
  , TOTAL_INVESTING_AMOUNT      NUMBER(13)                  COMMENT '총 투자 모집금액'
  , CURRENT_INVESTING_AMOUNT    NUMBER(13)                  COMMENT '누적 투자 모집금액'
  , INVESTER_CNT                NUMBER(10)                  COMMENT '투자자 수'
  , STARTED_AT                  TIMESTAMP                   COMMENT '투자시작일시'
  , FINISHED_AT                 TIMESTAMP                   COMMENT '투자종료일시'
  , INVESTMENT_STATUS           VARCHAR(3)                  COMMENT '투자모집상태'
  , PRIMARY KEY (PRODUCT_ID)
);

--
-- 투자내역
--
DROP TABLE IF EXISTS INVESTMENT_DETAILS;

CREATE TABLE INVESTMENT_DETAILS COMMENT '투자내역' (
    SEQUENCE_NUMBER     NUMBER(13)      NOT NULL    COMMENT '투자내역ID'
  , USER_ID             VARCHAR(10)     NOT NULL    COMMENT '사용자ID'
  , PRODUCT_ID          NUMBER(13)      NOT NULL    COMMENT '상품ID'
  , INVESTMENT_AMOUNT   NUMBER(13)                COMMENT '투자금액'
  , INVESTMENT_DATE     TIMESTAMP                COMMENT '투자일시'

  , PRIMARY KEY (SEQUENCE_NUMBER)
);



