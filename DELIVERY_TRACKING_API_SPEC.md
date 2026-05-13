# RE:FORM 택배조회 API 명세서

> 작성일: 2026-05-12  
> 대상: 프론트엔드 / 백엔드 / QA  
> 관련 외부 문서: [deliveryapi tracking docs](https://www.deliveryapi.co.kr/docs#tracking-trace)

---

## 1. 개요

RE:FORM 서버는 외부 택배조회 서비스를 직접 프론트에 노출하지 않고, 백엔드 API로 중계한다.

기본 경로:

- `/api/delivery/tracking`

현재 제공 기능:

1. 지원 택배사 목록 조회
2. 송장번호 기반 배송 조회

응답은 공통 래퍼를 사용한다.

```json
{
  "data": {},
  "message": "..."
}
```

---

## 2. 택배사 목록 조회

### 2-1. 요청

- Method: `GET`
- URL: `/api/delivery/tracking/couriers`
- 인증: 없음

### 2-2. 성공 응답 예시

```json
{
  "data": {
    "isSuccess": true,
    "data": {
      "couriers": [
        {
          "trackingApiCode": "cj",
          "displayName": "CJ대한통운"
        },
        {
          "trackingApiCode": "lotte",
          "displayName": "롯데택배"
        }
      ],
      "total": 2
    }
  },
  "message": "택배사 목록 조회 완료"
}
```

### 2-3. 응답 핵심 필드

| 필드 | 설명 |
| --- | --- |
| `data.isSuccess` | 외부 API 호출 성공 여부 |
| `data.data.couriers[]` | 지원 택배사 목록 |
| `data.data.couriers[].trackingApiCode` | 조회 요청에 사용할 택배사 코드 |
| `data.data.couriers[].displayName` | 화면 표시용 택배사 이름 |

---

## 3. 송장 배송 조회

### 3-1. 요청

- Method: `POST`
- URL: `/api/delivery/tracking/trace`
- 인증: 없음
- Content-Type: `application/json`

### 3-2. 요청 본문

```json
{
  "items": [
    {
      "clientId": "ORDER-2026-001",
      "courierCode": "cj",
      "trackingNumber": "123456789012"
    }
  ]
}
```

### 3-3. 요청 필드

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| `items` | `array` | Y | 조회할 송장 목록 |
| `items[].clientId` | `string` | N | 클라이언트 내부 식별자 |
| `items[].courierCode` | `string` | Y | 택배사 코드 |
| `items[].trackingNumber` | `string` | Y | 송장번호 |

### 3-4. 성공 응답 예시

```json
{
  "data": {
    "isSuccess": true,
    "data": {
      "results": [
        {
          "clientId": "ORDER-2026-001",
          "success": true,
          "data": {
            "trackingNumber": "123456789012",
            "courierCode": "cj",
            "courierName": "CJ대한통운",
            "deliveryStatus": "IN_TRANSIT",
            "deliveryStatusText": "배송중",
            "isDelivered": false,
            "dateLastProgress": "2026-05-12 13:10:00",
            "progresses": [
              {
                "dateTime": "2026-05-12 13:10:00",
                "location": "서울 강남구",
                "status": "배송중",
                "statusCode": "IN_TRANSIT",
                "description": "배송중"
              }
            ],
            "queriedAt": "2026-05-12T04:15:00.000Z"
          },
          "error": null,
          "cache": {
            "fromCache": false,
            "cachedAt": null
          }
        }
      ],
      "summary": {
        "total": 1,
        "successful": 1,
        "failed": 0,
        "billable": 1
      }
    }
  },
  "message": "택배 조회 완료"
}
```

### 3-5. 실패 응답 예시

개별 송장 실패는 HTTP 에러가 아니라 `results[].success=false` 형태로 내려올 수 있다.

```json
{
  "data": {
    "isSuccess": true,
    "data": {
      "results": [
        {
          "clientId": "ORDER-2026-001",
          "success": false,
          "data": null,
          "error": {
            "code": "NOT_FOUND",
            "message": "조회된 배송 정보가 없습니다. 송장번호를 확인해주세요.",
            "courierCode": "cj",
            "trackingNumber": "123456789012",
            "billable": true
          },
          "cache": null
        }
      ],
      "summary": {
        "total": 1,
        "successful": 0,
        "failed": 1,
        "billable": 1
      }
    }
  },
  "message": "택배 조회 완료"
}
```

### 3-6. 프론트 처리 포인트

- `results[].success=true`면 배송 정보 화면을 보여준다.
- `results[].success=false`면 `results[].error.message`를 사용자에게 노출한다.
- `results[].data.progresses`는 최신순/시간순 정렬 여부를 UI에서 확인해 사용한다.
- `results[].data.deliveryStatus`는 상태 태그 색상 분기에 사용할 수 있다.

---

## 4. HTTP 에러

외부 API 자체 인증 실패, rate limit, 내부 서버 오류 등은 백엔드에서 예외를 던진다.

현재 구현 기준 메시지:

| 상황 | 예외 메시지 |
| --- | --- |
| 택배사 목록 조회 실패 | `택배사 목록 조회 API 호출 실패: ...` |
| 송장 조회 실패 | `택배 조회 API 호출 실패: ...` |

향후 필요 시 공통 `ErrorResponse` 포맷으로 변환할 수 있다.

---

## 5. 사용 예시

### 5-1. 택배사 목록 먼저 조회

1. 화면 진입 시 `/api/delivery/tracking/couriers` 호출
2. `trackingApiCode`를 select option value로 사용
3. 사용자가 택배사와 송장번호를 입력

### 5-2. 배송 추적 조회

1. 사용자가 택배사 선택
2. 송장번호 입력
3. `/api/delivery/tracking/trace` 호출
4. `results[0].data.progresses` 기반으로 배송 이력 표시

---

## 6. 구현 파일

- Controller: `src/main/java/com/re_form_shop_2605/controller/delivery/DeliveryTrackingController.java`
- Service: `src/main/java/com/re_form_shop_2605/service/delivery/DeliveryTrackingService.java`
- Service Impl: `src/main/java/com/re_form_shop_2605/service/delivery/DeliveryTrackingServiceImpl.java`
- External Client: `src/main/java/com/re_form_shop_2605/service/delivery/DeliveryTrackingApiClient.java`
- DTOs: `src/main/java/com/re_form_shop_2605/dto/delivery/*`
