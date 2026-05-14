-- 1. MEMBER (50명)
INSERT INTO member (email, nickname, role, status, warning_count, email_event, manner_score, created_at)
VALUES
    ('user01@reform.com', '유니폼헌터01', 'USER', 'ACTIVE', 0, true, 4.50, NOW()),
    ('user02@reform.com', '스포츠마켓02', 'USER', 'ACTIVE', 0, false, 4.20, NOW()),
    ('user03@reform.com', '유니폼셀러03', 'USER', 'ACTIVE', 0, true, 3.80, NOW()),
    ('user04@reform.com', '레어유니폼04', 'USER', 'ACTIVE', 0, true, 4.70, NOW()),
    ('user05@reform.com', '스포츠팬05', 'USER', 'ACTIVE', 0, false, 4.10, NOW()),
    ('user06@reform.com', '유니폼컬렉터06', 'USER', 'ACTIVE', 0, true, 4.90, NOW()),
    ('user07@reform.com', '중고마켓07', 'USER', 'ACTIVE', 0, false, 3.50, NOW()),
    ('user08@reform.com', '스포츠굿즈08', 'USER', 'ACTIVE', 0, true, 4.30, NOW()),
    ('user09@reform.com', '유니폼거래09', 'USER', 'ACTIVE', 0, true, 4.60, NOW()),
    ('user10@reform.com', '레플리카킹10', 'USER', 'ACTIVE', 0, false, 4.00, NOW()),
    ('user11@reform.com', '축구팬11', 'USER', 'ACTIVE', 0, true, 4.20, NOW()),
    ('user12@reform.com', '야구매니아12', 'USER', 'ACTIVE', 0, false, 3.90, NOW()),
    ('user13@reform.com', '농구러버13', 'USER', 'ACTIVE', 0, true, 4.50, NOW()),
    ('user14@reform.com', '배구팬14', 'USER', 'ACTIVE', 0, true, 4.10, NOW()),
    ('user15@reform.com', '이스포츠팬15', 'USER', 'ACTIVE', 0, false, 4.80, NOW()),
    ('user16@reform.com', '유니폼중고16', 'USER', 'ACTIVE', 0, true, 3.70, NOW()),
    ('user17@reform.com', '스포츠웨어17', 'USER', 'ACTIVE', 0, false, 4.40, NOW()),
    ('user18@reform.com', '레트로유니폼18', 'USER', 'ACTIVE', 0, true, 4.20, NOW()),
    ('user19@reform.com', '유니폼샵19', 'USER', 'ACTIVE', 0, true, 4.60, NOW()),
    ('user20@reform.com', '스포츠중고20', 'USER', 'ACTIVE', 0, false, 3.80, NOW()),
    ('user21@reform.com', '유니폼마켓21', 'USER', 'ACTIVE', 0, true, 4.30, NOW()),
    ('user22@reform.com', '굿즈헌터22', 'USER', 'ACTIVE', 0, false, 4.50, NOW()),
    ('user23@reform.com', '스포츠팬23', 'USER', 'ACTIVE', 0, true, 4.10, NOW()),
    ('user24@reform.com', '유니폼셀24', 'USER', 'ACTIVE', 0, true, 4.70, NOW()),
    ('user25@reform.com', '중고유니폼25', 'USER', 'ACTIVE', 0, false, 3.90, NOW()),
    ('user26@reform.com', '스포츠굿즈26', 'USER', 'ACTIVE', 0, true, 4.20, NOW()),
    ('user27@reform.com', '유니폼킹27', 'USER', 'ACTIVE', 0, false, 4.50, NOW()),
    ('user28@reform.com', '레어굿즈28', 'USER', 'ACTIVE', 0, true, 4.80, NOW()),
    ('user29@reform.com', '스포츠마켓29', 'USER', 'ACTIVE', 0, true, 4.00, NOW()),
    ('user30@reform.com', '유니폼컬렉30', 'USER', 'ACTIVE', 0, false, 4.30, NOW()),
    ('user31@reform.com', '야구유니폼31', 'USER', 'ACTIVE', 0, true, 4.10, NOW()),
    ('user32@reform.com', '축구중고32', 'USER', 'ACTIVE', 0, false, 4.60, NOW()),
    ('user33@reform.com', '농구굿즈33', 'USER', 'ACTIVE', 0, true, 3.80, NOW()),
    ('user34@reform.com', '스포츠웨어34', 'USER', 'ACTIVE', 0, true, 4.40, NOW()),
    ('user35@reform.com', '유니폼거래35', 'USER', 'ACTIVE', 0, false, 4.20, NOW()),
    ('user36@reform.com', '레플리카36', 'USER', 'ACTIVE', 0, true, 4.50, NOW()),
    ('user37@reform.com', '스포츠팬37', 'USER', 'ACTIVE', 0, false, 3.70, NOW()),
    ('user38@reform.com', '유니폼샵38', 'USER', 'ACTIVE', 0, true, 4.90, NOW()),
    ('user39@reform.com', '중고마켓39', 'USER', 'ACTIVE', 0, true, 4.10, NOW()),
    ('user40@reform.com', '굿즈마켓40', 'USER', 'ACTIVE', 0, false, 4.30, NOW()),
    ('user41@reform.com', '유니폼헌터41', 'USER', 'ACTIVE', 0, true, 4.60, NOW()),
    ('user42@reform.com', '스포츠중고42', 'USER', 'ACTIVE', 0, false, 4.20, NOW()),
    ('user43@reform.com', '레어유니폼43', 'USER', 'ACTIVE', 0, true, 4.50, NOW()),
    ('user44@reform.com', '유니폼마켓44', 'USER', 'ACTIVE', 0, true, 3.90, NOW()),
    ('user45@reform.com', '스포츠굿즈45', 'USER', 'ACTIVE', 0, false, 4.70, NOW()),
    ('user46@reform.com', '유니폼셀러46', 'USER', 'ACTIVE', 0, true, 4.10, NOW()),
    ('user47@reform.com', '중고유니폼47', 'USER', 'ACTIVE', 0, false, 4.40, NOW()),
    ('user48@reform.com', '스포츠팬48', 'USER', 'ACTIVE', 0, true, 4.20, NOW()),
    ('user49@reform.com', '유니폼컬렉49', 'USER', 'ACTIVE', 0, true, 4.80, NOW()),
    ('user50@reform.com', '굿즈헌터50', 'USER', 'ACTIVE', 0, false, 4.30, NOW());

-- 2. POINT_WALLET
SELECT member_id FROM member ORDER BY member_id DESC LIMIT 50; -- 확인용

INSERT INTO point_wallet (member_id, balance, withdrawable, pending)
SELECT member_id, 0, 0, 0 FROM member WHERE email LIKE 'user%@reform.com';

-- 3. POST
-- 1) BASEBALL (150개)
INSERT INTO post (seller_id, title, content, sport, team, uniform_name, grade, size, marking, price, delivery_type, status, view_count, wish_count, created_at, updated_at)
SELECT
    (SELECT member_id FROM member WHERE email LIKE 'user%@reform.com' ORDER BY RAND() LIMIT 1),
    CONCAT(d.team_name, ' ', d.uniform_name, ' 유니폼 팝니다'),
    CONCAT('상태 ', d.grade, '급 유니폼입니다. ', d.description),
    'BASEBALL', d.team_name, d.uniform_name, d.grade, d.size, d.marking, d.price, d.delivery_type, 'ON_SALE',
    FLOOR(RAND() * 500), FLOOR(RAND() * 100),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY)
FROM (
         SELECT 1 AS idx, 'KIA 타이거즈' AS team_name, '2024 홈' AS uniform_name, 'S' AS grade, '95' AS size, false AS marking, 75000 AS price, 'DELIVERY' AS delivery_type, '착용 1회, 보관 상태 완벽합니다.' AS description UNION ALL
         SELECT 2, 'KIA 타이거즈', '2024 어웨이', 'A', '100', false, 65000, 'DELIVERY', '깨끗하게 사용했습니다.' UNION ALL
         SELECT 3, 'KIA 타이거즈', '2023 홈', 'B', '105', false, 45000, 'BOTH', '약간의 사용감 있습니다.' UNION ALL
         SELECT 4, 'KIA 타이거즈', '2022 홈', 'A', '110', false, 55000, 'DELIVERY', '보관만 했습니다.' UNION ALL
         SELECT 5, 'KIA 타이거즈', '이범호 레전드 마킹', 'S', '95', true, 130000, 'DELIVERY', '레어 마킹 유니폼입니다.' UNION ALL
         SELECT 6, 'KIA 타이거즈', '나성범 마킹', 'A', '100', true, 90000, 'DELIVERY', '마킹 선명합니다.' UNION ALL
         SELECT 7, 'KIA 타이거즈', '챔피언스 에디션 2024', 'S', '105', false, 120000, 'DELIVERY', '우승 기념 한정판입니다.' UNION ALL
         SELECT 8, 'KIA 타이거즈', '최형우 마킹', 'A', '100', true, 92000, 'BOTH', '마킹 상태 좋습니다.' UNION ALL
         SELECT 9, 'KIA 타이거즈', '팬 에디션 홈', 'S', '95', false, 85000, 'DELIVERY', '직접 구매한 정품입니다.' UNION ALL
         SELECT 10, 'KIA 타이거즈', '2021 홈', 'B', '110', false, 32000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 11, '삼성 라이온즈', '2024 홈', 'S', '100', false, 80000, 'DELIVERY', '착용 2회 상태 좋습니다.' UNION ALL
         SELECT 12, '삼성 라이온즈', '2024 어웨이', 'A', '95', false, 68000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 13, '삼성 라이온즈', '오승환 마킹', 'S', '100', true, 160000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 14, '삼성 라이온즈', '구자욱 마킹', 'A', '100', true, 95000, 'DELIVERY', '정품 마킹 선명합니다.' UNION ALL
         SELECT 15, '삼성 라이온즈', '이승엽 레전드', 'S', '95', true, 250000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 16, '삼성 라이온즈', '레트로 1990', 'S', '105', false, 110000, 'BOTH', '레트로 한정판입니다.' UNION ALL
         SELECT 17, '삼성 라이온즈', '블루 에디션', 'S', '100', false, 88000, 'DELIVERY', '한정 에디션 정품입니다.' UNION ALL
         SELECT 18, '삼성 라이온즈', '2023 홈', 'B', '110', false, 42000, 'DELIVERY', '가격 협의 가능합니다.' UNION ALL
         SELECT 19, '삼성 라이온즈', '라이온 클래식', 'A', '100', false, 72000, 'BOTH', '빠른 거래 원합니다.' UNION ALL
         SELECT 20, '삼성 라이온즈', '2022 어웨이', 'A', '95', false, 58000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 21, 'LG 트윈스', '2024 홈', 'S', '100', false, 85000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 22, 'LG 트윈스', '2023 한국시리즈', 'S', '105', true, 180000, 'DELIVERY', '우승 기념 유니폼입니다.' UNION ALL
         SELECT 23, 'LG 트윈스', '오지환 마킹', 'A', '100', true, 95000, 'BOTH', '마킹 상태 완벽합니다.' UNION ALL
         SELECT 24, 'LG 트윈스', '박해민 마킹', 'S', '100', true, 88000, 'DELIVERY', '정품 마킹입니다.' UNION ALL
         SELECT 25, 'LG 트윈스', '블랙 에디션', 'S', '95', false, 98000, 'DELIVERY', '한정 에디션입니다.' UNION ALL
         SELECT 26, 'LG 트윈스', '2024 어웨이', 'A', '110', false, 70000, 'BOTH', '보관만 했습니다.' UNION ALL
         SELECT 27, 'LG 트윈스', '레트로 홈', 'B', '100', false, 40000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 28, 'LG 트윈스', '트윈스 클래식', 'A', '105', false, 75000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 29, 'LG 트윈스', '이형종 마킹', 'A', '100', true, 85000, 'BOTH', '마킹 상태 좋습니다.' UNION ALL
         SELECT 30, 'LG 트윈스', '2022 홈', 'B', '95', false, 38000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 31, '두산 베어스', '2024 홈', 'S', '100', false, 82000, 'DELIVERY', '구매 후 1회 착용했습니다.' UNION ALL
         SELECT 32, '두산 베어스', '김재환 마킹', 'A', '105', true, 98000, 'BOTH', '마킹 선명합니다.' UNION ALL
         SELECT 33, '두산 베어스', '2024 어웨이', 'A', '95', false, 70000, 'DELIVERY', '보관 상태 좋습니다.' UNION ALL
         SELECT 34, '두산 베어스', '양의지 마킹', 'S', '100', true, 110000, 'DELIVERY', '정품 마킹입니다.' UNION ALL
         SELECT 35, '두산 베어스', '곰돌이 에디션', 'S', '110', false, 95000, 'BOTH', '한정 에디션 정품입니다.' UNION ALL
         SELECT 36, '두산 베어스', '레트로 베어스', 'A', '100', false, 72000, 'DELIVERY', '레트로 스타일입니다.' UNION ALL
         SELECT 37, '두산 베어스', '2023 홈', 'B', '95', false, 40000, 'DELIVERY', '저렴하게 드립니다.' UNION ALL
         SELECT 38, '두산 베어스', '허경민 마킹', 'B', '105', true, 55000, 'BOTH', '가격 협의 가능합니다.' UNION ALL
         SELECT 39, '두산 베어스', '베어스 스페셜', 'S', '100', false, 88000, 'DELIVERY', '스페셜 에디션입니다.' UNION ALL
         SELECT 40, '두산 베어스', '2022 한국시리즈', 'S', '100', false, 120000, 'DELIVERY', '플레이오프 기념 유니폼입니다.' UNION ALL
         SELECT 41, 'SSG 랜더스', '2024 홈', 'S', '100', false, 88000, 'DELIVERY', '신품 상태입니다.' UNION ALL
         SELECT 42, 'SSG 랜더스', '추신수 마킹', 'S', '105', true, 210000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 43, 'SSG 랜더스', '2022 통합우승', 'S', '95', false, 150000, 'BOTH', '우승 기념 한정판입니다.' UNION ALL
         SELECT 44, 'SSG 랜더스', '최정 마킹', 'A', '100', true, 95000, 'DELIVERY', '정품 마킹입니다.' UNION ALL
         SELECT 45, 'SSG 랜더스', '김광현 마킹', 'S', '110', true, 130000, 'DELIVERY', '레전드 마킹입니다.' UNION ALL
         SELECT 46, 'SSG 랜더스', '랜더스 스페셜', 'S', '100', false, 105000, 'BOTH', '스페셜 에디션 정품입니다.' UNION ALL
         SELECT 47, 'SSG 랜더스', '2024 어웨이', 'A', '95', false, 72000, 'DELIVERY', '보관만 했습니다.' UNION ALL
         SELECT 48, 'SSG 랜더스', '레드 에디션', 'S', '100', false, 98000, 'DELIVERY', '한정 에디션입니다.' UNION ALL
         SELECT 49, 'SSG 랜더스', '2023 홈', 'B', '105', false, 45000, 'BOTH', '세탁 후 보관했습니다.' UNION ALL
         SELECT 50, 'SSG 랜더스', '한유섬 마킹', 'A', '100', true, 88000, 'DELIVERY', '마킹 선명합니다.' UNION ALL
         SELECT 51, '롯데 자이언츠', '2024 홈', 'S', '100', false, 78000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 52, '롯데 자이언츠', '이대호 레전드', 'S', '105', true, 190000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 53, '롯데 자이언츠', '손아섭 마킹', 'A', '95', true, 88000, 'BOTH', '마킹 선명합니다.' UNION ALL
         SELECT 54, '롯데 자이언츠', '전준우 마킹', 'B', '100', true, 60000, 'DELIVERY', '가격 협의 가능합니다.' UNION ALL
         SELECT 55, '롯데 자이언츠', '자이언츠 클래식', 'S', '110', false, 95000, 'DELIVERY', '클래식 에디션 정품입니다.' UNION ALL
         SELECT 56, '롯데 자이언츠', '2024 어웨이', 'A', '100', false, 65000, 'BOTH', '보관만 했습니다.' UNION ALL
         SELECT 57, '롯데 자이언츠', '레트로 홈', 'B', '95', false, 38000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 58, '롯데 자이언츠', '블루 에디션', 'S', '100', false, 85000, 'DELIVERY', '한정 에디션입니다.' UNION ALL
         SELECT 59, '롯데 자이언츠', '부산 갈매기 에디션', 'A', '105', false, 82000, 'BOTH', '부산 한정 에디션입니다.' UNION ALL
         SELECT 60, '롯데 자이언츠', '2022 홈', 'A', '100', false, 60000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 61, 'NC 다이노스', '2024 홈', 'S', '100', false, 82000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 62, 'NC 다이노스', '2020 한국시리즈', 'S', '105', true, 165000, 'DELIVERY', '우승 기념 유니폼입니다.' UNION ALL
         SELECT 63, 'NC 다이노스', '양의지 마킹', 'A', '95', true, 98000, 'BOTH', '마킹 선명 정품입니다.' UNION ALL
         SELECT 64, 'NC 다이노스', '나성범 마킹', 'A', '100', true, 90000, 'DELIVERY', '마킹 상태 양호합니다.' UNION ALL
         SELECT 65, 'NC 다이노스', '다크 에디션', 'S', '110', false, 95000, 'DELIVERY', '한정 에디션 정품입니다.' UNION ALL
         SELECT 66, 'NC 다이노스', '2024 어웨이', 'A', '100', false, 70000, 'BOTH', '보관 상태 좋습니다.' UNION ALL
         SELECT 67, 'NC 다이노스', '다이노스 스페셜', 'S', '95', false, 100000, 'DELIVERY', '스페셜 에디션입니다.' UNION ALL
         SELECT 68, 'NC 다이노스', '창원 에디션', 'S', '100', false, 88000, 'DELIVERY', '창원 한정 에디션입니다.' UNION ALL
         SELECT 69, 'NC 다이노스', '2022 홈', 'B', '105', false, 40000, 'BOTH', '세탁 후 보관했습니다.' UNION ALL
         SELECT 70, 'NC 다이노스', '레트로 유니폼', 'A', '100', false, 68000, 'DELIVERY', '레트로 스타일입니다.' UNION ALL
         SELECT 71, 'KT 위즈', '2024 홈', 'S', '100', false, 80000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 72, 'KT 위즈', '2021 한국시리즈', 'S', '105', true, 160000, 'DELIVERY', '우승 기념 유니폼입니다.' UNION ALL
         SELECT 73, 'KT 위즈', '강백호 마킹', 'A', '95', true, 95000, 'BOTH', '마킹 선명합니다.' UNION ALL
         SELECT 74, 'KT 위즈', '황재균 마킹', 'B', '100', true, 60000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 75, 'KT 위즈', '박병호 마킹', 'S', '110', true, 115000, 'DELIVERY', '레전드 마킹입니다.' UNION ALL
         SELECT 76, 'KT 위즈', '레드 에디션', 'S', '100', false, 92000, 'BOTH', '한정 에디션 정품입니다.' UNION ALL
         SELECT 77, 'KT 위즈', '위즈 스페셜', 'A', '95', false, 72000, 'DELIVERY', '스페셜 에디션입니다.' UNION ALL
         SELECT 78, 'KT 위즈', '수원 에디션', 'A', '100', false, 75000, 'DELIVERY', '수원 한정 에디션입니다.' UNION ALL
         SELECT 79, 'KT 위즈', '2024 어웨이', 'A', '105', false, 68000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 80, 'KT 위즈', '레트로 유니폼', 'B', '100', false, 42000, 'DELIVERY', '레트로 스타일입니다.' UNION ALL
         SELECT 81, '한화 이글스', '2024 홈', 'S', '100', false, 78000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 82, '한화 이글스', '류현진 마킹', 'S', '105', true, 230000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 83, '한화 이글스', '채은성 마킹', 'A', '95', true, 90000, 'BOTH', '마킹 선명합니다.' UNION ALL
         SELECT 84, '한화 이글스', '김태균 레전드', 'S', '100', true, 180000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 85, '한화 이글스', '이글스 클래식', 'S', '110', false, 88000, 'DELIVERY', '클래식 에디션 정품입니다.' UNION ALL
         SELECT 86, '한화 이글스', '노란 이글스 에디션', 'S', '100', false, 95000, 'BOTH', '한정 에디션입니다.' UNION ALL
         SELECT 87, '한화 이글스', '대전 에디션', 'S', '95', false, 85000, 'DELIVERY', '대전 한정 에디션입니다.' UNION ALL
         SELECT 88, '한화 이글스', '2024 어웨이', 'A', '105', false, 65000, 'DELIVERY', '보관만 했습니다.' UNION ALL
         SELECT 89, '한화 이글스', '정은원 마킹', 'A', '100', true, 82000, 'BOTH', '마킹 상태 양호합니다.' UNION ALL
         SELECT 90, '한화 이글스', '2022 홈', 'B', '100', false, 38000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 91, '키움 히어로즈', '2024 홈', 'S', '100', false, 75000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 92, '키움 히어로즈', '이정후 마킹', 'S', '105', true, 200000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 93, '키움 히어로즈', '안우진 마킹', 'A', '95', true, 88000, 'BOTH', '마킹 선명합니다.' UNION ALL
         SELECT 94, '키움 히어로즈', '박동원 마킹', 'A', '100', true, 82000, 'DELIVERY', '마킹 상태 양호합니다.' UNION ALL
         SELECT 95, '키움 히어로즈', '히어로즈 클래식', 'S', '110', false, 92000, 'DELIVERY', '클래식 에디션 정품입니다.' UNION ALL
         SELECT 96, '키움 히어로즈', '서울 에디션', 'A', '100', false, 78000, 'BOTH', '서울 한정 에디션입니다.' UNION ALL
         SELECT 97, '키움 히어로즈', '레트로 히어로즈', 'A', '95', false, 68000, 'DELIVERY', '레트로 스타일입니다.' UNION ALL
         SELECT 98, '키움 히어로즈', '2024 어웨이', 'A', '105', false, 62000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 99, '키움 히어로즈', '히어로즈 스페셜', 'S', '100', false, 85000, 'BOTH', '스페셜 에디션입니다.' UNION ALL
         SELECT 100, '키움 히어로즈', '2022 홈', 'B', '100', false, 38000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 101, 'KIA 타이거즈', '어린이날 에디션', 'S', '100', false, 88000, 'DELIVERY', '어린이날 한정 에디션입니다.' UNION ALL
         SELECT 102, '삼성 라이온즈', '개막전 에디션', 'S', '95', false, 92000, 'BOTH', '개막전 기념 유니폼입니다.' UNION ALL
         SELECT 103, 'LG 트윈스', '올스타전 에디션', 'A', '105', false, 78000, 'DELIVERY', '올스타전 기념 유니폼입니다.' UNION ALL
         SELECT 104, '두산 베어스', '포스트시즌 에디션', 'S', '100', false, 110000, 'DELIVERY', '포스트시즌 기념입니다.' UNION ALL
         SELECT 105, 'SSG 랜더스', '인천 콜라보 에디션', 'S', '110', false, 120000, 'BOTH', '콜라보 한정 에디션입니다.' UNION ALL
         SELECT 106, '롯데 자이언츠', '2019 홈', 'A', '100', false, 52000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 107, 'NC 다이노스', '2019 어웨이', 'B', '95', false, 38000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 108, 'KT 위즈', '2020 홈', 'A', '100', false, 60000, 'BOTH', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 109, '한화 이글스', '2020 어웨이', 'B', '105', false, 40000, 'DELIVERY', '가격 협의 가능합니다.' UNION ALL
         SELECT 110, '키움 히어로즈', '2019 홈', 'A', '100', false, 58000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 111, 'KIA 타이거즈', '2019 어웨이', 'B', '95', false, 36000, 'DELIVERY', '저렴하게 드립니다.' UNION ALL
         SELECT 112, '삼성 라이온즈', '2020 홈', 'A', '100', false, 58000, 'BOTH', '보관 상태 좋습니다.' UNION ALL
         SELECT 113, 'LG 트윈스', '2019 홈', 'B', '105', false, 38000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 114, '두산 베어스', '2020 어웨이', 'A', '100', false, 62000, 'DELIVERY', '깔끔합니다.' UNION ALL
         SELECT 115, 'SSG 랜더스', '2019 홈', 'S', '95', false, 80000, 'BOTH', '신품급 상태입니다.' UNION ALL
         SELECT 116, '롯데 자이언츠', '2020 어웨이', 'B', '110', false, 36000, 'DELIVERY', '저렴하게 드립니다.' UNION ALL
         SELECT 117, 'NC 다이노스', '2020 홈', 'A', '100', false, 60000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 118, 'KT 위즈', '2019 어웨이', 'S', '95', false, 78000, 'BOTH', '신품급 상태입니다.' UNION ALL
         SELECT 119, '한화 이글스', '2019 홈', 'B', '105', false, 38000, 'DELIVERY', '가격 협의 가능합니다.' UNION ALL
         SELECT 120, '키움 히어로즈', '2020 어웨이', 'A', '100', false, 58000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 121, 'KIA 타이거즈', '소년 KIA 에디션', 'S', '100', false, 110000, 'DELIVERY', '한정 에디션 정품입니다.' UNION ALL
         SELECT 122, '삼성 라이온즈', '2024 플레이오프', 'S', '105', false, 98000, 'BOTH', '플레이오프 기념입니다.' UNION ALL
         SELECT 123, 'LG 트윈스', '2024 플레이오프', 'S', '95', false, 105000, 'DELIVERY', '플레이오프 기념입니다.' UNION ALL
         SELECT 124, '두산 베어스', '2024 플레이오프', 'A', '100', false, 85000, 'DELIVERY', '플레이오프 기념입니다.' UNION ALL
         SELECT 125, 'SSG 랜더스', '2024 플레이오프', 'S', '110', false, 115000, 'BOTH', '플레이오프 기념입니다.' UNION ALL
         SELECT 126, '롯데 자이언츠', '2023 어웨이', 'B', '100', false, 42000, 'DELIVERY', '착용감 좋습니다.' UNION ALL
         SELECT 127, 'NC 다이노스', '손시헌 마킹', 'B', '95', true, 58000, 'DELIVERY', '가격 협의 가능합니다.' UNION ALL
         SELECT 128, 'KT 위즈', '2023 홈', 'B', '100', false, 42000, 'BOTH', '세탁 후 보관했습니다.' UNION ALL
         SELECT 129, '한화 이글스', '2023 어웨이', 'B', '105', false, 40000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 130, '키움 히어로즈', '2023 홈', 'A', '100', false, 62000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 131, 'KIA 타이거즈', '2024 스페셜 홈', 'S', '110', false, 98000, 'DELIVERY', '스페셜 에디션 정품입니다.' UNION ALL
         SELECT 132, '삼성 라이온즈', '2024 스페셜 어웨이', 'S', '100', false, 95000, 'BOTH', '한정 에디션입니다.' UNION ALL
         SELECT 133, 'LG 트윈스', '2024 스페셜 홈', 'S', '95', false, 100000, 'DELIVERY', '스페셜 에디션입니다.' UNION ALL
         SELECT 134, '두산 베어스', '2024 스페셜 어웨이', 'A', '105', false, 78000, 'DELIVERY', '한정 에디션입니다.' UNION ALL
         SELECT 135, 'SSG 랜더스', '2024 스페셜 홈', 'S', '100', false, 105000, 'BOTH', '스페셜 에디션 정품입니다.' UNION ALL
         SELECT 136, '롯데 자이언츠', '2024 스페셜 어웨이', 'A', '110', false, 72000, 'DELIVERY', '한정 에디션입니다.' UNION ALL
         SELECT 137, 'NC 다이노스', '2024 스페셜 홈', 'S', '100', false, 98000, 'DELIVERY', '스페셜 에디션입니다.' UNION ALL
         SELECT 138, 'KT 위즈', '2024 스페셜 어웨이', 'A', '95', false, 75000, 'BOTH', '한정 에디션입니다.' UNION ALL
         SELECT 139, '한화 이글스', '2024 스페셜 홈', 'S', '100', false, 92000, 'DELIVERY', '스페셜 에디션 정품입니다.' UNION ALL
         SELECT 140, '키움 히어로즈', '2024 스페셜 어웨이', 'A', '105', false, 70000, 'DELIVERY', '한정 에디션입니다.' UNION ALL
         SELECT 141, 'KIA 타이거즈', '2020 어웨이', 'B', '100', false, 34000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 142, '삼성 라이온즈', '2019 어웨이', 'A', '95', false, 55000, 'BOTH', '보관 상태 좋습니다.' UNION ALL
         SELECT 143, 'LG 트윈스', '2020 홈', 'B', '105', false, 38000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 144, '두산 베어스', '2019 홈', 'S', '100', false, 80000, 'DELIVERY', '신품급입니다.' UNION ALL
         SELECT 145, 'SSG 랜더스', '2020 어웨이', 'A', '110', false, 65000, 'BOTH', '깔끔합니다.' UNION ALL
         SELECT 146, '롯데 자이언츠', '2021 어웨이', 'B', '95', false, 36000, 'DELIVERY', '저렴하게 드립니다.' UNION ALL
         SELECT 147, 'NC 다이노스', '2021 홈', 'A', '100', false, 62000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 148, 'KT 위즈', '2020 어웨이', 'S', '105', false, 80000, 'BOTH', '신품급 상태입니다.' UNION ALL
         SELECT 149, '한화 이글스', '2021 어웨이', 'B', '100', false, 40000, 'DELIVERY', '가격 협의 가능합니다.' UNION ALL
         SELECT 150, '키움 히어로즈', '2021 어웨이', 'A', '95', false, 60000, 'DELIVERY', '깔끔하게 사용했습니다.'
     ) AS d;

-- 2) SOCCER (120개)
INSERT INTO post (seller_id, title, content, sport, team, uniform_name, grade, size, marking, price, delivery_type, status, view_count, wish_count, created_at, updated_at)
SELECT
    (SELECT member_id FROM member WHERE email LIKE 'user%@reform.com' ORDER BY RAND() LIMIT 1),
    CONCAT(d.team_name, ' ', d.uniform_name, ' 유니폼 팝니다'),
    CONCAT('상태 ', d.grade, '급 유니폼입니다. ', d.description),
    'SOCCER', d.team_name, d.uniform_name, d.grade, d.size, d.marking, d.price, d.delivery_type, 'ON_SALE',
    FLOOR(RAND() * 500), FLOOR(RAND() * 100),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY)
FROM (
         SELECT 1 AS idx, '토트넘 홋스퍼' AS team_name, '23/24 홈' AS uniform_name, 'S' AS grade, '95' AS size, false AS marking, 88000 AS price, 'DELIVERY' AS delivery_type, '착용 1회, 보관 상태 완벽합니다.' AS description UNION ALL
         SELECT 2, '토트넘 홋스퍼', '손흥민 마킹 홈', 'S', '100', true, 160000, 'DELIVERY', '손흥민 정품 마킹 유니폼입니다.' UNION ALL
         SELECT 3, '토트넘 홋스퍼', '23/24 어웨이', 'A', '105', false, 75000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 4, '토트넘 홋스퍼', '22/23 홈', 'B', '95', false, 48000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 5, '토트넘 홋스퍼', '손흥민 어웨이 마킹', 'S', '100', true, 155000, 'DELIVERY', '손흥민 어웨이 마킹 정품입니다.' UNION ALL
         SELECT 6, '토트넘 홋스퍼', '21/22 홈', 'A', '110', false, 62000, 'BOTH', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 7, '토트넘 홋스퍼', '챔피언스리그 에디션', 'S', '95', false, 140000, 'DELIVERY', '챔피언스리그 한정판입니다.' UNION ALL
         SELECT 8, '토트넘 홋스퍼', '케인 마킹 홈', 'A', '100', true, 110000, 'DELIVERY', '케인 마킹 정품입니다.' UNION ALL
         SELECT 9, '토트넘 홋스퍼', '20/21 어웨이', 'B', '105', false, 42000, 'BOTH', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 10, '토트넘 홋스퍼', '써드킷 23/24', 'S', '100', false, 95000, 'DELIVERY', '써드킷 한정 에디션입니다.' UNION ALL
         SELECT 11, 'PSG', '23/24 홈', 'S', '100', false, 95000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 12, 'PSG', '이강인 마킹', 'S', '105', true, 185000, 'DELIVERY', '이강인 정품 마킹 유니폼입니다.' UNION ALL
         SELECT 13, 'PSG', '음바페 마킹 홈', 'S', '95', true, 210000, 'BOTH', '음바페 마킹 정품입니다.' UNION ALL
         SELECT 14, 'PSG', '23/24 어웨이', 'A', '100', false, 80000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 15, 'PSG', '22/23 홈', 'B', '110', false, 50000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 16, 'PSG', '챔피언스리그 에디션', 'S', '100', false, 138000, 'BOTH', '챔피언스리그 한정판입니다.' UNION ALL
         SELECT 17, 'PSG', '네이마르 마킹 레전드', 'S', '95', true, 220000, 'DELIVERY', '네이마르 레전드 마킹입니다.' UNION ALL
         SELECT 18, 'PSG', '조던 콜라보 에디션', 'S', '100', false, 165000, 'DELIVERY', '조던 콜라보 한정판입니다.' UNION ALL
         SELECT 19, 'PSG', '21/22 어웨이', 'A', '105', false, 68000, 'BOTH', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 20, 'PSG', '20/21 홈', 'B', '100', false, 45000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 21, '맨체스터 시티', '23/24 홈', 'S', '100', false, 92000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 22, '맨체스터 시티', '홀란드 마킹', 'S', '105', true, 178000, 'DELIVERY', '홀란드 정품 마킹입니다.' UNION ALL
         SELECT 23, '맨체스터 시티', '23/24 어웨이', 'A', '95', false, 78000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 24, '맨체스터 시티', '챔피언스리그 우승 에디션', 'S', '100', false, 250000, 'DELIVERY', '우승 기념 한정판입니다.' UNION ALL
         SELECT 25, '맨체스터 시티', '데브라위너 마킹', 'A', '110', true, 112000, 'DELIVERY', '데브라위너 마킹 정품입니다.' UNION ALL
         SELECT 26, '맨체스터 시티', '22/23 홈', 'B', '100', false, 50000, 'BOTH', '세탁 후 보관했습니다.' UNION ALL
         SELECT 27, '맨체스터 시티', '아구에로 레전드 마킹', 'S', '95', true, 195000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 28, '맨체스터 시티', '써드킷 23/24', 'S', '100', false, 98000, 'DELIVERY', '써드킷 한정 에디션입니다.' UNION ALL
         SELECT 29, '맨체스터 시티', '21/22 어웨이', 'A', '105', false, 72000, 'BOTH', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 30, '맨체스터 시티', '20/21 홈', 'B', '100', false, 46000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 31, '레알 마드리드', '23/24 홈', 'S', '100', false, 98000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 32, '레알 마드리드', '벨링엄 마킹', 'S', '105', true, 188000, 'DELIVERY', '벨링엄 정품 마킹입니다.' UNION ALL
         SELECT 33, '레알 마드리드', '비니시우스 마킹', 'A', '95', true, 128000, 'BOTH', '비니시우스 마킹 정품입니다.' UNION ALL
         SELECT 34, '레알 마드리드', '챔피언스리그 에디션', 'S', '100', false, 148000, 'DELIVERY', '챔피언스리그 한정판입니다.' UNION ALL
         SELECT 35, '레알 마드리드', '23/24 어웨이', 'A', '110', false, 85000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 36, '레알 마드리드', '호날두 레전드 마킹', 'S', '100', true, 280000, 'BOTH', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 37, '레알 마드리드', '22/23 홈', 'B', '95', false, 55000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 38, '레알 마드리드', '모드리치 마킹', 'A', '100', true, 115000, 'DELIVERY', '모드리치 마킹 정품입니다.' UNION ALL
         SELECT 39, '레알 마드리드', '써드킷 23/24', 'S', '105', false, 102000, 'BOTH', '써드킷 한정 에디션입니다.' UNION ALL
         SELECT 40, '레알 마드리드', '21/22 어웨이', 'A', '100', false, 75000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 41, 'FC 바르셀로나', '23/24 홈', 'S', '100', false, 95000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 42, 'FC 바르셀로나', '야말 마킹', 'S', '105', true, 172000, 'DELIVERY', '야말 정품 마킹입니다.' UNION ALL
         SELECT 43, 'FC 바르셀로나', '레반도프스키 마킹', 'A', '95', true, 118000, 'BOTH', '레반도프스키 마킹 정품입니다.' UNION ALL
         SELECT 44, 'FC 바르셀로나', '메시 레전드 마킹', 'S', '100', true, 320000, 'DELIVERY', '메시 레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 45, 'FC 바르셀로나', '23/24 어웨이', 'A', '110', false, 80000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 46, 'FC 바르셀로나', '챔피언스리그 에디션', 'S', '100', false, 138000, 'BOTH', '챔피언스리그 한정판입니다.' UNION ALL
         SELECT 47, 'FC 바르셀로나', '22/23 홈', 'B', '95', false, 52000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 48, 'FC 바르셀로나', '페드리 마킹', 'A', '100', true, 108000, 'DELIVERY', '페드리 마킹 정품입니다.' UNION ALL
         SELECT 49, 'FC 바르셀로나', '써드킷 23/24', 'S', '105', false, 100000, 'BOTH', '써드킷 한정 에디션입니다.' UNION ALL
         SELECT 50, 'FC 바르셀로나', '21/22 어웨이', 'A', '100', false, 72000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 51, '맨체스터 유나이티드', '23/24 홈', 'S', '100', false, 90000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 52, '맨체스터 유나이티드', '라쉬포드 마킹', 'A', '105', true, 108000, 'DELIVERY', '라쉬포드 마킹 정품입니다.' UNION ALL
         SELECT 53, '맨체스터 유나이티드', '박지성 레전드 마킹', 'S', '95', true, 260000, 'BOTH', '박지성 레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 54, '맨체스터 유나이티드', '루니 레전드 마킹', 'S', '100', true, 230000, 'DELIVERY', '루니 레전드 마킹입니다.' UNION ALL
         SELECT 55, '맨체스터 유나이티드', '23/24 어웨이', 'A', '110', false, 76000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 56, '맨체스터 유나이티드', '22/23 홈', 'B', '100', false, 50000, 'BOTH', '세탁 후 보관했습니다.' UNION ALL
         SELECT 57, '맨체스터 유나이티드', '써드킷 23/24', 'S', '95', false, 96000, 'DELIVERY', '써드킷 한정 에디션입니다.' UNION ALL
         SELECT 58, '맨체스터 유나이티드', '21/22 어웨이', 'A', '105', false, 70000, 'BOTH', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 59, '맨체스터 유나이티드', '퍼디난드 레전드', 'A', '100', true, 115000, 'DELIVERY', '레전드 마킹입니다.' UNION ALL
         SELECT 60, '맨체스터 유나이티드', '20/21 홈', 'B', '100', false, 46000, 'DELIVERY', '가격 저렴하게 드립니다.' UNION ALL
         SELECT 61, '리버풀', '23/24 홈', 'S', '100', false, 92000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 62, '리버풀', '살라 마킹', 'S', '105', true, 168000, 'DELIVERY', '살라 정품 마킹입니다.' UNION ALL
         SELECT 63, '리버풀', '누녜스 마킹', 'A', '95', true, 112000, 'BOTH', '누녜스 마킹 정품입니다.' UNION ALL
         SELECT 64, '리버풀', '제라드 레전드 마킹', 'S', '100', true, 245000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 65, '리버풀', '23/24 어웨이', 'A', '110', false, 78000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 66, '리버풀', '챔피언스리그 에디션', 'S', '100', false, 135000, 'BOTH', '챔피언스리그 한정판입니다.' UNION ALL
         SELECT 67, '리버풀', '22/23 홈', 'B', '95', false, 50000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 68, '리버풀', '알리슨 마킹', 'A', '100', true, 98000, 'DELIVERY', '알리슨 마킹 정품입니다.' UNION ALL
         SELECT 69, '리버풀', '써드킷 23/24', 'S', '105', false, 98000, 'BOTH', '써드킷 한정 에디션입니다.' UNION ALL
         SELECT 70, '리버풀', '21/22 어웨이', 'A', '100', false, 72000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 71, '바이에른 뮌헨', '23/24 홈', 'S', '100', false, 95000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 72, '바이에른 뮌헨', '케인 마킹', 'S', '105', true, 172000, 'DELIVERY', '케인 정품 마킹입니다.' UNION ALL
         SELECT 73, '바이에른 뮌헨', '뮐러 마킹', 'A', '95', true, 110000, 'BOTH', '뮐러 마킹 정품입니다.' UNION ALL
         SELECT 74, '바이에른 뮌헨', '레반도프스키 레전드', 'S', '100', true, 200000, 'DELIVERY', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 75, '바이에른 뮌헨', '23/24 어웨이', 'A', '110', false, 80000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 76, '바이에른 뮌헨', '챔피언스리그 에디션', 'S', '100', false, 140000, 'BOTH', '챔피언스리그 한정판입니다.' UNION ALL
         SELECT 77, '바이에른 뮌헨', '노이어 마킹', 'A', '95', true, 105000, 'DELIVERY', '노이어 마킹 정품입니다.' UNION ALL
         SELECT 78, '바이에른 뮌헨', '써드킷 23/24', 'S', '105', false, 100000, 'DELIVERY', '써드킷 한정 에디션입니다.' UNION ALL
         SELECT 79, '바이에른 뮌헨', '22/23 홈', 'B', '100', false, 52000, 'BOTH', '세탁 후 보관했습니다.' UNION ALL
         SELECT 80, '바이에른 뮌헨', '21/22 어웨이', 'A', '100', false, 74000, 'DELIVERY', '깔끔하게 사용했습니다.' UNION ALL
         SELECT 81, '첼시', '23/24 홈', 'S', '100', false, 88000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 82, '첼시', '팔머 마킹', 'S', '105', true, 158000, 'DELIVERY', '팔머 정품 마킹입니다.' UNION ALL
         SELECT 83, '첼시', '드로그바 레전드 마킹', 'S', '95', true, 205000, 'BOTH', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 84, '첼시', '23/24 어웨이', 'A', '100', false, 74000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 85, '첼시', '22/23 홈', 'B', '110', false, 48000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 86, '아스날', '23/24 홈', 'S', '100', false, 90000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 87, '아스날', '사카 마킹', 'S', '105', true, 162000, 'DELIVERY', '사카 정품 마킹입니다.' UNION ALL
         SELECT 88, '아스날', '외데고르 마킹', 'A', '95', true, 115000, 'BOTH', '외데고르 마킹 정품입니다.' UNION ALL
         SELECT 89, '아스날', '23/24 어웨이', 'A', '100', false, 76000, 'DELIVERY', '보관 상태 양호합니다.' UNION ALL
         SELECT 90, '아스날', '22/23 홈', 'B', '110', false, 48000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 91, '대한민국 국가대표', '2024 홈', 'S', '100', false, 85000, 'DELIVERY', '국대 홈 유니폼 정품입니다.' UNION ALL
         SELECT 92, '대한민국 국가대표', '손흥민 마킹 홈', 'S', '105', true, 175000, 'DELIVERY', '손흥민 국대 마킹 정품입니다.' UNION ALL
         SELECT 93, '대한민국 국가대표', '이강인 마킹', 'A', '95', true, 135000, 'BOTH', '이강인 국대 마킹입니다.' UNION ALL
         SELECT 94, '대한민국 국가대표', '2022 월드컵 홈', 'S', '100', false, 120000, 'DELIVERY', '월드컵 기념 유니폼입니다.' UNION ALL
         SELECT 95, '대한민국 국가대표', '2022 월드컵 어웨이', 'A', '110', false, 98000, 'DELIVERY', '월드컵 기념 어웨이입니다.' UNION ALL
         SELECT 96, '대한민국 국가대표', '박지성 레전드 마킹', 'S', '100', true, 280000, 'BOTH', '레전드 마킹 유니폼입니다.' UNION ALL
         SELECT 97, '대한민국 국가대표', '황희찬 마킹', 'A', '95', true, 128000, 'DELIVERY', '황희찬 마킹 정품입니다.' UNION ALL
         SELECT 98, '대한민국 국가대표', '김민재 마킹', 'S', '100', true, 148000, 'DELIVERY', '김민재 마킹 정품입니다.' UNION ALL
         SELECT 99, '대한민국 국가대표', '2024 어웨이', 'A', '105', false, 72000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 100, '대한민국 국가대표', '2023 홈', 'B', '100', false, 55000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 101, '전북 현대', '2024 홈', 'S', '100', false, 72000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 102, '전북 현대', '2024 어웨이', 'A', '105', false, 60000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 103, '전북 현대', '2023 홈', 'B', '95', false, 40000, 'DELIVERY', '세탁 후 보관했습니다.' UNION ALL
         SELECT 104, '울산 HD', '2024 홈', 'S', '100', false, 70000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 105, '울산 HD', '2024 어웨이', 'A', '110', false, 58000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 106, 'FC 서울', '2024 홈', 'S', '100', false, 68000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 107, 'FC 서울', '레트로 홈', 'A', '95', false, 88000, 'DELIVERY', '레트로 한정 에디션입니다.' UNION ALL
         SELECT 108, '수원 삼성 블루윙즈', '2024 홈', 'S', '100', false, 65000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 109, '수원 삼성 블루윙즈', '레트로 홈', 'S', '110', false, 125000, 'DELIVERY', '레트로 한정판입니다.' UNION ALL
         SELECT 110, '인천 유나이티드', '2024 홈', 'A', '100', false, 62000, 'BOTH', '보관 상태 양호합니다.' UNION ALL
         SELECT 111, '아틀레티코 마드리드', '23/24 홈', 'S', '100', false, 92000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 112, '아틀레티코 마드리드', '그리즈만 마킹', 'A', '105', true, 118000, 'BOTH', '그리즈만 마킹 정품입니다.' UNION ALL
         SELECT 113, '유벤투스', '23/24 홈', 'S', '100', false, 90000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 114, '유벤투스', '블라호비치 마킹', 'A', '95', true, 112000, 'DELIVERY', '블라호비치 마킹 정품입니다.' UNION ALL
         SELECT 115, '인테르 밀란', '23/24 홈', 'S', '100', false, 88000, 'BOTH', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 116, '인테르 밀란', '라우타로 마킹', 'A', '105', true, 115000, 'DELIVERY', '라우타로 마킹 정품입니다.' UNION ALL
         SELECT 117, 'AC 밀란', '23/24 홈', 'S', '100', false, 90000, 'DELIVERY', '신품급 상태입니다.' UNION ALL
         SELECT 118, 'AC 밀란', '지루 마킹', 'A', '95', true, 108000, 'BOTH', '지루 마킹 정품입니다.' UNION ALL
         SELECT 119, '도르트문트', '23/24 홈', 'S', '100', false, 88000, 'DELIVERY', '착용 1회 상태 좋습니다.' UNION ALL
         SELECT 120, '도르트문트', '산초 마킹', 'A', '105', true, 110000, 'DELIVERY', '산초 마킹 정품입니다.'
     ) AS d;

-- 3) BASKETBALL (100개)
INSERT INTO post (seller_id, title, content, sport, team, uniform_name, grade, size, marking, price, delivery_type, status, view_count, wish_count, created_at, updated_at)
SELECT
    (SELECT member_id FROM member WHERE email LIKE 'user%@reform.com' ORDER BY RAND() LIMIT 1),
    CONCAT(d.team_name, ' ', d.uniform_name, ' 유니폼 팝니다'),
    CONCAT('상태 ', d.grade, '급 농구 유니폼입니다. ', d.description),
    'BASKETBALL', d.team_name, d.uniform_name, d.grade, d.size, d.marking, d.price, d.delivery_type, 'ON_SALE',
    FLOOR(RAND() * 400), FLOOR(RAND() * 80),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY)
FROM (
         SELECT 1 AS idx, '골든스테이트 워리어스' AS team_name, '23/24 스테픈 커리 시티에디션' AS uniform_name, 'S' AS grade, '105' AS size, true AS marking, 185000 AS price, 'DELIVERY' AS delivery_type, '커리 정품 마킹, 태그 달려있는 새상품입니다.' AS description UNION ALL
         SELECT 2, '골든스테이트 워리어스', '스테픈 커리 홈', 'A', '100', true, 120000, 'BOTH', '실착 2회, 상태 매우 깨끗합니다.' UNION ALL
         SELECT 3, '골든스테이트 워리어스', '클레이 탐슨 어웨이', 'B', '110', true, 65000, 'DELIVERY', '약간의 사용감 있으나 프린팅 상태 양호함.' UNION ALL
         SELECT 4, 'LA 레이커스', '르브론 제임스 23번 홈', 'S', '115', true, 190000, 'DELIVERY', '르브론 제임스 정품 스윙맨 유니폼입니다.' UNION ALL
         SELECT 5, 'LA 레이커스', '코비 브라이언트 레트로 8번', 'S', '100', true, 350000, 'DELIVERY', '코비 브라이언트 영구결번 기념 레트로 한정판.' UNION ALL
         SELECT 6, 'LA 레이커스', '앤서니 데이비스 어웨이', 'A', '105', true, 110000, 'BOTH', '사이즈 미스로 팝니다.' UNION ALL
         SELECT 7, '시카고 불스', '마이클 조던 어웨이 레트로', 'S', '105', true, 420000, 'DELIVERY', '조던 정품 복각 유니폼, 소장 가치 높음.' UNION ALL
         SELECT 8, '시카고 불스', '더마 드로잔 홈', 'A', '100', true, 95000, 'DELIVERY', '깔끔한 상태 유지 중입니다.' UNION ALL
         SELECT 9, '시카고 불스', '데릭 로즈 전성기 레트로', 'A', '95', true, 150000, 'BOTH', '로즈 불스 시절 정품 유니폼.' UNION ALL
         SELECT 10, '댈러스 매버릭스', '루카 돈치치 시티 에디션', 'S', '110', true, 210000, 'DELIVERY', '돈치치 한정판 디자인 유니폼입니다.' UNION ALL
         SELECT 11, '댈러스 매버릭스', '어빙 마킹 홈', 'A', '100', true, 130000, 'DELIVERY', '카이리 어빙 마킹 완료.' UNION ALL
         SELECT 12, '밀워키 벅스', '아데토쿤보 홈', 'S', '115', true, 175000, 'DELIVERY', '그리스 괴물 쿤보 정품 유니폼.' UNION ALL
         SELECT 13, '밀워키 벅스', '데미안 릴라드 어웨이', 'A', '105', true, 140000, 'BOTH', '릴라드 이적 첫 시즌 유니폼.' UNION ALL
         SELECT 14, '보스턴 셀틱스', '제이슨 테이텀 시티 에디션', 'S', '105', true, 180000, 'DELIVERY', '테이텀 정품 스윙맨.' UNION ALL
         SELECT 15, '보스턴 셀틱스', '제일런 브라운 홈', 'A', '100', true, 115000, 'DELIVERY', '파이널 우승 기념 소장용.' UNION ALL
         SELECT 16, '보스턴 셀틱스', '폴 피어스 레트로', 'B', '110', true, 80000, 'BOTH', '올드스쿨 감성 레트로.' UNION ALL
         SELECT 17, '피닉스 선즈', '케빈 듀란트 홈', 'S', '105', true, 195000, 'DELIVERY', '듀란트 정품 마킹, 상태 최상.' UNION ALL
         SELECT 18, '피닉스 선즈', '데빈 부커 어웨이', 'A', '100', true, 125000, 'DELIVERY', '부커 마킹 깔끔합니다.' UNION ALL
         SELECT 19, '피닉스 선즈', '크리스 폴 클래식 에디션', 'A', '105', true, 140000, 'BOTH', '선즈 클래식 퍼플 디자인.' UNION ALL
         SELECT 20, '덴버 너기츠', '니콜라 요키치 홈', 'S', '115', true, 188000, 'DELIVERY', 'MVP 요키치 정품 유니폼.' UNION ALL
         SELECT 21, '덴버 너기츠', '자말 머레이 어웨이', 'A', '105', true, 120000, 'DELIVERY', '상태 양호합니다.' UNION ALL
         SELECT 22, '마이애미 히트', '지미 버틀러 시티 에디션', 'S', '100', true, 220000, 'DELIVERY', '바이스 시티 핑크/블루 에디션.' UNION ALL
         SELECT 23, '마이애미 히트', '드웨인 웨이드 레트로', 'A', '105', true, 180000, 'BOTH', '웨이드 은퇴 시즌 유니폼.' UNION ALL
         SELECT 24, '필라델피아 76ers', '조엘 엠비드 홈', 'S', '115', true, 170000, 'DELIVERY', '엠비드 정품 스윙맨.' UNION ALL
         SELECT 25, '필라델피아 76ers', '앨런 아이버슨 블랙 레트로', 'S', '100', true, 300000, 'DELIVERY', '아이버슨 블랙 답장 유니폼.' UNION ALL
         SELECT 26, '브루클린 네츠', '미칼 브릿지스 어웨이', 'A', '105', true, 95000, 'BOTH', '기본형 어웨이 유니폼.' UNION ALL
         SELECT 27, '샌안토니오 스퍼스', '빅터 웸반야마 루키 시즌', 'S', '110', true, 250000, 'DELIVERY', '웸반야마 루키 시즌 한정 소장품.' UNION ALL
         SELECT 28, '샌안토니오 스퍼스', '팀 던컨 레트로', 'A', '105', true, 160000, 'DELIVERY', '레전드 던컨 유니폼.' UNION ALL
         SELECT 29, '뉴욕 닉스', '제이런 브런슨 홈', 'S', '100', true, 165000, 'DELIVERY', '브런슨 정품 마킹.' UNION ALL
         SELECT 30, '멤피스 그리즐리스', '자 모란트 어웨이', 'A', '100', true, 110000, 'BOTH', '상태 매우 좋습니다.' UNION ALL
         SELECT 31, '부산 KCC 이지스', '허웅 홈 유니폼', 'S', '105', true, 150000, 'DELIVERY', '허웅 정품 마킹 유니폼입니다.' UNION ALL
         SELECT 32, '부산 KCC 이지스', '최준용 어웨이', 'A', '110', true, 120000, 'BOTH', '실착 적음, 깨끗함.' UNION ALL
         SELECT 33, '서울 SK 나이츠', '김선형 홈', 'S', '100', true, 140000, 'DELIVERY', '플래시 김선형 정품 마킹.' UNION ALL
         SELECT 34, '서울 SK 나이츠', '자밀 워니 어웨이', 'A', '115', true, 110000, 'DELIVERY', '워니 마킹 유니폼.' UNION ALL
         SELECT 35, '안양 정관장 레드부스터스', '박지훈 홈', 'A', '100', true, 95000, 'BOTH', '정관장 새 로고 유니폼.' UNION ALL
         SELECT 36, '창원 LG 세이커스', '양홍석 어웨이', 'S', '105', true, 130000, 'DELIVERY', 'LG 세이커스 정품.' UNION ALL
         SELECT 37, '수원 KT 소닉붐', '허훈 홈 유니폼', 'S', '100', true, 155000, 'DELIVERY', '허훈 정품 마킹, 풀 패치.' UNION ALL
         SELECT 38, '울산 현대모비스 피버스', '함지훈 레전드 홈', 'A', '110', true, 100000, 'BOTH', '현대모비스 정품.' UNION ALL
         SELECT 39, '원주 DB 프로미', '강상재 홈', 'S', '105', true, 125000, 'DELIVERY', 'DB 프로미 정품 유니폼.' UNION ALL
         SELECT 40, '대구 한국가스공사 페가수스', '김낙현 어웨이', 'A', '100', true, 85000, 'DELIVERY', '가스공사 어웨이 유니폼.' UNION ALL
         SELECT 41, '토론토 랩터스', '카와이 레너드 우승 시즌', 'A', '105', true, 140000, 'DELIVERY', '랩터스 유일 우승 시즌.' UNION ALL
         SELECT 42, '미네소타 팀버울브스', '앤서니 에드워즈 홈', 'S', '105', true, 175000, 'DELIVERY', '에드 에디션 스윙맨.' UNION ALL
         SELECT 43, '오클라호마시티 썬더', 'SGA 시티 에디션', 'S', '100', true, 185000, 'BOTH', '샤이 길저스 알렉산더 마킹.' UNION ALL
         SELECT 44, '샬럿 호네츠', '라멜로 볼 어웨이', 'B', '95', true, 70000, 'DELIVERY', '민트색 디자인 예뻐요.' UNION ALL
         SELECT 45, '유타 재즈', '라우리 마카넨 홈', 'A', '110', true, 100000, 'DELIVERY', '상태 좋습니다.' UNION ALL
         SELECT 46, '뉴올리언스 펠리컨스', '자이언 윌리엄슨 홈', 'A', '120', true, 120000, 'BOTH', '빅사이즈 유니폼.' UNION ALL
         SELECT 47, '인디애나 페이서스', '타이리스 할리버튼 어웨이', 'S', '105', true, 160000, 'DELIVERY', '신성 할리버튼 마킹.' UNION ALL
         SELECT 48, '클리블랜드 캐벌리어스', '도노반 미첼 홈', 'A', '100', true, 115000, 'DELIVERY', '미첼 정품 마킹.' UNION ALL
         SELECT 49, '포틀랜드 트레일블레이저스', '스쿠트 헨더슨 루키', 'S', '105', true, 130000, 'BOTH', '루키 유니폼입니다.' UNION ALL
         SELECT 50, '휴스턴 로케츠', '제일런 그린 어웨이', 'A', '100', true, 95000, 'DELIVERY', '로케츠 레드 컬러.' UNION ALL
         SELECT 51, '골든스테이트 워리어스', '연습용 리버시블 져지', 'A', 'FREE', false, 45000, 'BOTH', '양면 활용 가능한 연습복.' UNION ALL
         SELECT 52, 'LA 레이커스', '보라색 어웨이 져지', 'B', '105', false, 55000, 'DELIVERY', '마킹 없는 무지 기본형.' UNION ALL
         SELECT 53, '시카고 불스', '윈드브레이커 세트', 'A', '105', false, 88000, 'DELIVERY', '유니폼 스타일 바람막이.' UNION ALL
         SELECT 54, '대한민국 농구대표팀', '2023 국제대회 홈', 'S', '105', false, 120000, 'DELIVERY', '국대 정품 유니폼.' UNION ALL
         SELECT 55, '대한민국 농구대표팀', '2023 국제대회 어웨이', 'A', '100', true, 140000, 'BOTH', '라건아 마킹 국대 유니폼.' UNION ALL
         SELECT 56, '서울 삼성 썬더스', '이정현 홈 유니폼', 'S', '105', true, 130000, 'DELIVERY', '삼성 썬더스 정품.' UNION ALL
         SELECT 57, '고양 소노 스카이거너스', '이정현 홈', 'S', '100', true, 135000, 'DELIVERY', '신생팀 소노 유니폼.' UNION ALL
         SELECT 58, '부산 KCC 이지스', '허웅 올스타 유니폼', 'S', '105', true, 180000, 'DELIVERY', '올스타전 한정판 마킹.' UNION ALL
         SELECT 59, '서울 SK 나이츠', '레트로 클래식 홈', 'A', '110', false, 90000, 'BOTH', '올드 로고 버전.' UNION ALL
         SELECT 60, '올랜도 매직', '파올로 반케로 홈', 'S', '110', true, 150000, 'DELIVERY', '매직 유망주 마킹.' UNION ALL
         SELECT 61, '애틀랜타 호크스', '트레이 영 시티에디션', 'A', '100', true, 140000, 'DELIVERY', '트레이 영 마킹.' UNION ALL
         SELECT 62, '디트로이트 피스톤즈', '케이드 커닝햄 홈', 'A', '105', true, 110000, 'BOTH', '기본 피스톤즈 블루.' UNION ALL
         SELECT 63, '세크라멘토 킹스', '디아론 팍스 어웨이', 'S', '100', true, 160000, 'DELIVERY', '킹스 퍼플 컬러.' UNION ALL
         SELECT 64, '시카고 불스', '스콧 피펜 레트로', 'A', '110', true, 170000, 'DELIVERY', '조던 파트너 피펜 유니폼.' UNION ALL
         SELECT 65, 'LA 레이커스', '샤킬 오닐 레트로', 'B', '120', true, 140000, 'BOTH', '샤크 황금기 유니폼.' UNION ALL
         SELECT 66, '골든스테이트 워리어스', '무지 홈 져지', 'A', '105', false, 75000, 'DELIVERY', '마킹 없는 스윙맨.' UNION ALL
         SELECT 67, '피닉스 선즈', '찰스 바클리 클래식', 'S', '105', true, 250000, 'DELIVERY', '바클리 시절 클래식.' UNION ALL
         SELECT 68, '보스턴 셀틱스', '래리 버드 레전드', 'A', '100', true, 180000, 'DELIVERY', '그린 자존심 래리 버드.' UNION ALL
         SELECT 69, '필라델피아 76ers', '아이버슨 화이트 홈', 'B', '105', true, 110000, 'BOTH', '약간의 프린팅 갈라짐 있음.' UNION ALL
         SELECT 70, '댈러스 매버릭스', '덕 노비츠키 레전드', 'S', '110', true, 280000, 'DELIVERY', '독일 병정 노비츠키 정품.' UNION ALL
         SELECT 71, '미네소타 팀버울브스', '케빈 가넷 레트로', 'A', '110', true, 190000, 'DELIVERY', '늑대군주 가넷 유니폼.' UNION ALL
         SELECT 72, '토론토 랩터스', '빈스 카터 퍼플 레트로', 'S', '105', true, 320000, 'BOTH', '에어 캐나다 빈스 카터.' UNION ALL
         SELECT 73, '마이애미 히트', '무지 화이트 홈', 'A', '100', false, 65000, 'DELIVERY', '깔끔한 흰색 기본.' UNION ALL
         SELECT 74, 'NBA 올스타', '2024 올스타 져지', 'S', '105', false, 160000, 'DELIVERY', '최신 올스타전 에디션.' UNION ALL
         SELECT 75, '샌안토니오 스퍼스', '마누 지노빌리 레트로', 'A', '100', true, 140000, 'BOTH', '지노빌리 정품 마킹.' UNION ALL
         SELECT 76, '창원 LG 세이커스', '무지 어웨이', 'B', '105', false, 45000, 'DELIVERY', '연습복 대용 가능.' UNION ALL
         SELECT 77, '수원 KT 소닉붐', '한희원 홈 유니폼', 'A', '100', true, 90000, 'DELIVERY', '상태 준수합니다.' UNION ALL
         SELECT 78, '원주 DB 프로미', '김종규 홈 유니폼', 'S', '115', true, 135000, 'BOTH', '빅사이즈 정품.' UNION ALL
         SELECT 79, '서울 삼성 썬더스', '클래식 블루 레트로', 'A', '105', false, 80000, 'DELIVERY', '추억의 삼성 로고.' UNION ALL
         SELECT 80, '안양 정관장 레드부스터스', '오세근 전성기 홈', 'A', '110', true, 110000, 'DELIVERY', '오세근 안양 시절.' UNION ALL
         SELECT 81, '밀워키 벅스', '레이 알렌 레트로', 'A', '105', true, 145000, 'BOTH', '벅스 클래식 퍼플.' UNION ALL
         SELECT 82, 'LA 클리퍼스', '카와이 레너드 홈', 'S', '105', true, 170000, 'DELIVERY', '클리퍼스 정품.' UNION ALL
         SELECT 83, 'LA 클리퍼스', '폴 조지 어웨이', 'A', '100', true, 125000, 'DELIVERY', '상태 아주 좋음.' UNION ALL
         SELECT 84, '휴스턴 로케츠', '하킴 올라주원 레트로', 'S', '110', true, 290000, 'BOTH', '더 드림 올라주원.' UNION ALL
         SELECT 85, '휴스턴 로케츠', '제임스 하든 홈', 'B', '105', true, 60000, 'DELIVERY', '하든 시절 유니폼.' UNION ALL
         SELECT 86, '포틀랜드 트레일블레이저스', '데미안 릴라드 홈', 'A', '100', true, 95000, 'DELIVERY', '릴라드 타임 시절.' UNION ALL
         SELECT 87, '유타 재즈', '존 스탁턴 레트로', 'S', '100', true, 210000, 'BOTH', '전설의 어시스트 왕.' UNION ALL
         SELECT 88, '유타 재즈', '칼 말론 레트로', 'A', '115', true, 185000, 'DELIVERY', '우편배달부 칼 말론.' UNION ALL
         SELECT 89, '오클라호마시티 썬더', '러셀 웨스트브룩 홈', 'B', '105', true, 55000, 'DELIVERY', '웨스트브룩 전성기.' UNION ALL
         SELECT 90, '오클라호마시티 썬더', '케빈 듀란트 썬더 시절', 'B', '110', true, 50000, 'BOTH', '듀란트 OKC 시절.' UNION ALL
         SELECT 91, '워싱턴 위저즈', '조던 위저즈 레트로', 'A', '105', true, 190000, 'DELIVERY', '조던 복귀 시절.' UNION ALL
         SELECT 92, '워싱턴 위저즈', '카일 쿠즈마 시티에디션', 'S', '100', true, 155000, 'DELIVERY', '핑크색 벚꽃 에디션.' UNION ALL
         SELECT 93, '멤피스 그리즐리스', '클래식 티얼 레트로', 'S', '105', false, 130000, 'BOTH', '벤쿠버 시절 디자인.' UNION ALL
         SELECT 94, '인디애나 페이서스', '레지 밀러 레트로', 'A', '100', true, 180000, 'DELIVERY', '밀러 타임 레전드.' UNION ALL
         SELECT 95, '새크라멘토 킹스', '크리스 웨버 레트로', 'A', '110', true, 150000, 'DELIVERY', '밀레니엄 킹스 시절.' UNION ALL
         SELECT 96, '샬럿 호네츠', '무그시 보그스 레트로', 'S', '95', true, 160000, 'BOTH', '최단신 전설 보그스.' UNION ALL
         SELECT 97, '올랜도 매직', '앤퍼니 하더웨이 레트로', 'S', '105', true, 230000, 'DELIVERY', '페니 하더웨이 정품.' UNION ALL
         SELECT 98, '디트로이트 피스톤즈', '그랜트 힐 레트로', 'A', '105', true, 170000, 'DELIVERY', '피스톤즈 말 로고 버전.' UNION ALL
         SELECT 99, '뉴저지 네츠', '제이슨 키드 레트로', 'A', '100', true, 155000, 'BOTH', '네츠 시절 키드 마킹.' UNION ALL
         SELECT 100, '시카고 불스', '데니스 로드맨 홈', 'A', '110', true, 195000, 'DELIVERY', '리바운드 왕 로드맨.'
     ) AS d;

-- 4) VOLLEYBALL (80개)
INSERT INTO post (seller_id, title, content, sport, team, uniform_name, grade, size, marking, price, delivery_type, status, view_count, wish_count, created_at, updated_at)
SELECT
    (SELECT member_id FROM member WHERE email LIKE 'user%@reform.com' ORDER BY RAND() LIMIT 1),
    CONCAT(d.team_name, ' ', d.uniform_name, ' 유니폼 팝니다'),
    CONCAT('상태 ', d.grade, '급 배구 유니폼입니다. ', d.description),
    'VOLLEYBALL', d.team_name, d.uniform_name, d.grade, d.size, d.marking, d.price, d.delivery_type, 'ON_SALE',
    FLOOR(RAND() * 300), FLOOR(RAND() * 50),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 90) DAY)
FROM (
         SELECT 1 AS idx, '인천 흥국생명 핑크스파이더스' AS team_name, '김연경 마킹 홈' AS uniform_name, 'S' AS grade, '100' AS size, true AS marking, 180000 AS price, 'DELIVERY' AS delivery_type, '김연경 선수 정품 마킹, 실착 없음.' AS description UNION ALL
         SELECT 2, '인천 흥국생명 핑크스파이더스', '어웨이 핑크', 'A', '95', false, 85000, 'BOTH', '색감이 너무 예쁜 유니폼입니다.' UNION ALL
         SELECT 3, '수원 현대건설 힐스테이트', '양효진 홈 마킹', 'S', '105', true, 140000, 'DELIVERY', '현대건설 거미줄 마킹 정품.' UNION ALL
         SELECT 4, '수원 현대건설 힐스테이트', '리베로 전용 유니폼', 'A', '95', true, 110000, 'DELIVERY', '김연견 마킹 리베로 유니폼.' UNION ALL
         SELECT 5, '대전 정관장 레드스파크스', '메가 마킹 홈', 'S', '100', true, 155000, 'DELIVERY', '인도네시아 특급 메가 선수 마킹.' UNION ALL
         SELECT 6, '대전 정관장 레드스파크스', '염혜선 어웨이', 'A', '100', true, 120000, 'BOTH', '국가대표 세터 염혜선 마킹.' UNION ALL
         SELECT 7, '경북천년숲 김천 한국도로공사' , '임명옥 리베로 져지', 'A', '95', true, 105000, 'DELIVERY', '도로공사 리베로 정품 유니폼.' UNION ALL
         SELECT 8, '경북천년숲 김천 한국도로공사', '23/24 홈 유니폼', 'B', '105', false, 60000, 'DELIVERY', '사용감 조금 있으나 깨끗함.' UNION ALL
         SELECT 9, '서울 GS칼텍스 KIXX', '강소휘 시절 홈', 'A', '100', true, 130000, 'BOTH', '민트색 GS칼텍스 유니폼.' UNION ALL
         SELECT 10, '서울 GS칼텍스 KIXX', '실착 사인 유니폼', 'S', '100', true, 250000, 'DELIVERY', '선수 친필 사인이 포함된 한정판.' UNION ALL
         SELECT 11, '화성 IBK기업은행 알토스', '김희진 홈 마킹', 'S', '105', true, 165000, 'DELIVERY', 'IBK 희진리 정품 유니폼.' UNION ALL
         SELECT 12, '광주 페퍼저축은행 AI페퍼스', '야스민 홈', 'A', '110', true, 95000, 'DELIVERY', '페퍼스 강렬한 레드 유니폼.' UNION ALL
         SELECT 13, '인천 대한항공 점보스', '한선수 홈 마킹', 'S', '105', true, 150000, 'DELIVERY', '대한항공 캡틴 한선수 정품.' UNION ALL
         SELECT 14, '인천 대한항공 점보스', '곽승석 어웨이', 'A', '105', true, 120000, 'BOTH', '통합 우승 패치 부착됨.' UNION ALL
         SELECT 15, '천안 현대캐피탈 스카이워커스', '문성민 레전드 홈', 'S', '110', true, 170000, 'DELIVERY', '현대캐피탈 상징 문성민 마킹.' UNION ALL
         SELECT 16, '천안 현대캐피탈 스카이워커스', '허수봉 어웨이', 'A', '110', true, 135000, 'DELIVERY', '수봉봉 정품 마킹 유니폼.' UNION ALL
         SELECT 17, '안산 OK금융그룹 읏맨', '레오 홈 마킹', 'S', '115', true, 145000, 'DELIVERY', '괴물 용병 레오 마킹.' UNION ALL
         SELECT 18, '의정부 KB손해보험 스타즈', '황택의 홈 마킹', 'A', '105', true, 110000, 'BOTH', 'KB손보 노란색 유니폼.' UNION ALL
         SELECT 19, '수원 한국전력 빅스톰', '임성진 홈 마킹', 'S', '105', true, 160000, 'DELIVERY', '배구돌 임성진 정품 유니폼.' UNION ALL
         SELECT 20, '서울 우리카드 우리WON', '김지한 어웨이', 'A', '100', true, 125000, 'DELIVERY', '우리카드 블루 컬러 유니폼.' UNION ALL
         SELECT 21, '대한민국 국가대표', '김연경 은퇴 경기 기념', 'S', '100', true, 300000, 'DELIVERY', '국대 10번 KYK 한정판 에디션.' UNION ALL
         SELECT 22, '대한민국 국가대표', '도쿄올림픽 어웨이', 'A', '105', false, 180000, 'BOTH', '4강 신화 도쿄올림픽 모델.' UNION ALL
         SELECT 23, '일본 국가대표', '이시카와 유키 홈', 'S', '100', true, 210000, 'DELIVERY', '일본 에이스 이시카와 정품.' UNION ALL
         SELECT 24, '일본 국가대표', '니시다 유지 어웨이', 'A', '105', true, 195000, 'DELIVERY', '니시다 마킹 아식스 정품.' UNION ALL
         SELECT 25, '이탈리아 국가대표', '자이체프 레트로', 'A', '110', true, 150000, 'BOTH', '이탈리아 아주리 유니폼.' UNION ALL
         SELECT 26, '브라질 국가대표', '홈 옐로우 져지', 'B', '105', false, 80000, 'DELIVERY', '배구 강국 브라질 기본형.' UNION ALL
         SELECT 27, '인천 흥국생명 핑크스파이더스', '김수지 홈 마킹', 'A', '100', true, 130000, 'DELIVERY', '수지메달 정품 마킹.' UNION ALL
         SELECT 28, '수원 현대건설 힐스테이트', '이다현 어웨이', 'S', '100', true, 145000, 'BOTH', '현대건설 센터 이다현.' UNION ALL
         SELECT 29, '대전 정관장 레드스파크스', '이소영 시절 홈', 'B', '95', true, 70000, 'DELIVERY', '소영에스파냐 마킹 유니폼.' UNION ALL
         SELECT 30, '서울 GS칼텍스 KIXX', '실착 연습복', 'B', '105', false, 40000, 'DELIVERY', '구단 연습용 훈련복.' UNION ALL
         SELECT 31, '수원 한국전력 빅스톰', '신영석 마킹 홈', 'S', '115', true, 140000, 'DELIVERY', '신영석 정품 마킹.' UNION ALL
         SELECT 32, '화성 IBK기업은행 알토스', '폰푼 어웨이', 'A', '95', true, 135000, 'BOTH', '태국 국대 세터 폰푼 마킹.' UNION ALL
         SELECT 33, '안산 OK금융그룹 읏맨', '바야르사이한 홈', 'A', '110', true, 110000, 'DELIVERY', '아시아쿼터 바야 마킹.' UNION ALL
         SELECT 34, '인천 대한항공 점보스', '정지석 홈 마킹', 'A', '105', true, 125000, 'DELIVERY', '대한항공 에이스 정지석.' UNION ALL
         SELECT 35, '천안 현대캐피탈 스카이워커스', '전광인 어웨이', 'S', '105', true, 150000, 'BOTH', '현캡 전광인 정품.' UNION ALL
         SELECT 36, '경북천년숲 김천 한국도로공사', '강소휘 새 유니폼', 'S', '100', true, 170000, 'DELIVERY', '도공 이적 첫 유니폼.' UNION ALL
         SELECT 37, '광주 페퍼저축은행 AI페퍼스', '박정아 홈 마킹', 'S', '105', true, 160000, 'DELIVERY', '클러치박 정품 마킹.' UNION ALL
         SELECT 38, '서울 우리카드 우리WON', '한성정 홈', 'A', '105', true, 100000, 'BOTH', '우리카드 주행 유니폼.' UNION ALL
         SELECT 39, '대한민국 국가대표', 'VNL 리베로 유니폼', 'A', '95', false, 120000, 'DELIVERY', '국대 리베로 져지.' UNION ALL
         SELECT 40, '대한민국 국가대표', '남배 국대 홈', 'B', '110', false, 75000, 'DELIVERY', '남자 배구 국가대표 유니폼.' UNION ALL
         SELECT 41, '수원 현대건설 힐스테이트', '모마 홈 마킹', 'S', '105', true, 140000, 'DELIVERY', '우승 주역 모마 마킹.' UNION ALL
         SELECT 42, '인천 흥국생명 핑크스파이더스', '윌로우 홈', 'A', '110', true, 110000, 'BOTH', '랜디 존슨 딸 윌로우 마킹.' UNION ALL
         SELECT 43, '대전 정관장 레드스파크스', '표승주 이적 유니폼', 'S', '100', true, 150000, 'DELIVERY', '정관장 표승주 마킹.' UNION ALL
         SELECT 44, '의정부 KB손해보험 스타즈', '나경복 홈', 'A', '105', true, 120000, 'DELIVERY', 'KB 나경복 정품.' UNION ALL
         SELECT 45, '안양 정관장 레드스파크스', '사인 연습복', 'S', '100', false, 90000, 'BOTH', '전구단 사인 연습 티셔츠.' UNION ALL
         SELECT 46, '천안 현대캐피탈 스카이워커스', '복각 레트로 져지', 'A', '105', false, 115000, 'DELIVERY', '90년대 현대캐피탈 디자인.' UNION ALL
         SELECT 47, '인천 대한항공 점보스', '임동혁 홈', 'A', '115', true, 100000, 'DELIVERY', '상무 입대 전 막판 시즌.' UNION ALL
         SELECT 48, '미국 국가대표', '조던 라슨 홈', 'S', '95', true, 220000, 'DELIVERY', '미국 여배 전설 라슨 마킹.' UNION ALL
         SELECT 49, '폴란드 국가대표', '레온 어웨이', 'S', '110', true, 200000, 'BOTH', '세계 최강 공격수 레온.' UNION ALL
         SELECT 50, '태국 국가대표', '차추온 홈 마킹', 'A', '95', true, 130000, 'DELIVERY', '태국 에이스 차추온.' UNION ALL
         SELECT 51, '서울 GS칼텍스 KIXX', '실착 무릎 보호대 세트', 'B', 'FREE', false, 35000, 'DELIVERY', '유니폼과 세트인 보호대.' UNION ALL
         SELECT 52, '화성 IBK기업은행 알토스', '최정민 홈', 'A', '105', true, 110000, 'BOTH', '블로킹 1위 최정민.' UNION ALL
         SELECT 53, '수원 현대건설 힐스테이트', '고예림 어웨이', 'S', '100', true, 155000, 'DELIVERY', '고예림 정품 마킹.' UNION ALL
         SELECT 54, '경북천년숲 김천 한국도로공사', '배유나 홈', 'A', '105', true, 125000, 'DELIVERY', '배구천재 배유나 마킹.' UNION ALL
         SELECT 55, '광주 페퍼저축은행 AI페퍼스', '박은서 홈', 'B', '100', true, 70000, 'BOTH', '페퍼스 주공격수.' UNION ALL
         SELECT 56, '인천 흥국생명 핑크스파이더스', '레트로 블랙 에디션', 'S', '105', false, 160000, 'DELIVERY', '한정판 블랙 유니폼.' UNION ALL
         SELECT 57, '수원 한국전력 빅스톰', '서재덕 홈', 'A', '110', true, 120000, 'DELIVERY', '수원 왕자 서재덕.' UNION ALL
         SELECT 58, '서울 우리카드 우리WON', '이상현 홈', 'A', '115', true, 105000, 'BOTH', '미들블로커 이상현.' UNION ALL
         SELECT 59, '천안 현대캐피탈 스카이워커스', '김지한 시절 홈', 'B', '105', true, 65000, 'DELIVERY', '현캡 시절 김지한.' UNION ALL
         SELECT 60, '의정부 KB손해보험 스타즈', '리베로 그린 져지', 'A', '100', true, 95000, 'DELIVERY', '정민수 마킹 리베로.' UNION ALL
         SELECT 61, '서울 GS칼텍스 KIXX', '안혜진 홈', 'S', '100', true, 145000, 'BOTH', '돌아온 안혜진 정품.' UNION ALL
         SELECT 62, '화성 IBK기업은행 알토스', '황민경 홈', 'A', '100', true, 130000, 'DELIVERY', '밍키 황민경 정품 마킹.' UNION ALL
         SELECT 63, '수원 현대건설 힐스테이트', '정지윤 홈', 'S', '105', true, 150000, 'DELIVERY', '파워 공격수 정지윤.' UNION ALL
         SELECT 64, '안산 OK금융그룹 읏맨', '송희채 홈', 'A', '105', true, 105000, 'BOTH', '살림꾼 송희채.' UNION ALL
         SELECT 65, '인천 대한항공 점보스', '유광우 홈', 'A', '100', true, 120000, 'DELIVERY', '명품 세터 유광우.' UNION ALL
         SELECT 66, '대한민국 국가대표', '90년대 빈티지 져지', 'B', '105', false, 90000, 'DELIVERY', '올드스쿨 국대 유니폼.' UNION ALL
         SELECT 67, '일본 국가대표', '다카하시 란 홈', 'S', '100', true, 220000, 'DELIVERY', '다카하시 란 정품 마킹.' UNION ALL
         SELECT 68, '브라질 국가대표', '가비 어웨이', 'S', '95', true, 200000, 'BOTH', '월드클래스 가비 마킹.' UNION ALL
         SELECT 69, '튀르키예 국가대표', '바르가스 홈', 'S', '105', true, 230000, 'DELIVERY', '튀르키예 귀화 에이스.' UNION ALL
         SELECT 70, '튀르키예 국가대표', '에브라르 카라쿠르트', 'A', '110', true, 180000, 'DELIVERY', '핑크머리 카라쿠르트.' UNION ALL
         SELECT 71, '인천 흥국생명 핑크스파이더스', '연습복 상의', 'C', '105', false, 25000, 'BOTH', '상태 안 좋음 훈련용.' UNION ALL
         SELECT 72, '수원 현대건설 힐스테이트', '팬메이드 응원복', 'A', 'FREE', false, 30000, 'DELIVERY', '응원용 보급형 져지.' UNION ALL
         SELECT 73, '대전 정관장 레드스파크스', '올스타전 유니폼', 'S', '100', true, 190000, 'DELIVERY', 'V리그 올스타전 실착급.' UNION ALL
         SELECT 74, '화성 IBK기업은행 알토스', '육서영 홈', 'A', '100', true, 110000, 'BOTH', '육서영 정품 마킹.' UNION ALL
         SELECT 75, '서울 GS칼텍스 KIXX', '오세연 홈', 'A', '110', true, 100000, 'DELIVERY', '오세연 정품 마킹.' UNION ALL
         SELECT 76, '천안 현대캐피탈 스카이워커스', '박상하 홈', 'B', '115', true, 60000, 'DELIVERY', '중고 유니폼.' UNION ALL
         SELECT 77, '인천 대한항공 점보스', '정한용 어웨이', 'S', '105', true, 140000, 'BOTH', '떠오르는 에이스 정한용.' UNION ALL
         SELECT 78, '수원 한국전력 빅스톰', '구교혁 홈', 'A', '105', true, 95000, 'DELIVERY', '한전 신성 구교혁.' UNION ALL
         SELECT 79, '안산 OK금융그룹 읏맨', '차지환 홈', 'A', '110', true, 110000, 'BOTH', '차지환 정품 마킹.' UNION ALL
         SELECT 80, '대한민국 국가대표', '아시안게임 블루', 'A', '100', false, 110000, 'DELIVERY', '아시안게임 국대 유니폼.'
     ) AS d;

-- 5) ESPORTS (80개)
INSERT INTO post (seller_id, title, content, sport, team, uniform_name, grade, size, marking, price, delivery_type, status, view_count, wish_count, created_at, updated_at)
SELECT
    (SELECT member_id FROM member WHERE email LIKE 'user%@reform.com' ORDER BY RAND() LIMIT 1),
    CONCAT(d.team_name, ' ', d.uniform_name, ' 판매합니다'),
    CONCAT('상태 ', d.grade, '급 이스포츠 의류입니다. ', d.description),
    'ESPORTS', d.team_name, d.uniform_name, d.grade, d.size, d.marking, d.price, d.delivery_type, 'ON_SALE',
    FLOOR(RAND() * 600), FLOOR(RAND() * 120),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 60) DAY),
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 60) DAY)
FROM (
         SELECT 1 AS idx, 'T1' AS team_name, '2025 월즈 우승 기념 져지' AS uniform_name, 'S' AS grade, '105' AS size, true AS marking, 250000 AS price, 'DELIVERY' AS delivery_type, '페이커(Faker) 마킹 포함된 5성 우승 기념 한정판입니다.' AS description UNION ALL
         SELECT 2, 'T1', '2026 시즌 공식 홈 져지', 'S', '100', true, 120000, 'BOTH', '도란(Doran) 선수 마킹, 미개봉 새상품.' UNION ALL
         SELECT 3, 'T1', '2024 월즈 유니폼 (자켓형)', 'A', '105', true, 185000, 'DELIVERY', '케리아(Keria) 마킹, 자수 상태 완벽.' UNION ALL
         SELECT 4, 'T1', '구마유시 싸인 져지', 'S', '110', true, 300000, 'DELIVERY', '구마유시(Gumayusi) 친필 사인이 포함된 소장용.' UNION ALL
         SELECT 5, 'T1', '시즌 클래식 후드티', 'B', '100', false, 45000, 'BOTH', '실착 횟수 많음, 편하게 입으실 분.' UNION ALL
         SELECT 6, 'Gen.G Esports', '2026 LCK 스프링 져지', 'S', '105', true, 130000, 'DELIVERY', '쵸비(Chovy) 마킹, 젠지 공식 샵 구매.' UNION ALL
         SELECT 7, 'Gen.G Esports', '골드 에디션 져지', 'A', '100', true, 150000, 'BOTH', '캐년(Canyon) 마킹, 골드 로고가 매력적입니다.' UNION ALL
         SELECT 8, 'Gen.G Esports', '룰러 복귀 기념 져지', 'S', '105', true, 200000, 'DELIVERY', '룰러(Ruler) 복귀 시즌 한정판 마킹.' UNION ALL
         SELECT 9, 'Gen.G Esports', '발로란트 팀 공식 져지', 'A', '100', false, 85000, 'DELIVERY', 'VCT 퍼시픽 참가 공식 의류.' UNION ALL
         SELECT 10, 'Hanwha Life Esports', '2026 오렌지 져지', 'S', '105', true, 125000, 'DELIVERY', '제카(Zeka) 마킹, 색감 아주 밝고 예뻐요.' UNION ALL
         SELECT 11, 'Hanwha Life Esports', '윈드브레이커 (바람막이)', 'A', '110', true, 160000, 'BOTH', '바이퍼(Viper) 마킹된 고퀄리티 자켓.' UNION ALL
         SELECT 12, 'Hanwha Life Esports', '피넛 마킹 홈 져지', 'S', '100', true, 110000, 'DELIVERY', '피넛(Peanut) 선수 전용 마킹.' UNION ALL
         SELECT 13, 'Dplus KIA', '쇼메이커 시그니처 져지', 'S', '100', true, 140000, 'DELIVERY', 'ShowMaker 마킹, 민트색 포인트가 특징.' UNION ALL
         SELECT 14, 'Dplus KIA', '2026 시즌 어웨이 화이트', 'A', '105', true, 95000, 'BOTH', '루시드(Lucid) 마킹, 깔끔한 화이트.' UNION ALL
         SELECT 15, 'KT Rolster', '2026 공식 홈 져지', 'A', '105', true, 115000, 'DELIVERY', '비디디(Bdd) 마킹, KT 로고 자수.' UNION ALL
         SELECT 16, 'KT Rolster', '데프트 헌정 레트로 져지', 'S', '100', true, 220000, 'DELIVERY', '데프트(Deft) 선수 은퇴 기념 한정판.' UNION ALL
         SELECT 17, 'G2 Esports', '2026 프로 킷 져지', 'S', '105', false, 145000, 'DELIVERY', '유럽 감성 G2 정품, 사무라이 로고.' UNION ALL
         SELECT 18, 'Sentinels', '발로란트 프로 져지', 'S', '100', true, 180000, 'DELIVERY', '텐즈(TenZ) 마킹, 센티널즈 공식 굿즈.' UNION ALL
         SELECT 19, 'Fnatic', '2026 오렌지 블랙 져지', 'A', '105', false, 110000, 'BOTH', '프나틱 유럽 공식 스토어 직구.' UNION ALL
         SELECT 20, 'DRX', '2022 월즈 미라클 져지', 'A', '100', true, 280000, 'DELIVERY', '중꺾마 신화, 제카 마킹 소장용.' UNION ALL
         SELECT 21, 'BNK FEARX', '2026 옐로우 져지', 'A', '105', true, 90000, 'DELIVERY', '피어엑스 신상 유니폼.' UNION ALL
         SELECT 22, 'Nongshim RedForce', '라면 에디션 져지', 'S', '100', true, 100000, 'BOTH', '농심 레드포스 특유의 레드 컬러.' UNION ALL
         SELECT 23, 'Kwangdong Freecs', '2026 홈 져지', 'A', '105', true, 88000, 'DELIVERY', '커즈(Cuzz) 마킹, 광동 정품.' UNION ALL
         SELECT 24, 'T1', '페이커 10주년 기념 자켓', 'S', '105', true, 450000, 'DELIVERY', 'Faker 10th Anniversary Limited Edition.' UNION ALL
         SELECT 25, 'OKSavingsBank BRION', '테디 마킹 홈 져지', 'A', '100', true, 85000, 'BOTH', '브리온 테디(Teddy) 마킹.' UNION ALL
         SELECT 26, 'Paper Rex', 'VCT 퍼시픽 공식 져지', 'S', '100', false, 160000, 'DELIVERY', 'PRX 화려한 패턴 디자인.' UNION ALL
         SELECT 27, 'Team Liquid', '나이키 콜라보 져지', 'A', '105', false, 135000, 'DELIVERY', '리퀴드 x 나이키 한정판.' UNION ALL
         SELECT 28, 'Cloud9', 'C9 클래식 화이트', 'B', '110', false, 55000, 'BOTH', '북미의 자존심 C9 유니폼.' UNION ALL
         SELECT 29, 'ZETA DIVISION', '일본 발로란트 팀 져지', 'S', '100', false, 175000, 'DELIVERY', '제타 디비전 공식 굿즈.' UNION ALL
         SELECT 30, 'T1', '2023 월즈 우승 서머너즈 컵 져지', 'A', '105', true, 210000, 'DELIVERY', '제우스(Zeus) 마킹 포함.' UNION ALL
         SELECT 31, 'Gen.G Esports', '시티 에디션 후드', 'A', '110', false, 75000, 'DELIVERY', '평상시 입기 좋은 디자인.' UNION ALL
         SELECT 32, 'Dplus KIA', '에이밍 마킹 져지', 'B', '105', true, 60000, 'BOTH', 'Aiming 마킹, 실착감 있음.' UNION ALL
         SELECT 33, 'Hanwha Life Esports', '딜라이트 마킹 져지', 'S', '100', true, 115000, 'DELIVERY', 'Delight 정품 마킹.' UNION ALL
         SELECT 34, 'DRX', '2026 발로란트 팀 유니폼', 'A', '105', true, 98000, 'DELIVERY', '마코(MaKo) 마킹.' UNION ALL
         SELECT 35, 'T1', '베이스볼 자켓 에디션', 'S', '110', false, 240000, 'DELIVERY', 'T1 라이프스타일 한정판.' UNION ALL
         SELECT 36, 'G2 Esports', '레인보우 식스 시즈 팀 져지', 'A', '100', false, 120000, 'BOTH', '유럽 직구 상품.' UNION ALL
         SELECT 37, 'Sentinels', 'SEN City 챔피언스 져지', 'S', '105', false, 190000, 'DELIVERY', '발로란트 챔피언스 한정.' UNION ALL
         SELECT 38, 'Natus Vincere', 'CS2 공식 져지', 'A', '105', false, 130000, 'DELIVERY', '나비(NAVI) 우크라이나 정품.' UNION ALL
         SELECT 39, 'T1', '아카데미 팀 유니폼', 'B', '95', false, 50000, 'BOTH', 'T1 루키즈 연습생 복장.' UNION ALL
         SELECT 40, 'Gen.G Esports', '전설의 2024 MSI 우승 져지', 'A', '105', true, 180000, 'DELIVERY', '기인(Kiin) 마킹.' UNION ALL
         SELECT 41, 'Hanwha Life Esports', '구마유시 싸인 화이트져지', 'S', '105', true, 280000, 'DELIVERY', '이벤트 당첨 사인이 포함됨.' UNION ALL
         SELECT 42, 'Dplus KIA', '베릴 마킹 레트로', 'B', '100', true, 75000, 'BOTH', '도사님 마킹된 옛 유니폼.' UNION ALL
         SELECT 43, 'KT Rolster', '퍼펙트 마킹 홈 져지', 'A', '105', true, 95000, 'DELIVERY', 'PerfecT 마킹.' UNION ALL
         SELECT 44, 'T1', '2026 스프링 자켓', 'S', '110', false, 210000, 'DELIVERY', '최신형 트랙 자켓.' UNION ALL
         SELECT 45, 'Team Vitality', '2026 프로 져지', 'A', '100', false, 110000, 'BOTH', '프랑스 명문 팀 유니폼.' UNION ALL
         SELECT 46, 'NRG Esports', '발로란트 공식 져지', 'B', '105', false, 65000, 'DELIVERY', '북미 NRG 정품.' UNION ALL
         SELECT 47, 'Leviatán', 'VCT 아메리카스 져지', 'A', '100', true, 150000, 'DELIVERY', '아스파스(aspas) 마킹.' UNION ALL
         SELECT 48, 'FUT Esports', 'EMEA 리그 져지', 'A', '105', false, 95000, 'BOTH', '터키 팀 공식 의류.' UNION ALL
         SELECT 49, 'DRX', '베릴 롤백 기념 져지', 'S', '100', true, 140000, 'DELIVERY', 'BeryL 마킹.' UNION ALL
         SELECT 50, 'T1', '월즈 파이널 후드', 'A', '115', false, 95000, 'DELIVERY', 'XL 사이즈 넉넉합니다.' UNION ALL
         SELECT 51, 'Gen.G Esports', '2026 써머 공식 져지', 'S', '105', true, 120000, 'DELIVERY', '페이즈(Peyz) 마킹.' UNION ALL
         SELECT 52, 'Nongshim RedForce', '지우 마킹 홈', 'A', '100', true, 80000, 'BOTH', 'Jiwoo 마킹 유니폼.' UNION ALL
         SELECT 53, 'FearX', '클리어 마킹 홈', 'B', '105', true, 55000, 'DELIVERY', 'Clear 마킹.' UNION ALL
         SELECT 54, 'T1', '조마 콜라보 바람막이', 'A', '100', false, 130000, 'DELIVERY', 'T1 정품 굿즈.' UNION ALL
         SELECT 55, 'Hanwha Life Esports', '피넛 실착급 져지', 'S', '105', true, 150000, 'BOTH', '상태 매우 좋음.' UNION ALL
         SELECT 56, 'Dplus KIA', '시우 마킹 홈 져지', 'A', '100', true, 110000, 'DELIVERY', 'Siwoo 마킹.' UNION ALL
         SELECT 57, 'KDF', '불독 마킹 홈 져지', 'A', '105', true, 85000, 'DELIVERY', 'BuLLDoG 마킹.' UNION ALL
         SELECT 58, 'Gen.G Esports', '국제전 한정 자켓', 'S', '110', false, 230000, 'DELIVERY', '희귀 매물입니다.' UNION ALL
         SELECT 59, 'T1', '2026 어웨이 유니폼', 'S', '105', true, 125000, 'BOTH', '제우스 마킹.' UNION ALL
         SELECT 60, '100 Thieves', 'LCS 공식 져지', 'A', '105', false, 140000, 'DELIVERY', '디자인 끝판왕 100T.' UNION ALL
         SELECT 61, 'FaZe Clan', '프로 팀 져지', 'B', '110', false, 75000, 'DELIVERY', '글로벌 인기 팀.' UNION ALL
         SELECT 62, 'Team Heretics', 'EMEA 발로란트 져지', 'S', '100', false, 130000, 'BOTH', '헤레틱스 정품.' UNION ALL
         SELECT 63, 'T1', '레트로 2015 져지 복각', 'A', '105', true, 200000, 'DELIVERY', 'SKT T1 시절 디자인.' UNION ALL
         SELECT 64, 'Gen.G Esports', '리그오브레전드 팀 패딩', 'S', '110', false, 280000, 'DELIVERY', '겨울용 공식 패딩.' UNION ALL
         SELECT 65, 'Hanwha Life Esports', '바이퍼 시그니처 후드', 'A', '100', false, 85000, 'BOTH', '바이퍼 디자인 참여.' UNION ALL
         SELECT 66, 'Dplus KIA', '2025 월즈 져지', 'B', '105', true, 70000, 'DELIVERY', '에이밍 마킹.' UNION ALL
         SELECT 67, 'DRX', '2026 블루 져지', 'A', '100', true, 90000, 'DELIVERY', '테디 마킹.' UNION ALL
         SELECT 68, 'T1', '케리아 한정판 반팔', 'S', '100', false, 65000, 'BOTH', '케리아 디자인 굿즈.' UNION ALL
         SELECT 69, 'LOUD', '브라질 VCT 져지', 'A', '105', false, 110000, 'DELIVERY', '브라질 인기팀 라우드.' UNION ALL
         SELECT 70, 'ZETA DIVISION', '2026 화이트 져지', 'S', '100', false, 155000, 'DELIVERY', '일본 직구.' UNION ALL
         SELECT 71, 'Paper Rex', '징 마킹 져지', 'A', '105', true, 180000, 'BOTH', 'Jinggg 마킹 포함.' UNION ALL
         SELECT 72, 'Fnatic', '보아스터 마킹 져지', 'S', '100', true, 160000, 'DELIVERY', 'Boaster 마킹.' UNION ALL
         SELECT 73, 'T1', '페이커 월즈 MVP 에디션', 'S', '105', true, 350000, 'DELIVERY', 'MVP 한정 마킹.' UNION ALL
         SELECT 74, 'Gen.G Esports', '쵸비 사인 연습복', 'A', '100', true, 120000, 'BOTH', '연습용 져지 사인반.' UNION ALL
         SELECT 75, 'Hanwha Life Esports', '2026 캠프 자켓', 'B', '110', false, 95000, 'DELIVERY', '기능성 바람막이.' UNION ALL
         SELECT 76, 'Dplus KIA', '루시드 사인 져지', 'S', '105', true, 160000, 'DELIVERY', '신성 루시드 사인.' UNION ALL
         SELECT 77, 'OKSavingsBank BRION', '모건 마킹 홈', 'A', '105', true, 80000, 'BOTH', 'Morgan 마킹.' UNION ALL
         SELECT 78, 'FearX', '헤나 마킹 어웨이', 'A', '100', true, 85000, 'DELIVERY', 'Hena 마킹.' UNION ALL
         SELECT 79, 'Nongshim RedForce', '실착급 윈드브레이커', 'S', '105', false, 110000, 'DELIVERY', '농심 정품 자켓.' UNION ALL
         SELECT 80, 'T1', '2026 월즈 응원 머플러', 'S', 'FREE', false, 35000, 'BOTH', '유니폼과 세트인 머플러.'
     ) AS d;


-- 인기글 집계 Batch 테스트용 community_post 더미 데이터
INSERT INTO community_post (
    member_id, sport_category, team_category, comm_title, comm_content,
    comm_view_count, like_count, comment_count, status, created_at
)
SELECT
    (SELECT member_id FROM member ORDER BY RAND() LIMIT 1),
    d.sport_category, d.team_category, d.comm_title, d.comm_content,
    d.view_count, d.like_count, d.comment_count, 'ACTIVE',
    d.random_date
FROM (
         -- BASEBALL (10개)
         SELECT 'BASEBALL' AS sport_category, 'LG 트윈스' AS team_category, '오늘 엘지 타격 실화냐?' AS comm_title, '1회부터 홈런 터지는데 오늘 무조건 이길듯ㅋㅋ' AS comm_content, 450 AS view_count, 80 AS like_count, 45 AS comment_count, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) AS random_date UNION ALL
         SELECT 'BASEBALL', 'SSG 랜더스', '문학 직관 중인데 날씨 좋네요', '응원 열기 장난 아님. 역시 문학 구장이 최고임.' , 120, 15, 8, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', 'KIA 타이거즈', '기아 불펜진 보강 시급함', '매번 8회에 뒤집히는 거 실화? 투수진 좀 갈아치우자.', 380, 55, 30, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', '삼성 라이온즈', '라팍 먹거리 추천 좀요', '오늘 처음 가는데 뭐가 제일 맛있나요?', 80, 5, 12, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', '두산 베어스', '잠실 더비 예매 성공!', '드디어 직관 간다. 두산 가즈아!', 200, 30, 10, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', '롯데 자이언츠', '사직구장 응원가 중독성 대박', '하루 종일 귓가에 맴돌아요 ㅋㅋㅋ', 150, 40, 20, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', '한화 이글스', '오늘도 행복수비 중인 한화', '그래도 사랑한다 한화야... 지지는 말자.', 480, 95, 50, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', 'KT 위즈', '강백호 타격감 살아났네요', '역시 국대 타자. 앞으로 계속 이렇게만 해주길.', 210, 25, 15, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', 'NC 다이노스', '엔씨 유니폼 새로 샀어요', '디자인 진짜 잘 뽑은 듯. 대만족!', 60, 12, 3, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASEBALL', '키움 히어로즈', '고척돔 시원해서 좋네요', '여름엔 역시 돔구장이 최고임.', 90, 8, 5, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL

         -- SOCCER (10개)
         SELECT 'SOCCER', '토트넘 홋스퍼', '손흥민 오늘 골 넣을까요?', '요즘 폼 좋아서 멀티골 기대해봅니다.', 500, 100, 48, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', 'PSG', '이강인 패스 지렸다...', '방금 전반전 보신 분? 시야 진짜 미쳤네요.', 420, 85, 40, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', '전북 현대', '전북 올해는 우승 가능할까요?', '경기력이 예전만 못한 것 같아서 걱정입니다.', 310, 45, 25, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', '울산 HD', '울산 공격진 압도적이네요', 'K리그에서 울산 막을 팀이 있을까 싶음.', 280, 38, 18, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', 'FC 서울', '상암 직관 관중 대박 ㄷㄷ', '린가드 효과인가요? 사람 진짜 많음.', 450, 90, 42, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', '맨체스터 시티', '홀란드 득점력 무섭네요', '기계가 아닐까 의심됨... 벌써 몇 골이야.', 230, 28, 14, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', '리버풀', '클롭 나간 이후로 걱정됨', '팀 컬러가 바뀔까요? 잘 적응해야 할 텐데.', 180, 22, 11, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', '레알 마드리드', '역시 챔스의 왕은 레알임', '위기 상황에서도 결국 이겨버리네.', 350, 70, 35, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', '바이에른 뮌헨', '김민재 수비 안정감 대박', '뮌헨 수비의 핵심임. 오늘도 무실점 가자.', 400, 75, 28, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'SOCCER', '대한민국 국가대표', '북중미 월드컵 예선 티켓 예매 정보', '언제 오픈하는지 아시는 분 공유 좀요.', 110, 10, 6, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL

         -- BASKETBALL (10개)
         SELECT 'BASKETBALL', '부산 KCC 이지스', '허웅 오늘도 잘하네요', '인기만큼 실력도 확실한 듯.', 290, 45, 22, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '서울 SK 나이츠', '김선형 플래시 썬 대박', '속공 상황에서 스피드 여전하네요.', 240, 32, 16, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '창원 LG 세이커스', '오늘 엘지 수비 그물망이네요', '상대팀 실책 유도하는 거 장난 아님.', 160, 20, 9, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '수원 KT 소닉붐', '허훈 부상 복귀 환영!', '팀 활력이 달라진 게 눈에 보임.', 210, 25, 13, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '안양 정관장', '정관장 외국인 용병 선택 잘한 듯', '골밑 장악력이 예전보다 훨씬 좋아졌어요.', 130, 15, 7, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '골든스테이트 워리어스', '스테픈 커리 3점슛 하이라이트', '이게 사람이 쏘는 건지... 거리 무관 다 들어감.', 460, 98, 45, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', 'LA 레이커스', '르브론 제임스 나이 잊은 활약', '아직도 리그 정상급인 게 소름 돋음.', 380, 60, 29, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '시카고 불스', '불스 예전 명성 찾았으면', '레트로 유니폼 입고 경기 보는데 뭉클하네요.', 100, 10, 5, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '댈러스 매버릭스', '돈치치 트리플 더블 ㄷㄷ', '스탯 쌓는 속도가 괴물이네요.', 320, 50, 24, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'BASKETBALL', '서울 삼성 썬더스', '삼성 팬인데 언제쯤 반등할까요', '끝까지 응원하긴 하는데 마음이 아프네요.', 90, 5, 20, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL

         -- VOLLEYBALL (10개)
         SELECT 'VOLLEYBALL', '흥국생명 핑크스파이더스', '김연경 선수 스파이크 대박', '실제로 보니까 소리가 장난 아니에요.', 470, 92, 47, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', '현대건설 힐스테이트', '양효진 블로킹 진짜 벽이다', '상대 공격수들 오늘 멘붕 올 듯 ㅋㅋㅋ', 330, 58, 25, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', '정관장 레드스파크스', '메가 선수 서브 에이스 지렸다', '서브 넣을 때 파워가 압도적임.', 290, 42, 21, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', '대한항공 점보스', '한선수 세팅 예술이네요', '공격수들 입맛대로 다 올려줌.', 200, 28, 14, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', '현대캐피탈 스카이워커스', '천안 직관 왔습니다 분위기 짱', '역시 배구의 도시답네요. 응원 소름.', 180, 24, 12, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', 'GS칼텍스', '민트 군단 화이팅!', '오늘 경기 끈질기게 붙어보자.', 140, 18, 8, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', '한국도로공사', '도로공사 수비력은 알아줘야 함', '디그 하나하나가 예술이네요.', 110, 15, 6, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', '삼성화재 블루팡스', '레오 공격 성공률 실화?', '혼자 다 하는 느낌임 ㅋㅋㅋ.', 220, 35, 17, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', 'IBK기업은행', '기업은행 분위기 반전 성공!', '연패 끊어서 다행이네요. 가즈아.', 105, 12, 5, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'VOLLEYBALL', 'OK금융그룹', '오늘 작전 타임 감독님 열정 대박', '선수들도 자극받아서 잘 뛰는 듯.', 95, 9, 4, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL

         -- ESPORTS (10개)
         SELECT 'ESPORTS', 'T1', '페이커 폼 미쳤네요...', '오늘 경기 보셨나요? 무빙이 예술임.', 495, 100, 50, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'Gen.G Esports', '젠지 운영 진짜 소름 돋네', '실수가 아예 없음. 완벽 그 자체.', 430, 88, 38, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'Hanwha Life Esports', '한화 이번 시즌 우승각 잡히나요?', '체급이 진짜 장난 아님.', 340, 65, 33, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'Dplus KIA', '쇼메이커 오늘도 쇼하네요', '플레이메이킹 능력이 아직도 최고임.', 310, 55, 27, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'KT Rolster', '대퍼타임은 이제 옛말?', '오늘 경기 진짜 깔끔하게 이겼음.', 190, 25, 15, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'DRX', '신인들 패기가 장난 아니네요', '보는 맛이 있는 팀인 듯.', 150, 20, 10, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'G2 Esports', 'G2 창의적인 밴픽 보소 ㅋㅋ', '저런 걸 픽할 줄은 몰랐음. 재밌다.', 260, 40, 19, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'Fnatic', '유럽의 자존심 프나틱 가즈아', '국제전에서 사고 한번 쳤으면.', 110, 15, 8, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'Sentinels', '발로란트는 텐즈가 진짜 사기캐', '에임 실화인가요? 화면이 안 보임.', 280, 48, 20, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE) UNION ALL
         SELECT 'ESPORTS', 'Nongshim RedForce', '농심 육성 능력은 인정해줘야 함', '어린 선수들 성장세가 무섭네요.', 85, 7, 5, DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 1440) MINUTE)
     ) AS d;