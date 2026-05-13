# UC-TRD-006 택배 배송 흐름 연동 안내

## 목적
- 거래 상태 흐름에서 `PAID -> IN_PROGRESS` 전환은 판매자의 택배사/송장번호 입력이 있어야만 가능하도록 분리한다.
- 결제와 배치 담당자는 기존 담당 범위를 유지하면서, 현재 거래 도메인에 맞춰 필요한 연결만 반영한다.

## 현재 반영된 범위
- 판매자 전용 배송 시작 API 추가
  - `PATCH /api/trades/{id}/shipping`
  - 요청 본문: `courierCode`, `trackingNumber`
  - 처리 내용:
    - 판매자 본인만 가능
    - `DELIVERY` 거래만 가능
    - 거래 상태가 `PAID`여야 가능
    - 배송지가 입력되어 있어야 가능
    - 택배조회 API의 택배사 목록으로 `courierCode` 유효성 검증
    - 성공 시 `trade.status = IN_PROGRESS`
    - `trade.courierCode`, `trade.courierName`, `trade.trackingNumber` 저장

- 거래 기준 배송 조회 API 추가
  - `GET /api/trades/{id}/tracking`
  - 거래 참여자(구매자/판매자)만 조회 가능
  - 저장된 `courierCode`, `trackingNumber`를 사용해 deliveryapi 추적 결과 반환

- 거래 상세 응답 확장
  - `TradeResponseDTO`에 아래 필드 추가
  - `courierCode`
  - `courierName`
  - `trackingNumber`

## 결제 담당자 연동 가이드
- 담당 범위: `PaymentService`
- 현재 권장 규칙:
  - 결제 성공 시 거래 상태는 `PAID`까지만 변경한다.
  - 결제 성공 시점에 `IN_PROGRESS`로 자동 전환하지 않는다.
  - 배송 시작은 반드시 판매자 API `PATCH /api/trades/{id}/shipping`로만 처리한다.

- 이유:
  - 택배사/송장번호 입력 주체가 판매자이기 때문
  - 결제 완료 직후에는 아직 실물 발송이 시작되지 않았을 수 있기 때문

## 배치 담당자 연동 가이드
- 담당 범위: 스케줄러/배치/알림
- 요구사항:
  - 거래가 `PAID` 상태인데 판매자가 `courierCode` 또는 `trackingNumber`를 입력하지 않은 경우
  - 판매자에게 1일 단위로 계속 알림 발송

### 추천 조회 조건
```sql
SELECT t.*
FROM trade t
WHERE t.status = 'PAID'
  AND t.delivery_type = 'DELIVERY'
  AND (
    t.courier_code IS NULL OR t.courier_code = ''
    OR t.tracking_number IS NULL OR t.tracking_number = ''
  );
```

### 추천 알림 정책
- 발송 대상: `trade.seller_id`
- 알림 타입: `TRADE` 또는 `SYSTEM`
- 알림 문구 예시:
  - `결제가 완료된 거래 '{판매글 제목}'에 택배사와 송장번호를 입력해주세요.`
- 링크 예시:
  - `/trades/{tradeId}`
- 반복 주기:
  - 매일 1회
- 중복 처리 기준:
  - 같은 거래에 대해 같은 날 미읽음 알림이 이미 있으면 추가 발송하지 않음
  - 이전 알림을 읽었거나 날짜가 바뀌면 재발송 가능

### 추천 알림 존재 체크 조건
- `member_id = seller_id`
- `type = TRADE` 또는 선택한 타입
- `link_url = /trades/{tradeId}`
- `is_read = false`
- `created_at >= 오늘 00:00:00`

## DB 반영 사항
- `trade` 테이블 신규 컬럼
  - `courier_code VARCHAR(50)`
  - `courier_name VARCHAR(100)`
  - `tracking_number VARCHAR(100)`

## 프론트 화면 처리 포인트
- 판매자 화면
  - 상태가 `PAID`이고 `deliveryType = DELIVERY`면 택배사/송장번호 입력 화면 노출
  - 저장 버튼은 `PATCH /api/trades/{id}/shipping` 호출

- 구매자 화면
  - 거래 상세 조회 응답의 `courierName`, `trackingNumber` 표시
  - 상세 배송 흐름이 필요하면 `GET /api/trades/{id}/tracking` 호출

## 주의사항
- 직거래(`DIRECT`)는 이 흐름을 사용하지 않는다.
- 결제/배치 담당자는 현재 거래 서비스의 `배송 시작` 책임을 침범하지 않는 방향으로 연결한다.
