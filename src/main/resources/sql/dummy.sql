USE reform_shop_2605;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE risk_analysis_result;
TRUNCATE TABLE notification;
TRUNCATE TABLE report;
TRUNCATE TABLE reply_like;
TRUNCATE TABLE community_like;
TRUNCATE TABLE reply;
TRUNCATE TABLE community_post;
TRUNCATE TABLE point_request;
TRUNCATE TABLE point_history;
TRUNCATE TABLE point_wallet;
TRUNCATE TABLE toss_log;
TRUNCATE TABLE payment;
TRUNCATE TABLE chat_message;
TRUNCATE TABLE chat_room;
TRUNCATE TABLE manner_review;
TRUNCATE TABLE trade;
TRUNCATE TABLE wish;
TRUNCATE TABLE post_image;
TRUNCATE TABLE post;
TRUNCATE TABLE interest_keyword;
TRUNCATE TABLE interest_setting;
TRUNCATE TABLE social_member;
TRUNCATE TABLE member;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO member (
    member_id, email, password, nickname, profile_image_url, bio,
    manner_score, role, status, warning_count, email_event, created_at
) VALUES -- pw: 1234
    (1, 'admin@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '관리자계정', 'https://cdn.reform.test/profiles/admin.png', '운영자 계정', 5.00, 'ADMIN', 'ACTIVE', 0, TRUE, DATE_SUB(NOW(), INTERVAL 120 DAY)),
    (2, 'seller1@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '판매왕민수', 'https://cdn.reform.test/profiles/seller1.png', '레어 유니폼 수집가', 4.80, 'USER', 'ACTIVE', 0, TRUE, DATE_SUB(NOW(), INTERVAL 90 DAY)),
    (3, 'buyer1@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '구매요정지은', 'https://cdn.reform.test/profiles/buyer1.png', '축구 유니폼 위주로 봅니다.', 4.60, 'USER', 'ACTIVE', 0, TRUE, DATE_SUB(NOW(), INTERVAL 80 DAY)),
    (4, 'seller2@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '야구덕후현우', 'https://cdn.reform.test/profiles/seller2.png', '야구 한정판 전문', 4.30, 'USER', 'ACTIVE', 1, FALSE, DATE_SUB(NOW(), INTERVAL 70 DAY)),
    (5, 'buyer2@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '농구좋아소영', 'https://cdn.reform.test/profiles/buyer2.png', '농구 굿즈와 유니폼 환영', 4.10, 'USER', 'ACTIVE', 0, TRUE, DATE_SUB(NOW(), INTERVAL 65 DAY)),
    (6, 'suspended@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '정지회원테스트', 'https://cdn.reform.test/profiles/suspended.png', '신고 누적 회원', 2.50, 'USER', 'SUSPENDED', 3, FALSE, DATE_SUB(NOW(), INTERVAL 60 DAY)),
    (7, 'withdrawn@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '탈퇴회원샘플', NULL, '탈퇴 처리된 계정', 3.00, 'USER', 'WITHDRAWN', 0, FALSE, DATE_SUB(NOW(), INTERVAL 55 DAY)),
    (8, 'social@reform.com', '$2a$10$Birbp2MlRmeg3fZ614YDLuG/uYx6ZQEFP.gRH6diToCmJPX/WVmQe', '소셜로그인유저', 'https://cdn.reform.test/profiles/social.png', '카카오/구글 연동 테스트용', 4.55, 'USER', 'ACTIVE', 0, TRUE, DATE_SUB(NOW(), INTERVAL 50 DAY));

INSERT INTO social_member (social_id, member_id, provider, provider_id) VALUES
    (1, 8, 'KAKAO', 'kakao-10001'),
    (2, 8, 'GOOGLE', 'google-10001');

INSERT INTO interest_setting (member_id, sport, team) VALUES
    (2, 'SOCCER', 'FC 서울'),
    (3, 'SOCCER', '토트넘 홋스퍼'),
    (4, 'BASEBALL', 'LG 트윈스'),
    (5, 'BASKETBALL', '서울 SK 나이츠'),
    (8, 'ESPORTS', 'T1');

INSERT INTO interest_keyword (keyword_id, member_id, keyword) VALUES
    (1, 2, '한정판'),
    (2, 2, '마킹'),
    (3, 3, '손흥민'),
    (4, 4, '우승'),
    (5, 5, '올드유니폼'),
    (6, 8, '페이커');

INSERT INTO post (
    post_id, seller_id, title, content, sport, team, uniform_name, grade, size,
    marking, price, delivery_type, status, view_count, wish_count, risk_level, created_at, updated_at
) VALUES
    (1, 2, '토트넘 손흥민 홈 유니폼 판매', '실착 1회, 상태 매우 좋습니다.', 'SOCCER', '토트넘 홋스퍼', '2024 홈', 'S', '100', TRUE, 135000, 'DELIVERY', 'ON_SALE', 127, 2, 'LOW', DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
    (2, 2, 'FC서울 레트로 유니폼', '보관만 한 레트로 제품입니다.', 'SOCCER', 'FC 서울', '2013 레트로', 'A', '105', FALSE, 89000, 'BOTH', 'RESERVED', 84, 1, NULL, DATE_SUB(NOW(), INTERVAL 16 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (3, 4, 'LG 트윈스 우승 기념 져지', '우승 시즌 기념판입니다.', 'BASEBALL', 'LG 트윈스', '2023 한국시리즈', 'S', '105', TRUE, 179000, 'DELIVERY', 'SOLD', 233, 3, 'LOW', DATE_SUB(NOW(), INTERVAL 25 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
    (4, 4, 'KIA 타이거즈 홈 유니폼', '사용감 조금 있습니다.', 'BASEBALL', 'KIA 타이거즈', '2024 홈', 'B', '100', FALSE, 59000, 'DIRECT', 'ON_SALE', 48, 0, NULL, DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (5, 2, '서울 SK 나이츠 챔피언 져지', '사이즈 미스로 판매합니다.', 'BASKETBALL', '서울 SK 나이츠', '챔피언 에디션', 'A', '95', FALSE, 99000, 'DELIVERY', 'HIDDEN', 15, 0, 'MID', DATE_SUB(NOW(), INTERVAL 9 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (6, 8, 'T1 2025 월즈 기념 져지', '미개봉 새상품입니다.', 'ESPORTS', 'T1', '2025 월즈 우승 기념', 'S', '105', TRUE, 249000, 'DELIVERY', 'ON_SALE', 312, 2, 'LOW', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO post_image (image_id, post_id, image_url, sort_order) VALUES
    (1, 1, 'https://cdn.reform.test/posts/1-1.jpg', 1),
    (2, 1, 'https://cdn.reform.test/posts/1-2.jpg', 2),
    (3, 2, 'https://cdn.reform.test/posts/2-1.jpg', 1),
    (4, 3, 'https://cdn.reform.test/posts/3-1.jpg', 1),
    (5, 4, 'https://cdn.reform.test/posts/4-1.jpg', 1),
    (6, 6, 'https://cdn.reform.test/posts/6-1.jpg', 1);

INSERT INTO wish (wish_id, member_id, post_id, created_at) VALUES
    (1, 3, 1, DATE_SUB(NOW(), INTERVAL 4 DAY)),
    (2, 5, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (3, 3, 3, DATE_SUB(NOW(), INTERVAL 12 DAY)),
    (4, 5, 6, DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO trade (
    trade_id, post_id, buyer_id, seller_id, status, delivery_type, delivery_address,
    courier_code, courier_name, tracking_number, shipping_started_at, trade_price,
    completed_at, confirmed_at, created_at, received_at
) VALUES
    (1, 2, 3, 2, 'REQUESTED', 'DIRECT', NULL, NULL, NULL, NULL, NULL, 89000, NULL, NULL, DATE_SUB(NOW(), INTERVAL 2 DAY), NULL),
    (2, 3, 5, 4, 'COMPLETED', 'DELIVERY', '서울시 송파구 올림픽로 300', 'kr.cjlogistics', 'CJ대한통운', '111122223333', DATE_SUB(NOW(), INTERVAL 14 DAY), 179000, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY)),
    (3, 4, 3, 4, 'PAID', 'DIRECT', NULL, NULL, NULL, NULL, NULL, 59000, NULL, NULL, DATE_SUB(NOW(), INTERVAL 1 DAY), NULL),
    (4, 5, 5, 2, 'CANCELED', 'DELIVERY', '경기도 성남시 분당구 판교역로 235', NULL, NULL, NULL, NULL, 99000, NULL, NULL, DATE_SUB(NOW(), INTERVAL 5 DAY), NULL),
    (5, 6, 3, 8, 'RECEIVED', 'DELIVERY', '서울시 마포구 월드컵북로 400', 'kr.hanjin', '한진택배', '444455556666', DATE_SUB(NOW(), INTERVAL 6 DAY), 249000, NULL, NULL, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO payment (
    payment_id, trade_id, pay_method, approval_no, toss_order_id, toss_payment_key,
    amount, status, paid_at, created_at
) VALUES
    (1, 2, 'Card', 'AP-20260501-01', 'ORDER-TRADE-2', 'pay_20260501_trade2', 179000, 'PAID', DATE_SUB(NOW(), INTERVAL 14 DAY), DATE_SUB(NOW(), INTERVAL 15 DAY)),
    (2, 3, 'Pay', NULL, 'ORDER-TRADE-3', 'pay_20260512_trade3', 59000, 'PAID', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (3, 4, 'Card', 'AP-20260508-04', 'ORDER-TRADE-4', 'pay_20260508_trade4', 99000, 'CANCELED', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
    (4, 5, 'Card', 'AP-20260507-05', 'ORDER-TRADE-5', 'pay_20260507_trade5', 249000, 'PAID', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY));

INSERT INTO toss_log (log_id, payment_id, toss_payment_key, raw_response, created_at) VALUES
    (1, 1, 'pay_20260501_trade2', JSON_OBJECT('status', 'DONE', 'method', 'CARD', 'amount', 179000), DATE_SUB(NOW(), INTERVAL 14 DAY)),
    (2, 3, 'pay_20260508_trade4', JSON_OBJECT('status', 'CANCELED', 'cancelReason', 'buyer request'), DATE_SUB(NOW(), INTERVAL 4 DAY)),
    (3, 4, 'pay_20260507_trade5', JSON_OBJECT('status', 'DONE', 'method', 'CARD', 'amount', 249000), DATE_SUB(NOW(), INTERVAL 7 DAY));

INSERT INTO point_wallet (wallet_id, member_id, balance, withdrawable, pending) VALUES
    (1, 1, 0, 0, 0),
    (2, 2, 179000, 120000, 59000),
    (3, 3, 0, 0, 0),
    (4, 4, 25000, 25000, 0),
    (5, 5, 0, 0, 0),
    (6, 6, 0, 0, 0),
    (7, 7, 0, 0, 0),
    (8, 8, 249000, 149000, 100000);

INSERT INTO point_history (point_id, wallet_id, trade_id, type, change_amount, balance, created_at) VALUES
    (1, 2, 2, 'EARN', 179000, 179000, DATE_SUB(NOW(), INTERVAL 6 DAY)),
    (2, 2, NULL, 'WITHDRAW', -59000, 120000, DATE_SUB(NOW(), INTERVAL 2 DAY)),
    (3, 8, 5, 'EARN', 249000, 249000, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (4, 4, NULL, 'EARN', 25000, 25000, DATE_SUB(NOW(), INTERVAL 10 DAY));

INSERT INTO point_request (
    withdraw_id, member_id, request_amount, bank_name, account_number, status, reject_reason, created_at
) VALUES
    (1, 2, 30000, '국민은행', '123-456-7890', 'PENDING', NULL, DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (2, 2, 29000, '신한은행', '321-654-0987', 'APPROVED', NULL, DATE_SUB(NOW(), INTERVAL 10 DAY)),
    (3, 8, 50000, '카카오뱅크', '3333-12-1234567', 'REJECTED', '예금주 정보 불일치', DATE_SUB(NOW(), INTERVAL 6 DAY)),
    (4, 4, 10000, '우리은행', '1002-555-123456', 'CANCELED', NULL, DATE_SUB(NOW(), INTERVAL 4 DAY));

INSERT INTO chat_room (chat_id, trade_id, buyer_id, post_id, created_at) VALUES
    (1, NULL, 3, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (2, 2, 5, 3, DATE_SUB(NOW(), INTERVAL 15 DAY)),
    (3, 5, 3, 6, DATE_SUB(NOW(), INTERVAL 7 DAY));

INSERT INTO chat_message (message_id, chat_id, sender_id, content, type, is_read, created_at) VALUES
    (1, 1, 3, '안녕하세요. 실측 사이즈 알 수 있을까요?', 'TEXT', TRUE, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (2, 1, 2, '가슴 단면 54cm입니다.', 'TEXT', TRUE, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (3, 2, 5, '오늘 발송 가능하신가요?', 'TEXT', TRUE, DATE_SUB(NOW(), INTERVAL 14 DAY)),
    (4, 2, 4, '네, 송장 등록했습니다.', 'SYSTEM', TRUE, DATE_SUB(NOW(), INTERVAL 13 DAY)),
    (5, 3, 3, '수령 완료했습니다. 상태 좋아요.', 'TEXT', FALSE, DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO manner_review (manner_id, trade_id, buyer_id, seller_id, score, content, created_at) VALUES
    (1, 2, 5, 4, 4.5, '포장 꼼꼼하고 응답이 빨랐습니다.', DATE_SUB(NOW(), INTERVAL 5 DAY));

INSERT INTO community_post (
    comm_id, member_id, sport_category, team_category, comm_title, comm_content,
    comm_image_url, comm_view_count, like_count, comment_count, status, created_at
) VALUES
    (1, 3, 'SOCCER', '토트넘 홋스퍼', '이번 시즌 홈 유니폼 사이즈감 질문', '105 입는데 나이키 핏 어떤가요?', NULL, 56, 2, 2, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 4 DAY)),
    (2, 4, 'BASEBALL', 'LG 트윈스', '우승 져지 보관 팁 공유', '마킹 갈라짐 없이 보관하는 방법 알려드립니다.', 'https://cdn.reform.test/community/2.jpg', 101, 1, 1, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 8 DAY)),
    (3, 8, 'ESPORTS', 'T1', '월즈 기념 져지 실물 후기', '생각보다 자수 퀄리티가 아주 좋습니다.', NULL, 88, 0, 0, 'HIDDEN', DATE_SUB(NOW(), INTERVAL 2 DAY));

INSERT INTO reply (reply_id, post_id, member_id, parent_id, reply_content, is_deleted, like_count, created_at) VALUES
    (1, 1, 2, NULL, '정사이즈보다 반 치수 크게 보는 분이 많아요.', FALSE, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (2, 1, 3, 1, '답변 감사합니다!', FALSE, 0, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (3, 2, 5, NULL, '좋은 팁이네요. 사진도 보고 싶어요.', FALSE, 1, DATE_SUB(NOW(), INTERVAL 7 DAY));

INSERT INTO community_like (like_id, member_id, comm_id, created_at) VALUES
    (1, 2, 1, DATE_SUB(NOW(), INTERVAL 4 DAY)),
    (2, 5, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (3, 3, 2, DATE_SUB(NOW(), INTERVAL 7 DAY));

INSERT INTO reply_like (like_id, member_id, reply_id, created_at) VALUES
    (1, 3, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (2, 4, 3, DATE_SUB(NOW(), INTERVAL 7 DAY));

INSERT INTO report (report_id, reporter_id, target_type, target_id, reason, detail, status, created_at) VALUES
    (1, 3, 'POST', 5, 'INAPPROPRIATE', '가격 조롱성 문구가 포함되어 있습니다.', 'PENDING', DATE_SUB(NOW(), INTERVAL 2 DAY)),
    (2, 5, 'COMMUNITY_POST', 3, 'ETC', '운영 기준 확인이 필요해 보여요.', 'NORMAL', DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (3, 2, 'POST', 4, 'FAKE', '실물 사진이 부족합니다.', 'WARNING', DATE_SUB(NOW(), INTERVAL 5 DAY));

INSERT INTO notification (noti_id, member_id, type, report_content, link_url, is_read, created_at) VALUES
    (1, 2, 'TRADE', '구매자가 거래 요청을 보냈습니다.', '/trades/1', FALSE, DATE_SUB(NOW(), INTERVAL 2 DAY)),
    (2, 3, 'CHAT', '판매자가 새 메시지를 보냈습니다.', '/chat/1', TRUE, DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (3, 4, 'REVIEW', '새 매너 후기가 등록되었습니다.', '/mypage/reviews', FALSE, DATE_SUB(NOW(), INTERVAL 5 DAY)),
    (4, 8, 'SYSTEM', '출금 요청이 반려되었습니다.', '/mypage/points', FALSE, DATE_SUB(NOW(), INTERVAL 6 DAY));

INSERT INTO risk_analysis_result (risk_id, target_type, target_id, risk_level, reason, suggestion, created_at) VALUES
    (1, 'POST', 5, 'MID', '과도한 가격 유도 표현이 감지되었습니다.', '가격 산정 근거와 제품 상태를 더 구체적으로 적어주세요.', DATE_SUB(NOW(), INTERVAL 8 DAY)),
    (2, 'CHAT', 5, 'LOW', '거래 완료 후 일반 확인 메시지입니다.', '현재 메시지는 위험도가 낮아 별도 조치가 필요하지 않습니다.', DATE_SUB(NOW(), INTERVAL 2 DAY));
