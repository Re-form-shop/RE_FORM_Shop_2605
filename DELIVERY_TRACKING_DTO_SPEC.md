# RE:FORM 택배조회 DTO 명세서

> 작성일: 2026-05-12  
> 대상: 백엔드 / 프론트엔드 / QA  
> 관련 외부 문서: [deliveryapi tracking docs](https://www.deliveryapi.co.kr/docs#tracking-trace)

---

## 1. 개요

이 문서는 RE:FORM 프로젝트에 추가된 택배조회 기능의 DTO 구조를 정리한 문서다.  
현재 구현 기준 패키지는 `com.re_form_shop_2605.dto.delivery` 이다.

구현된 컨트롤러 엔드포인트:

- `GET /api/delivery/tracking/couriers`
- `POST /api/delivery/tracking/trace`

---

## 2. 택배사 목록 응답 DTO

파일:

- `src/main/java/com/re_form_shop_2605/dto/delivery/DeliveryCourierListResponseDTO.java`

```java
public record DeliveryCourierListResponseDTO(
    boolean isSuccess,
    CourierListData data
) {}
```

### 2-1. CourierListData

```java
public record CourierListData(
    List<CourierDTO> couriers,
    int total
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `couriers` | `List<CourierDTO>` | 지원 택배사 목록 |
| `total` | `int` | 택배사 총 개수 |

### 2-2. CourierDTO

```java
public record CourierDTO(
    String trackingApiCode,
    String displayName
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `trackingApiCode` | `String` | 택배사 코드 예: `cj`, `lotte`, `post` |
| `displayName` | `String` | 사용자 표시용 이름 예: `CJ대한통운` |

---

## 3. 송장 조회 요청 DTO

파일:

- `src/main/java/com/re_form_shop_2605/dto/delivery/DeliveryTrackingTraceRequestDTO.java`

```java
public record DeliveryTrackingTraceRequestDTO(
    List<TraceItemRequestDTO> items
) {}
```

| 필드 | 타입 | 제약 | 설명 |
| --- | --- | --- | --- |
| `items` | `List<TraceItemRequestDTO>` | `@NotEmpty` | 조회할 송장 목록 |

### 3-1. TraceItemRequestDTO

```java
public record TraceItemRequestDTO(
    String clientId,
    String courierCode,
    String trackingNumber
) {}
```

| 필드 | 타입 | 제약 | 설명 |
| --- | --- | --- | --- |
| `clientId` | `String` | `@Size(max = 100)` | 클라이언트 내부 주문 식별자. 선택값 |
| `courierCode` | `String` | `@NotBlank`, `@Size(max = 50)` | 택배사 코드 |
| `trackingNumber` | `String` | `@NotBlank`, `@Size(max = 100)` | 송장번호 |

### 3-2. 요청 예시

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

---

## 4. 송장 조회 응답 DTO

파일:

- `src/main/java/com/re_form_shop_2605/dto/delivery/DeliveryTrackingTraceResponseDTO.java`

```java
public record DeliveryTrackingTraceResponseDTO(
    boolean isSuccess,
    TraceResponseData data
) {}
```

### 4-1. TraceResponseData

```java
public record TraceResponseData(
    List<TraceResultDTO> results,
    TraceSummaryDTO summary
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `results` | `List<TraceResultDTO>` | 개별 송장 조회 결과 |
| `summary` | `TraceSummaryDTO` | 전체 요청 집계 |

### 4-2. TraceResultDTO

```java
public record TraceResultDTO(
    String clientId,
    boolean success,
    TrackingDataDTO data,
    TraceErrorDTO error,
    TraceCacheDTO cache
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `clientId` | `String` | 요청 시 전달한 주문 식별자 |
| `success` | `boolean` | 개별 송장 조회 성공 여부 |
| `data` | `TrackingDataDTO` | 성공 시 배송 정보 |
| `error` | `TraceErrorDTO` | 실패 시 에러 정보 |
| `cache` | `TraceCacheDTO` | 캐시 사용 여부 |

### 4-3. TrackingDataDTO

```java
public record TrackingDataDTO(
    String trackingNumber,
    String courierCode,
    String courierName,
    String deliveryStatus,
    String deliveryStatusText,
    boolean isDelivered,
    String senderName,
    String receiverName,
    String productName,
    String arrivalBranch,
    String dateLastProgress,
    List<TrackingProgressDTO> progresses,
    String queriedAt
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `trackingNumber` | `String` | 송장번호 |
| `courierCode` | `String` | 택배사 코드 |
| `courierName` | `String` | 택배사 이름 |
| `deliveryStatus` | `String` | 정규화된 배송 상태 코드 |
| `deliveryStatusText` | `String` | 사용자 표시용 상태 텍스트 |
| `isDelivered` | `boolean` | 배송 완료 여부 |
| `senderName` | `String` | 보내는 사람 |
| `receiverName` | `String` | 받는 사람 |
| `productName` | `String` | 상품명 |
| `arrivalBranch` | `String` | 도착 영업소명 |
| `dateLastProgress` | `String` | 마지막 배송 이력 시각 |
| `progresses` | `List<TrackingProgressDTO>` | 배송 진행 이력 |
| `queriedAt` | `String` | 조회 시각 |

### 4-4. TrackingProgressDTO

```java
public record TrackingProgressDTO(
    String dateTime,
    String location,
    String status,
    String statusCode,
    String description
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `dateTime` | `String` | 처리 시각 |
| `location` | `String` | 처리 위치 |
| `status` | `String` | 원본 상태 문구 |
| `statusCode` | `String` | 정규화된 상태 코드 |
| `description` | `String` | 상세 설명 |

### 4-5. TraceErrorDTO

```java
public record TraceErrorDTO(
    String code,
    String message,
    String courierCode,
    String trackingNumber,
    Boolean billable
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `code` | `String` | 에러 코드 |
| `message` | `String` | 에러 메시지 |
| `courierCode` | `String` | 실패한 택배사 코드 |
| `trackingNumber` | `String` | 실패한 송장번호 |
| `billable` | `Boolean` | 과금 대상 여부 |

### 4-6. TraceCacheDTO

```java
public record TraceCacheDTO(
    boolean fromCache,
    String cachedAt
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `fromCache` | `boolean` | 캐시 응답 여부 |
| `cachedAt` | `String` | 캐시 저장 시각 |

### 4-7. TraceSummaryDTO

```java
public record TraceSummaryDTO(
    int total,
    int successful,
    int failed,
    int billable
) {}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| `total` | `int` | 총 요청 건수 |
| `successful` | `int` | 성공 건수 |
| `failed` | `int` | 실패 건수 |
| `billable` | `int` | 과금 대상 건수 |

---

## 5. 비고

- 현재 구현은 외부 API 응답 구조를 최대한 그대로 전달하는 중계형 DTO 구조다.
- 프론트엔드에서 사용성이 떨어지면 이후 내부 표준 응답 DTO로 한 번 더 가공할 수 있다.
- properties 키 이름은 현재 프로젝트 상태에 맞춰 `deleveryapi.API-Key`, `deleveryapi.Secret-Key`를 그대로 사용한다.
