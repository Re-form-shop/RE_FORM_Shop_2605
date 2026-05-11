-- Toss 결제 테스트용 데이터
-- 1. 구매자 member 삽입
INSERT INTO member (email, nickname, role, status, warning_count, email_event, manner_score, created_at)
VALUES ('buyer@test.com', '구매자', 'USER', 'ACTIVE', 0, false, 0.00, NOW());

-- 2. 판매자
INSERT INTO member (email, nickname, role, status, warning_count, email_event, manner_score, created_at)
VALUES ('seller@test.com', '판매자', 'USER', 'ACTIVE', 0, false, 0.00, NOW());

-- 3. post 삽입 (seller_id는 위에서 생성된 판매자 member_id)
INSERT INTO post (seller_id, title, content, sport, team, uniform_name, grade, price, delivery_type, status, view_count, wish_count, created_at, updated_at)
VALUES (2, '테스트 유니폼', '테스트 내용', 'SOCCER', '테스트팀', '테스트유니폼', 'A', 15000, 'DELIVERY', 'ON_SALE', 0, 0, NOW(), NOW());

-- 4. trade 삽입
INSERT INTO trade (post_id, buyer_id, seller_id, status, trade_price, created_at)
VALUES (1, 1, 2, 'ACCEPTED', 15000, NOW());



-- 포인트 / 출금 테스트용 데이터
-- 1) point_wallet 삽입 (member_id = 1 기준)
INSERT INTO point_wallet (member_id, balance, withdrawable, pending)
VALUES (1, 0, 0, 0);

-- 2) point_history 삽입
INSERT INTO point_history (wallet_id, trade_id, type, change_amount, balance, created_at)
VALUES (1, null, 'EARN', 15000, 15000, NOW());

-- 3) point_wallet withdrawable 업데이트 (출금 요청 테스트)
UPDATE point_wallet SET balance = 15000, withdrawable = 15000 WHERE member_id = 1;

-- 4) 여러 결과값 조회용 더미 데이터
-- member 추가
INSERT INTO member (email, nickname, role, status, warning_count, email_event, manner_score, created_at)
VALUES ('seller2@test.com', '판매자2', 'USER', 'ACTIVE', 0, false, 0.00, NOW());

INSERT INTO member (email, nickname, role, status, warning_count, email_event, manner_score, created_at)
VALUES ('buyer2@test.com', '구매자2', 'USER', 'ACTIVE', 0, false, 0.00, NOW());

-- point_wallet 추가
INSERT INTO point_wallet (member_id, balance, withdrawable, pending)
VALUES (2, 50000, 30000, 20000);

INSERT INTO point_wallet (member_id, balance, withdrawable, pending)
VALUES (3, 100000, 80000, 20000);

-- point_history 추가
INSERT INTO point_history (wallet_id, trade_id, type, change_amount, balance, created_at)
VALUES (3, null, 'EARN', 50000, 50000, NOW());

INSERT INTO point_history (wallet_id, trade_id, type, change_amount, balance, created_at)
VALUES (3, null, 'WITHDRAW', -20000, 30000, NOW());

INSERT INTO point_history (wallet_id, trade_id, type, change_amount, balance, created_at)
VALUES (4, null, 'EARN', 100000, 100000, NOW());