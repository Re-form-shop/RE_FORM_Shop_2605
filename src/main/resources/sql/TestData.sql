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



-- 배치 테스트용 데이터
-- 1. 자동 구매확정 테스트용
-- RECEIVED 상태 + received_at이 5일 이상 지난 거래
-- 배치 실행 후 결과 : status = 'CONFIRMED' -> 미정산 처리 진행
INSERT INTO trade (seller_id, buyer_id, post_id, status, delivery_type, trade_price, received_at, created_at)
VALUES
    (1, 2, 1, 'RECEIVED', 'DELIVERY', 75000, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
    (3, 4, 6, 'RECEIVED', 'DELIVERY', 80000, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 12 DAY)),
    (5, 6, 11, 'RECEIVED', 'DELIVERY', 85000, DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY));

-- 2. 미정산 처리 테스트용
-- CONFIRMED 상태 + point_history에 해당 trade_id 없는 거래
-- point_wallet.balance 증가, point_history.type = 'EARN', trade.status = 'COMPLETED'
INSERT INTO trade (seller_id, buyer_id, post_id, status, delivery_type, trade_price, received_at, confirmed_at, created_at)
VALUES
    (7, 8, 16, 'CONFIRMED', 'DELIVERY', 72000, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
    (9, 10, 21, 'CONFIRMED', 'DELIVERY', 88000, DATE_SUB(NOW(), INTERVAL 12 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 18 DAY));

-- 테스트 데이터 pending 업데이트
UPDATE point_wallet SET pending = 75000 WHERE member_id = 1;
UPDATE point_wallet SET pending = 80000 WHERE member_id = 3;
UPDATE point_wallet SET pending = 85000 WHERE member_id = 5;
UPDATE point_wallet SET pending = 72000 WHERE member_id = 7;
UPDATE point_wallet SET pending = 88000 WHERE member_id = 9;



-- viewPayment 테스트
-- 1. trade 더미 데이터
INSERT INTO trade (seller_id, buyer_id, post_id, status, delivery_type, trade_price, created_at)
VALUES (1, 2, 1, 'PAID', 'DELIVERY', 75000, NOW());

-- 2. payment 더미 데이터
INSERT INTO payment (trade_id, pay_method, toss_order_id, amount, status, created_at)
VALUES (LAST_INSERT_ID(), 'CARD', 'test-order-001', 75000, 'PAID', NOW());