CREATE DATABASE IF NOT EXISTS reform_shop_2605
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS `admin`@`%` IDENTIFIED BY '0507';
GRANT ALL PRIVILEGES ON `reform_shop_2605`.* TO `admin`@`%`;

FLUSH PRIVILEGES;

USE `reform_shop_2605`;


-- 1. 일반 회원가입 회원
-- PK : member_id
CREATE TABLE member
(
    member_id         BIGINT PRIMARY KEY                        NOT NULL AUTO_INCREMENT COMMENT '회원 ID',
    email             VARCHAR(100)                              NOT NULL UNIQUE COMMENT '회원 이메일',
    password          VARCHAR(255)                              NULL COMMENT '회원 비밀번호 (암호화 처리)',
    nickname          VARCHAR(50)                               NOT NULL UNIQUE COMMENT '회원 닉네임',
    profile_image_url VARCHAR(500)                              NULL COMMENT '회원 프로필 이미지 URL',
    bio               VARCHAR(200)                              NULL COMMENT '회원 자기소개',
    manner_score      DECIMAL(3, 2)                             NULL     DEFAULT 0.00 COMMENT '매너 평균 점수',
    role              ENUM ('USER', 'ADMIN')                    NOT NULL DEFAULT 'USER' COMMENT '사용자 분류',
    status            ENUM ('ACTIVE', 'SUSPENDED', 'WITHDRAWN') NOT NULL DEFAULT 'ACTIVE' COMMENT '활동 상태(활성화 / 정지 / 탈퇴)',
    warning_count     INT                                       NOT NULL DEFAULT 0 COMMENT '누적 경고 횟수',
    email_event       BOOLEAN                                   NOT NULL DEFAULT FALSE COMMENT '이벤트 이메일 수신 여부',
    created_at        DATETIME                                  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일'
) ENGINE = InnoDB;


-- 2. 소셜 로그인 회원가입 회원
-- PK : social_id   |  FK : member_id
CREATE TABLE social_member
(
    social_id   BIGINT PRIMARY KEY       NOT NULL AUTO_INCREMENT COMMENT '소셜 로그인 ID',
    member_id   BIGINT                   NOT NULL COMMENT '회원 ID',
    provider    ENUM ('KAKAO', 'GOOGLE') NOT NULL COMMENT '소셜 로그인 경로(카카오/구글)',
    provider_id VARCHAR(100)             NOT NULL COMMENT '소셜 제공자 고유 ID',
    UNIQUE KEY uk_social_provider_user (provider, provider_id),
    UNIQUE KEY uk_social_member_provider (member_id, provider),
    CONSTRAINT fk_social_member_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 3. 회원별 관심 종목 및 구단
-- ** PK : member_id   |  FK : member_id **
CREATE TABLE interest_setting
(
    member_id BIGINT PRIMARY KEY                                                 NOT NULL COMMENT '회원 ID',
    sport     ENUM ('SOCCER', 'BASEBALL', 'BASKETBALL', 'VOLLEYBALL', 'ESPORTS') NOT NULL COMMENT '관심 종목',
    team      VARCHAR(100) COMMENT '관심 구단'                                       NULL,
    CONSTRAINT fk_interest_setting_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 4. 회원별 관심 키워드
-- PK : keyword_id   |  FK : member_id
CREATE TABLE interest_keyword
(
    keyword_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    member_id  BIGINT             NOT NULL COMMENT '회원 ID',
    keyword    VARCHAR(200)       NOT NULL COMMENT '관심 키워드',
    UNIQUE KEY uk_interest_keyword_member_keyword (member_id, keyword),
    CONSTRAINT fk_interest_keyword_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 5. 거래 매물 게시글
-- PK : post_id   |  FK : seller_id
-- IDX : seller_id, status
CREATE TABLE post
(
    post_id       BIGINT PRIMARY KEY                                                 NOT NULL AUTO_INCREMENT COMMENT '게시글 번호',
    seller_id     BIGINT                                                             NOT NULL COMMENT '판매자 member id',
    title         VARCHAR(200)                                                       NOT NULL COMMENT '제목',
    content       TEXT                                                               NOT NULL COMMENT '본문',
    sport         ENUM ('SOCCER', 'BASEBALL', 'BASKETBALL', 'VOLLEYBALL', 'ESPORTS') NOT NULL COMMENT '종목',
    team          VARCHAR(50)                                                        NOT NULL COMMENT '구단명',
    uniform_name  VARCHAR(200)                                                       NOT NULL COMMENT '유니폼명',
    grade         ENUM ('S', 'A', 'B', 'C')                                          NOT NULL COMMENT '유니폼 상태 등급',
    size          VARCHAR(10)                                                        NULL COMMENT '유니폼 사이즈 (85 ~120)',
    marking       BOOLEAN                                                            NULL     DEFAULT FALSE COMMENT '마킹 여부',
    price         INT                                                                NOT NULL COMMENT '희망 가격',
    delivery_type ENUM ('DIRECT', 'DELIVERY', 'BOTH')                                NOT NULL COMMENT '수령 방법',
    status        ENUM ('ON_SALE', 'RESERVED', 'SOLD', 'HIDDEN', 'DELETED')          NOT NULL DEFAULT 'ON_SALE' COMMENT '거래 상태',
    view_count    INT                                                                NOT NULL DEFAULT 0 COMMENT '조회수',
    wish_count    INT                                                                NOT NULL DEFAULT 0 COMMENT '찜 수',
    risk_level    ENUM ('LOW', 'MID', 'HIGH')                                        NULL COMMENT '위험 탐지 등급',
    created_at    DATETIME                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '게시글 등록일',
    updated_at    DATETIME                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '게시글 수정일',
    KEY idx_post_seller_id (seller_id),
    KEY idx_post_status (status),
    CONSTRAINT fk_post_member FOREIGN KEY (seller_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 6. 거래 매물 게시글 이미지
-- PK : image_id   |  FK : post_id
-- IDX : post_id
CREATE TABLE post_image
(
    image_id   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    post_id    BIGINT             NOT NULL COMMENT '게시글 ID',
    image_url  VARCHAR(500)       NOT NULL COMMENT '이미지 URL',
    sort_order INT                NULL DEFAULT 0 COMMENT '이미지 순서 (최대 10장)',
    KEY idx_post_image_post_id (post_id),
    CONSTRAINT fk_post_image_post FOREIGN KEY (post_id) REFERENCES post (post_id)
) ENGINE = InnoDB;


-- 7. 찜 목록
-- PK : wish_id   |  FK : member_id, post_id
-- IDX : post_id
CREATE TABLE wish
(
    wish_id    BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    member_id  BIGINT             NOT NULL,
    post_id    BIGINT             NOT NULL,
    created_at DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_wish_member_post (member_id, post_id),
    KEY idx_wish_post_id (post_id),
    CONSTRAINT fk_wish_member FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT fk_wish_post FOREIGN KEY (post_id) REFERENCES post (post_id)
) ENGINE = InnoDB;


-- 8. 거래 목록
-- PK : trade_id   |  FK : post_id, buyer_id, seller_id
-- IDX : buyer_id, seller_id, status
CREATE TABLE trade
(
    trade_id         BIGINT PRIMARY KEY                   NOT NULL AUTO_INCREMENT COMMENT '거래 ID',
    post_id          BIGINT                               NOT NULL UNIQUE COMMENT '게시글 ID',
    buyer_id         BIGINT                               NOT NULL COMMENT '판매자 member id',
    seller_id        BIGINT                               NOT NULL COMMENT '구매자 member id',
    status           ENUM ('REQUESTED', 'ACCEPTED', 'PAID', 'IN_PROGRESS',
        'CONFIRMED', 'COMPLETED', 'CANCELED', 'DISPUTED') NOT NULL COMMENT '거래 상태',
    delivery_type    ENUM ('DIRECT', 'DELIVERY')          NULL COMMENT '전달 방법 (수령 / 배송)',
    delivery_address VARCHAR(300)                         NULL COMMENT '배송 주소(배송 시 해당사항)',
    trade_price      INT                                  NOT NULL COMMENT '실제 거래 금액',
    completed_at     DATETIME                             NULL COMMENT '정산 완료 일시',
    confirmed_at     DATETIME                             NULL COMMENT '구매 확정 일시',
    created_at       DATETIME                             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_trade_buyer_id (buyer_id),
    KEY idx_trade_seller_id (seller_id),
    KEY idx_trade_status (status),
    CONSTRAINT fk_trade_post FOREIGN KEY (post_id) REFERENCES post (post_id),
    CONSTRAINT fk_trade_buyer_member FOREIGN KEY (buyer_id) REFERENCES member (member_id),
    CONSTRAINT fk_trade_seller_member FOREIGN KEY (seller_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 9. 매너 점수 및 거래 후기
-- PK : manner_id   |  FK : trade_id, buyer_id, seller_id
-- IDX : seller_id
CREATE TABLE manner_review
(
    manner_id  BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    trade_id   BIGINT             NOT NULL COMMENT '거래 ID',
    buyer_id   BIGINT             NOT NULL COMMENT '작성자 member id',
    seller_id  BIGINT             NOT NULL COMMENT '구매자 member id',
    score      DOUBLE             NOT NULL COMMENT '매너 점수 (0.0 ~ 5.0)',
    content    TEXT               NULL COMMENT '텍스트 후기',
    created_at DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성 시간',
    UNIQUE KEY uk_manner_review_trade_buyer (trade_id, buyer_id),
    KEY idx_manner_review_seller_id (seller_id),
    CONSTRAINT fk_manner_review_trade FOREIGN KEY (trade_id) REFERENCES trade (trade_id),
    CONSTRAINT fk_manner_review_buyer_member FOREIGN KEY (buyer_id) REFERENCES member (member_id),
    CONSTRAINT fk_manner_review_seller_member FOREIGN KEY (seller_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 10. 채팅방 로그 0
-- PK : chat_id   |  FK : trade_id, buyer_id, seller_id
-- IDX : buyer_id, seller_id
CREATE TABLE chat_room
(
    chat_id    BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    trade_id   BIGINT             NOT NULL UNIQUE,
    buyer_id   BIGINT             NOT NULL,
    seller_id  BIGINT             NOT NULL,
    created_at DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_chat_room_buyer_id (buyer_id),
    KEY idx_chat_room_seller_id (seller_id),
    CONSTRAINT fk_chat_room_trade FOREIGN KEY (trade_id) REFERENCES trade (trade_id),
    CONSTRAINT fk_chat_room_buyer_member FOREIGN KEY (buyer_id) REFERENCES member (member_id),
    CONSTRAINT fk_chat_room_seller_member FOREIGN KEY (seller_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 11. 결제 기록 0
-- PK : payment_id   |  FK : trade_id
CREATE TABLE payment
(
    payment_id       BIGINT              NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '결제 번호',
    trade_id         BIGINT              NOT NULL COMMENT '거래 ID (FK)',
    pay_method       ENUM ('Card','Pay') NOT NULL COMMENT '결제 방식',
    approval_no      VARCHAR(50)         NULL COMMENT '카드 승인 번호',
    toss_order_id    VARCHAR(100)        NOT NULL UNIQUE COMMENT 'Toss 주문 ID',
    toss_payment_key VARCHAR(200)        NULL COMMENT 'Toss 결제 키',
    amount           INT                 NOT NULL COMMENT '결제 금액',
    status           ENUM ('READY','PAID','FAILED','CANCELED','REFUNDED')
                                         NOT NULL DEFAULT 'READY' COMMENT '결제 상태',
    paid_at          DATETIME            NULL COMMENT '결제 완료 일시',
    created_at       DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_trade FOREIGN KEY (trade_id) REFERENCES trade (trade_id)
) ENGINE = InnoDB;


-- 12. 토스 로그 기록 (append-only) 0
-- PK : log_id   |  FK : payment_id
CREATE TABLE toss_log
(
    log_id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '로그 번호',
    payment_id       BIGINT       NOT NULL COMMENT 'FK → payment',
    toss_payment_key VARCHAR(200) NULL COMMENT 'Toss 결제 키',
    raw_response     JSON         NULL COMMENT '토스 API 원문',
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_toss_log_payment FOREIGN KEY (payment_id) REFERENCES payment (payment_id)
) ENGINE = InnoDB;


-- 13. 회원별 출금 포인트 조회 0
-- PK : wallet_id   |  FK : member_id
CREATE TABLE point_wallet
(
    wallet_id    BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    member_id    BIGINT             NOT NULL UNIQUE,
    balance      INT                NOT NULL DEFAULT 0 COMMENT '회원별 보유 포인트',
    withdrawable INT                NOT NULL DEFAULT 0 COMMENT '출금 가능 포인트',
    pending      INT                NOT NULL DEFAULT 0 COMMENT '출금 대기 포인트',
    UNIQUE KEY uk_point_wallet_member (member_id),
    CONSTRAINT fk_point_wallet_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 14. 회원별 출금 포인트 변동 내역 0
-- PK : point_id   |  FK : wallet_id, trade_id
-- IDX : wallet_id, trade_id
CREATE TABLE point_history
(
    point_id      BIGINT PRIMARY KEY        NOT NULL AUTO_INCREMENT,
    wallet_id     BIGINT                    NOT NULL,
    trade_id      BIGINT                    NULL UNIQUE COMMENT '중복 지급 방지 거래 ID',
    type          ENUM ('EARN', 'WITHDRAW') NOT NULL COMMENT '지급 / 반려',
    change_amount INT                       NOT NULL COMMENT '변동량',
    balance       INT                       NOT NULL DEFAULT 0 COMMENT '회원별 보유 포인트',
    created_at    DATETIME                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_point_history_wallet_id (wallet_id),
    KEY idx_point_history_trade_id (trade_id),
    CONSTRAINT fk_point_history_wallet FOREIGN KEY (wallet_id) REFERENCES point_wallet (wallet_id),
    CONSTRAINT fk_point_history_trade FOREIGN KEY (trade_id) REFERENCES trade (trade_id)
) ENGINE = InnoDB;


-- 15. 포인트 현금화 요청 목록 0
-- PK : withdraw_id   |  FK : member_id
-- IDX : member_id, status
CREATE TABLE point_request
(
    withdraw_id    BIGINT PRIMARY KEY                                    NOT NULL AUTO_INCREMENT,
    member_id      BIGINT                                                NOT NULL,
    request_amount INT                                                   NOT NULL COMMENT '출금 요청 금액',
    bank_name      VARCHAR(50)                                           NULL COMMENT '은행명',
    account_number VARCHAR(30)                                           NULL COMMENT '계좌번호',
    status         ENUM ('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') NOT NULL COMMENT '출금 처리 상태',
    reject_reason  VARCHAR(300)                                          NULL COMMENT '반려 사유',
    created_at     DATETIME                                              NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_point_request_member_id (member_id),
    KEY idx_point_request_status (status),
    CONSTRAINT fk_point_request_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 16. 커뮤니티 글 목록 0
-- PK : comm_id   |  FK : member_id
-- 거래 매물 게시글 필드와 구분짓기 위해 중복된 필드명은 앞에 comm_ 붙여 구분했습니다
CREATE TABLE community_post
(
    comm_id         BIGINT PRIMARY KEY                                                 NOT NULL AUTO_INCREMENT,
    member_id       BIGINT                                                             NOT NULL,
    sport_category  ENUM ('SOCCER', 'BASEBALL', 'BASKETBALL', 'VOLLEYBALL', 'ESPORTS') NOT NULL COMMENT '종목',
    team_category   VARCHAR(50)                                                        NULL COMMENT '구단명',
    comm_title      VARCHAR(200)                                                       NOT NULL COMMENT '제목',
    comm_content    TEXT                                                               NOT NULL COMMENT '본문',
    comm_image_url  VARCHAR(500)                                                       NULL COMMENT '첨부 이미지 URL',
    comm_view_count INT                                                                NOT NULL DEFAULT 0 COMMENT '조회수',
    like_count      INT                                                                NOT NULL DEFAULT 0 COMMENT '좋아요 수',
    comment_count   INT                                                                NOT NULL DEFAULT 0 COMMENT '댓글 수',
    status          ENUM ('ACTIVE', 'HIDDEN', 'DELETED')                               NOT NULL COMMENT '게시글 상태',
    created_at      DATETIME                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_community_post_member_id (member_id),
    CONSTRAINT fk_community_post_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 17. 댓글 목록 0
-- PK : reply_id   |  FK : post_id, member_id, parent_id
-- IDX : post_id, member_id, parent_id
CREATE TABLE reply
(
    reply_id      BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    post_id       BIGINT             NOT NULL,
    member_id     BIGINT             NOT NULL,
    parent_id     BIGINT             NULL COMMENT '대댓글의 부모 댓글',
    reply_content TEXT               NULL COMMENT '댓글 내용',
    is_deleted    BOOLEAN            NOT NULL DEFAULT FALSE,
    like_count    INT                NOT NULL DEFAULT 0,
    created_at    DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_comment_post_id (post_id),
    KEY idx_comment_member_id (member_id),
    KEY idx_comment_parent_id (parent_id),
    CONSTRAINT fk_comment_community_post FOREIGN KEY (post_id) REFERENCES community_post (comm_id),
    CONSTRAINT fk_comment_member FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT fk_comment_parent FOREIGN KEY (parent_id) REFERENCES reply (reply_id)
) ENGINE = InnoDB;


-- 18. 회원별 출금 포인트 조회 0
-- PK : point_id   |  FK : reporter_id
-- IDX : reporter_id, target_type + target_id
CREATE TABLE report
(
    report_id   BIGINT PRIMARY KEY                               NOT NULL AUTO_INCREMENT,
    reporter_id BIGINT                                           NOT NULL COMMENT '신고자 member id',
    target_type ENUM ('POST', 'COMMUNITY_POST')                  NOT NULL COMMENT '신고 대상',
    target_id   BIGINT                                           NOT NULL COMMENT '신고 대상 ID',
    reason      ENUM ('FAKE', 'INAPPROPRIATE', 'FRAUD', 'ETC')   NOT NULL COMMENT '신고 사유',
    detail      TEXT                                             NULL COMMENT '상세 내용',
    status      ENUM ('PENDING', 'NORMAL', 'WARNING', 'DELETED') NOT NULL COMMENT '신고 처리 상태',
    created_at  DATETIME                                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_report_reporter_id (reporter_id),
    KEY idx_report_target (target_type, target_id),
    CONSTRAINT fk_report_member FOREIGN KEY (reporter_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- 19. 알림 목록 0
-- PK : noti_id   |  FK : member_id
-- IDX : member_id, type
CREATE TABLE notification
(
    noti_id    BIGINT PRIMARY KEY                                       NOT NULL AUTO_INCREMENT,
    member_id  BIGINT                                                   NOT NULL COMMENT '수신자',
    type       ENUM ('TRADE', 'CHAT', 'PRICE_DROP', 'REVIEW', 'SYSTEM') NOT NULL COMMENT '알림 분류',
    content    VARCHAR(300)                                             NOT NULL COMMENT '알림 내용',
    link_url   VARCHAR(300)                                             NULL COMMENT '클릭 시 경로',
    is_read    BOOLEAN                                                  NOT NULL DEFAULT FALSE COMMENT '읽음 여부',
    created_at DATETIME                                                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_notification_member_id (member_id),
    KEY idx_notification_type (type),
    CONSTRAINT fk_notification_member FOREIGN KEY (member_id) REFERENCES member (member_id)
) ENGINE = InnoDB;


-- . 위험 탐지 목록
-- PK : point_id   |  FK : member_id
-- IDX : target_type + target_id
# CREATE TABLE risk_analysis_result
# (
#     risk_id     BIGINT PRIMARY KEY          NOT NULL AUTO_INCREMENT,
#     target_type ENUM ('POST', 'CHAT')       NOT NULL COMMENT '감지된 대상 분류',
#     target_id   BIGINT                      NOT NULL COMMENT '대상 ID',
#     risk_level  ENUM ('LOW', 'MID', 'HIGH') NOT NULL COMMENT '위험 레벨',
#     reason      TEXT                        NULL COMMENT '탐지 사유',
#     suggestion  TEXT                        NULL COMMENT '개선 제안',
#     created_at  DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
#     KEY idx_risk_analysis_target (target_type, target_id)
# ) ENGINE = InnoDB;