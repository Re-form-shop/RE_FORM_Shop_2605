CREATE DATABASE IF NOT EXISTS reform_shop_2605
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE reform_shop_2605;

CREATE TABLE member (
    member_id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NULL,
    nickname VARCHAR(50) NOT NULL,
    profile_image_url VARCHAR(500) NULL,
    bio VARCHAR(200) NULL,
    manner_score DECIMAL(3,2) NULL DEFAULT 0.00,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    status ENUM('ACTIVE', 'SUSPENDED', 'WITHDRAWN') NOT NULL DEFAULT 'ACTIVE',
    warning_count INT NOT NULL DEFAULT 0,
    email_event BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (member_id),
    UNIQUE KEY uk_member_email (email),
    UNIQUE KEY uk_member_nickname (nickname)
) ENGINE=InnoDB;

CREATE TABLE social_member (
    social_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    provider ENUM('KAKAO', 'GOOGLE') NOT NULL,
    provider_id VARCHAR(100) NOT NULL,
    PRIMARY KEY (social_id),
    UNIQUE KEY uk_social_provider_user (provider, provider_id),
    UNIQUE KEY uk_social_member_provider (member_id, provider),
    CONSTRAINT fk_social_member_member
        FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE interest_setting (
    member_id BIGINT NOT NULL,
    sport ENUM('SOCCER', 'BASEBALL', 'BASKETBALL', 'VOLLEYBALL', 'ESPORTS') NOT NULL,
    team VARCHAR(100) NULL,
    PRIMARY KEY (member_id),
    CONSTRAINT fk_interest_setting_member
        FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE interest_keyword (
    keyword_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    keyword VARCHAR(200) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (keyword_id),
    UNIQUE KEY uk_interest_keyword_member_keyword (member_id, keyword),
    CONSTRAINT fk_interest_keyword_member
        FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE post (
    post_id BIGINT NOT NULL AUTO_INCREMENT,
    seller_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    sport ENUM('SOCCER', 'BASEBALL', 'BASKETBALL', 'VOLLEYBALL', 'ESPORTS') NOT NULL,
    team VARCHAR(50) NOT NULL,
    uniform_name VARCHAR(200) NOT NULL,
    grade ENUM('S', 'A', 'B', 'C') NOT NULL,
    size VARCHAR(10) NULL,
    marking BOOLEAN NULL DEFAULT FALSE,
    price INT NOT NULL,
    delivery_type ENUM('DIRECT', 'DELIVERY', 'BOTH') NOT NULL,
    status ENUM('ON_SALE', 'RESERVED', 'SOLD', 'HIDDEN', 'DELETED') NOT NULL DEFAULT 'ON_SALE',
    view_count INT NOT NULL DEFAULT 0,
    wish_count INT NOT NULL DEFAULT 0,
    risk_level ENUM('LOW', 'MID', 'HIGH') NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (post_id),
    KEY idx_post_seller_id (seller_id),
    KEY idx_post_status (status),
    CONSTRAINT fk_post_member
        FOREIGN KEY (seller_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE post_image (
    image_id BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    sort_order INT NULL DEFAULT 0,
    PRIMARY KEY (image_id),
    KEY idx_post_image_post_id (post_id),
    CONSTRAINT fk_post_image_post
        FOREIGN KEY (post_id) REFERENCES post(post_id)
) ENGINE=InnoDB;

CREATE TABLE wish (
    wish_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (wish_id),
    UNIQUE KEY uk_wish_member_post (member_id, post_id),
    KEY idx_wish_post_id (post_id),
    CONSTRAINT fk_wish_member
        FOREIGN KEY (member_id) REFERENCES member(member_id),
    CONSTRAINT fk_wish_post
        FOREIGN KEY (post_id) REFERENCES post(post_id)
) ENGINE=InnoDB;

CREATE TABLE trade (
    trade_id BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    status ENUM('REQUESTED', 'ACCEPTED', 'PAID', 'IN_PROGRESS', 'CONFIRMED', 'COMPLETED', 'CANCELED', 'DISPUTED') NOT NULL,
    delivery_type ENUM('DIRECT', 'DELIVERY') NULL,
    delivery_address VARCHAR(300) NULL,
    trade_price INT NOT NULL,
    confirmed_at DATETIME NULL,
    completed_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (trade_id),
    UNIQUE KEY uk_trade_post (post_id),
    KEY idx_trade_buyer_id (buyer_id),
    KEY idx_trade_seller_id (seller_id),
    KEY idx_trade_status (status),
    CONSTRAINT fk_trade_post
        FOREIGN KEY (post_id) REFERENCES post(post_id),
    CONSTRAINT fk_trade_buyer_member
        FOREIGN KEY (buyer_id) REFERENCES member(member_id),
    CONSTRAINT fk_trade_seller_member
        FOREIGN KEY (seller_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE manner_review (
    manner_id BIGINT NOT NULL AUTO_INCREMENT,
    trade_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    score DOUBLE NOT NULL,
    content TEXT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (manner_id),
    UNIQUE KEY uk_manner_review_trade_buyer (trade_id, buyer_id),
    KEY idx_manner_review_seller_id (seller_id),
    CONSTRAINT fk_manner_review_trade
        FOREIGN KEY (trade_id) REFERENCES trade(trade_id),
    CONSTRAINT fk_manner_review_buyer_member
        FOREIGN KEY (buyer_id) REFERENCES member(member_id),
    CONSTRAINT fk_manner_review_seller_member
        FOREIGN KEY (seller_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE chat_room (
    chat_id BIGINT NOT NULL AUTO_INCREMENT,
    trade_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    seller_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (chat_id),
    UNIQUE KEY uk_chat_room_trade (trade_id),
    KEY idx_chat_room_buyer_id (buyer_id),
    KEY idx_chat_room_seller_id (seller_id),
    CONSTRAINT fk_chat_room_trade
        FOREIGN KEY (trade_id) REFERENCES trade(trade_id),
    CONSTRAINT fk_chat_room_buyer_member
        FOREIGN KEY (buyer_id) REFERENCES member(member_id),
    CONSTRAINT fk_chat_room_seller_member
        FOREIGN KEY (seller_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE payment (
    payment_id BIGINT NOT NULL AUTO_INCREMENT,
    trade_id BIGINT NOT NULL,
    toss_order_id VARCHAR(100) NOT NULL,
    toss_payment_key VARCHAR(200) NULL,
    amount INT NOT NULL,
    status ENUM('READY', 'PAID', 'FAILED', 'CANCELED', 'REFUNDED') NOT NULL,
    paid_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (payment_id),
    UNIQUE KEY uk_payment_trade (trade_id),
    UNIQUE KEY uk_payment_toss_order_id (toss_order_id),
    UNIQUE KEY uk_payment_toss_payment_key (toss_payment_key),
    CONSTRAINT fk_payment_trade
        FOREIGN KEY (trade_id) REFERENCES trade(trade_id)
) ENGINE=InnoDB;

CREATE TABLE point_wallet (
    point_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    balance INT NOT NULL DEFAULT 0,
    withdrawable INT NOT NULL DEFAULT 0,
    pending INT NOT NULL DEFAULT 0,
    PRIMARY KEY (point_id),
    UNIQUE KEY uk_point_wallet_member (member_id),
    CONSTRAINT fk_point_wallet_member
        FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE point_history (
    point_history_id BIGINT NOT NULL AUTO_INCREMENT,
    wallet_id BIGINT NOT NULL,
    trade_id BIGINT NULL,
    type ENUM('EARN', 'WITHDRAW') NOT NULL,
    amount INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (point_history_id),
    KEY idx_point_history_wallet_id (wallet_id),
    KEY idx_point_history_trade_id (trade_id),
    CONSTRAINT fk_point_history_wallet
        FOREIGN KEY (wallet_id) REFERENCES point_wallet(point_id),
    CONSTRAINT fk_point_history_trade
        FOREIGN KEY (trade_id) REFERENCES trade(trade_id)
) ENGINE=InnoDB;

CREATE TABLE point_request (
    withdraw_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    amount INT NOT NULL,
    bank_name VARCHAR(50) NULL,
    account_number VARCHAR(30) NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') NOT NULL,
    reject_reason VARCHAR(300) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (withdraw_id),
    KEY idx_point_request_member_id (member_id),
    KEY idx_point_request_status (status),
    CONSTRAINT fk_point_request_member
        FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE community_post (
    comm_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    sport_category ENUM('SOCCER', 'BASEBALL', 'BASKETBALL', 'VOLLEYBALL', 'ESPORTS') NOT NULL,
    team_category VARCHAR(50) NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    image_url VARCHAR(500) NULL,
    view_count INT NOT NULL DEFAULT 0,
    like_count INT NOT NULL DEFAULT 0,
    comment_count INT NOT NULL DEFAULT 0,
    status ENUM('ACTIVE', 'HIDDEN', 'DELETED') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (comm_id),
    KEY idx_community_post_member_id (member_id),
    CONSTRAINT fk_community_post_member
        FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE comment (
    comment_id BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    parent_id BIGINT NULL,
    content TEXT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    like_count INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (comment_id),
    KEY idx_comment_post_id (post_id),
    KEY idx_comment_member_id (member_id),
    KEY idx_comment_parent_id (parent_id),
    CONSTRAINT fk_comment_community_post
        FOREIGN KEY (post_id) REFERENCES community_post(comm_id),
    CONSTRAINT fk_comment_member
        FOREIGN KEY (member_id) REFERENCES member(member_id),
    CONSTRAINT fk_comment_parent
        FOREIGN KEY (parent_id) REFERENCES comment(comment_id)
) ENGINE=InnoDB;

CREATE TABLE risk_analysis_result (
    risk_id BIGINT NOT NULL AUTO_INCREMENT,
    target_type ENUM('POST', 'CHAT') NOT NULL,
    target_id BIGINT NOT NULL,
    risk_level ENUM('LOW', 'MID', 'HIGH') NOT NULL,
    reason TEXT NULL,
    suggestion TEXT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (risk_id),
    KEY idx_risk_analysis_target (target_type, target_id)
) ENGINE=InnoDB;

CREATE TABLE report (
    report_id BIGINT NOT NULL AUTO_INCREMENT,
    reporter_id BIGINT NOT NULL,
    target_type ENUM('POST', 'COMMUNITY_POST') NOT NULL,
    target_id BIGINT NOT NULL,
    reason ENUM('FAKE', 'INAPPROPRIATE', 'FRAUD', 'ETC') NOT NULL,
    detail TEXT NULL,
    status ENUM('PENDING', 'NORMAL', 'WARNING', 'DELETED') NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (report_id),
    KEY idx_report_reporter_id (reporter_id),
    KEY idx_report_target (target_type, target_id),
    CONSTRAINT fk_report_member
        FOREIGN KEY (reporter_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE TABLE notification (
    noti_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    type ENUM('TRADE', 'CHAT', 'PRICE_DROP', 'REVIEW', 'SYSTEM') NOT NULL,
    content VARCHAR(300) NOT NULL,
    link_url VARCHAR(300) NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (noti_id),
    KEY idx_notification_member_id (member_id),
    KEY idx_notification_type (type),
    CONSTRAINT fk_notification_member
        FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;

CREATE USER IF NOT EXISTS `admin`@`%` IDENTIFIED BY '0507';
GRANT ALL PRIVILEGES ON `reform_shop_2605`.* TO `admin`@`%`;