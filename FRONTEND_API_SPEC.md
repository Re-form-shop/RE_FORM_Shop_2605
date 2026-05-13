# RE:FORM Frontend API Spec

원본 시트: [RE_FORM_API_Spec](https://docs.google.com/spreadsheets/d/1fzhcdGxei8Vn_SAbHh90ehmZo-olkXsHYqxhg11ex3o/edit?gid=2070352214#gid=2070352214)

이 문서는 프론트엔드에서 정리한 API 명세 시트를 프로젝트 내부에서 바로 확인할 수 있도록 마크다운으로 재구성한 버전이다.  
`프론트 시트 기준 상태`와 `현재 백엔드 코드 기준 메모`를 함께 정리했다.

## 요약

- 전체 API 수: `57`
- 프론트 시트 기준 구현 완료: `3`
- 프론트 시트 기준 부분 구현: `1`
- 프론트 시트 기준 미구현: `53`

도메인별 집계:

| 도메인 | 시트명 | API 수 | 구현완료 | 부분구현 | 미구현 |
| --- | --- | ---: | ---: | ---: | ---: |
| 인증 / 회원 | Auth | 10 | 0 | 0 | 10 |
| 판매글 | Listing | 8 | 0 | 0 | 8 |
| 채팅 | Chat | 4 | 0 | 0 | 4 |
| 거래 | Trade | 5 | 0 | 0 | 5 |
| 결제 | Payment | 4 | 3 | 1 | 0 |
| 커뮤니티 | Community | 9 | 0 | 0 | 9 |
| 마이페이지 / 포인트 | MyPage | 6 | 0 | 0 | 6 |
| 알림 / 신고 | Noti_Report | 5 | 0 | 0 | 5 |
| 관리자 | Admin | 6 | 0 | 0 | 6 |

## 1. 인증 / 회원 (Auth)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 이메일 로그인 | `POST` | `/api/auth/login` | N | `email`, `password` | `accessToken`, `refreshToken`, `user` | 미구현 | 구현됨 |
| 토큰 갱신 | `POST` | `/api/auth/token/refresh` | N | `refreshToken` | `accessToken` | 미구현 | 구현됨. 프론트 명세와 같이 `accessToken`만 재발급 |
| 로그아웃 | `POST` | `/api/auth/logout` | Y | `refreshToken` | `204` | 미구현 | 구현됨 |
| 회원가입 | `POST` | `/api/auth/register` | N | `email`, `password`, `nickname`, 약관 동의 | 토큰 + 유저 정보 | 미구현 | 구현됨 |
| 닉네임 중복 확인 | `GET` | `/api/auth/check-nickname` | N | `nickname` | `available` | 미구현 | 구현됨 |
| 카카오 소셜 로그인 | `GET` | `/api/auth/oauth/kakao` | N | - | `302 redirect` | 미구현 | 구현됨. 카카오 로그인 페이지로 리다이렉트 |
| 카카오 콜백 | `GET` | `/api/auth/oauth/kakao/callback` | N | `code`, `state` | 토큰 + `isNewUser` | 미구현 | 백엔드 공개 API는 두지 않고 스프링 시큐리티 콜백 `/login/oauth2/code/kakao` 사용 |
| 구글 소셜 로그인 | `GET` | `/api/auth/oauth/google` | N | - | `302 redirect` | 미구현 | 구현됨. 구글 로그인 페이지로 리다이렉트 |
| 구글 콜백 | `GET` | `/api/auth/oauth/google/callback` | N | `code`, `state` | 토큰 + `isNewUser` | 미구현 | 백엔드 공개 API는 두지 않고 스프링 시큐리티 콜백 `/login/oauth2/code/google` 사용 |
| 관심 설정 저장 | `POST` | `/api/users/me/interest-setting` | Y | `sports`, `leagues`, `teams`, `keywords` | 저장 완료 메시지 | 미구현 | 구현됨 |

추가로 현재 백엔드에는 프론트 시트에 없는 인증 API도 있다.

- `GET /api/auth/me`
- `GET /api/auth/sessions`
- `POST /api/auth/logout/session`
- `POST /api/auth/logout/all`
- `POST /api/auth/password/reset`

## 2. 판매글 (Listing)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 판매글 목록 조회 | `GET` | `/api/listings` | N | `sport`, `league`, `condition`, `tradeType`, `keyword`, `page`, `size`, `sort` | 목록 + 페이지 정보 | 미구현 | 구현은 되어 있으나 실제 컨트롤러는 `league`, `condition`, `sort`를 아직 받지 않음 |
| 판매글 상세 조회 | `GET` | `/api/listings/{id}` | N | `id` | 상세 정보 + `seller` + `isLiked` | 미구현 | 구현됨 |
| 판매글 작성 | `POST` | `/api/listings` | Y | JSON 본문 | `{id}` | 미구현 | 구현됨. `title`, `description`, `price`, `condition`, `sport`, `team`, `size`, `tradeType`, `imageUrls[]` JSON 사용 |
| 판매글 수정 | `PATCH` | `/api/listings/{id}` | Y | 부분 수정 JSON | `{id}` | 미구현 | 구현됨. `PATCH` + JSON 본문 + `imageUrls[]` 방식 사용 |
| 판매글 삭제 | `DELETE` | `/api/listings/{id}` | Y | `id` | `204` | 미구현 | 구현됨 |
| 찜 추가 / 취소 | `POST` | `/api/listings/{id}/like` | Y | `id` | `isLiked`, `likeCount` | 미구현 | 현재 전용 controller endpoint 확인 필요. 시트 기준 요구만 존재 |
| 이미지 업로드 | `POST` | `/api/listings/images` | Y | `multipart images[]` | `urls[]` | 미구현 | 구현됨. 업로드 후 `/uploads/post/temp/{memberId}/...` 형식의 URL 목록 반환 |
| AI 설명 추천 | `POST` | `/api/listings/ai/suggest` | Y | 상품 키워드 | `description` | 미구현 | 미구현 |

## 3. 채팅 (Chat)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 채팅방 목록 조회 | `GET` | `/api/chats` | Y | `page`, `size` | 채팅방 요약 목록 | 미구현 | REST controller 미구현. STOMP/WebSocket 구성만 일부 존재 |
| 채팅 메시지 조회 | `GET` | `/api/chats/{roomId}/messages` | Y | `roomId`, 페이지 정보 | 메시지 목록 | 미구현 | 미구현 |
| 채팅방 생성 | `POST` | `/api/chats` | Y | `listingId` | `roomId` | 미구현 | 미구현 |
| 메시지 전송 | `POST` | `/api/chats/{roomId}/messages` | Y | `message` | 메시지 식별자 | 미구현 | REST 기준 미구현. WebSocket 송수신 구조만 존재 |

## 4. 거래 (Trade)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 거래 생성 | `POST` | `/api/trades` | Y | `listingId` | `tradeId`, `status` | 미구현 | 구현됨 |
| 거래 상세 조회 | `GET` | `/api/trades/{id}` | Y | `id` | 거래 상세 | 미구현 | 구현됨 |
| 구매 확정 | `PATCH` | `/api/trades/{id}/confirm` | Y | `id` | `status=CONFIRMED` | 미구현 | 구현됨 |
| 매너 평가 작성 | `POST` | `/api/trades/{id}/reviews` | Y | `score`, `comment` | `reviewId` | 미구현 | 구현됨 |
| 내 거래 목록 | `GET` | `/api/trades/my` | Y | `role`, `status`, `page`, `size` | 거래 목록 | 미구현 | 구현은 되어 있으나 현재 `status` 필터는 없음 |

프론트 시트에는 없지만 현재 백엔드에는 `PATCH /api/trades/{id}/delivery`도 있다.

## 5. 결제 (Payment)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 결제 초기화 | `POST` | `/api/payments/init` | Y | `tradeId`, `payMethod` | `tossOrderId`, `orderName`, `amount` | 구현완료 | 구현됨. 구매자 식별은 JWT principal 기준으로 처리 |
| 결제 승인 | `POST` | `/api/payments/confirm` | Y | `paymentKey`, `orderId`, `amount` | 결제 상세 | 구현완료 | 구현됨 |
| 결제 정보 조회 | `GET` | `/api/payments/{tradeId}` | Y | `tradeId` | 결제 상세 | 부분구현 | 현재 `501` 수준 stub |
| 결제 취소 / 환불 | `POST` | `/api/payments/{paymentKey}/cancel` | Y | `cancelReason`, `cancelType` | 결제 상세 | 구현완료 | 구현됨 |

## 6. 커뮤니티 (Community)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 게시글 목록 조회 | `GET` | `/api/community` | N | `page`, `size`, `sort`, `keyword` | 게시글 목록 | 미구현 | controller 미구현 |
| 게시글 상세 조회 | `GET` | `/api/community/{id}` | N | `id` | 게시글 상세 | 미구현 | 미구현 |
| 게시글 작성 | `POST` | `/api/community` | Y | `title`, `content` | `{id}` | 미구현 | 미구현 |
| 게시글 수정 | `PATCH` | `/api/community/{id}` | Y | 수정 필드 | `{id}` | 미구현 | 미구현 |
| 게시글 삭제 | `DELETE` | `/api/community/{id}` | Y | `id` | `204` | 미구현 | 미구현 |
| 게시글 좋아요 | `POST` | `/api/community/{id}/like` | Y | `id` | `isLiked`, `likeCount` | 미구현 | 미구현 |
| 댓글 목록 조회 | `GET` | `/api/community/{id}/comments` | N | `id`, 페이지 정보 | 댓글 목록 | 미구현 | 미구현 |
| 댓글 작성 | `POST` | `/api/community/{id}/comments` | Y | `content` | `{id}` | 미구현 | 미구현 |
| 댓글 삭제 | `DELETE` | `/api/community/{postId}/comments/{commentId}` | Y | `postId`, `commentId` | `204` | 미구현 | 미구현 |

## 7. 마이페이지 / 포인트 (MyPage)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 내 프로필 조회 | `GET` | `/api/users/me` | Y | - | 프로필 정보 | 미구현 | 구현됨 |
| 프로필 수정 | `PATCH` | `/api/users/me` | Y | `nickname`, `bio`, `profileImageUrl` | 수정된 프로필 | 미구현 | 구현됨. JSON 본문으로 수정하고 프로필 이미지는 별도 업로드 API URL 사용 |
| 포인트 내역 조회 | `GET` | `/api/users/me/points` | Y | `type`, `page`, `size` | 활동/정산 포인트 + 내역 | 미구현 | 현재 실제 API는 `/api/points/wallet`, `/api/points/history`로 분리됨 |
| 포인트 출금 신청 | `POST` | `/api/users/me/points/withdraw` | Y | 출금 요청 정보 | `requestId`, `status` | 미구현 | 현재 controller endpoint 없음 |
| 받은 매너 평가 목록 | `GET` | `/api/users/me/reviews` | Y | `page`, `size` | 평점 평균 + 리뷰 목록 | 미구현 | 구현됨 |
| 관심 설정 조회 | `GET` | `/api/users/me/interest-setting` | Y | - | 관심 설정 정보 | 미구현 | 구현됨 |

## 8. 알림 / 신고 (Noti_Report)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 알림 목록 조회 | `GET` | `/api/notifications` | Y | `page`, `size` | `content`, `unreadCount` | 미구현 | 구현됨 |
| 알림 읽음 처리 | `PATCH` | `/api/notifications/{id}/read` | Y | `id` | `id`, `isRead` | 미구현 | 구현됨 |
| 전체 알림 읽음 | `PATCH` | `/api/notifications/read-all` | Y | - | `updatedCount` | 미구현 | 구현됨 |
| 신고 접수 | `POST` | `/api/reports` | Y | `targetType`, `targetId`, `reason` | `reportId` | 미구현 | 구현됨 |
| 내 신고 목록 | `GET` | `/api/reports/my` | Y | `page`, `size` | 신고 목록 | 미구현 | 구현됨 |

## 9. 관리자 (Admin)

| 기능 | Method | URL | 인증 | 요청 핵심 | 응답 핵심 | 프론트 시트 기준 상태 | 현재 백엔드 메모 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 신고 목록 조회 | `GET` | `/api/admin/reports` | Y | `status`, `page`, `size` | 신고 목록 | 미구현 | controller 미구현 |
| 신고 처리 | `PATCH` | `/api/admin/reports/{id}` | Y | `status`, `adminMemo` | 처리 결과 | 미구현 | 미구현 |
| 회원 목록 조회 | `GET` | `/api/admin/users` | Y | `keyword`, `status`, `page`, `size` | 회원 목록 | 미구현 | 미구현 |
| 회원 상태 변경 | `PATCH` | `/api/admin/users/{id}/status` | Y | `status`, `reason` | 상태 결과 | 미구현 | 미구현 |
| 출금 요청 목록 | `GET` | `/api/admin/withdraw-requests` | Y | `status`, `page`, `size` | 출금 요청 목록 | 미구현 | 미구현 |
| 출금 처리 | `PATCH` | `/api/admin/withdraw-requests/{id}` | Y | `status`, `adminMemo` | 처리 결과 | 미구현 | 미구현 |

## 프론트 시트와 현재 백엔드의 주요 차이

### 1. 인증 경로 차이
- 소셜 로그인 진입점은 프론트 시트와 같이 `/api/auth/oauth/kakao`, `/api/auth/oauth/google`로 맞췄다.
- 다만 실제 OAuth2 provider callback은 프론트 시트의 `/api/auth/oauth/.../callback`이 아니라 스프링 시큐리티 경로 `/login/oauth2/code/{provider}`를 사용한다.

### 2. JWT 인증 방식과 도메인 API 인증 방식 차이
- 인증 API와 주요 도메인 API는 `Authorization: Bearer {accessToken}` 기반 principal 복원으로 동작한다.
- `X-Member-Id` 임시 헤더 의존은 제거했다.

### 3. 판매글 작성/수정 명세 차이
- 프론트 시트와 같이 판매글 작성/수정은 `imageUrls[]`를 포함한 JSON 방식으로 맞췄다.
- 별도 이미지 업로드 API인 `/api/listings/images`로 먼저 URL을 받고, 그 URL을 판매글 API에 넘기면 된다.

### 4. 포인트 API 경로 차이
- 프론트 시트는 `/api/users/me/points`와 `/api/users/me/points/withdraw` 기준이다.
- 현재 백엔드는 `/api/points/wallet`, `/api/points/history`만 있으며 출금 요청 endpoint는 없다.

### 5. 토큰 재발급 응답 차이
- 프론트 시트와 같이 재발급 응답은 새 `accessToken`만 반환한다.
- refresh token은 Redis 검증 후 만료 전까지 같은 세션에서 계속 사용한다.

## 이번에 프론트 명세에 맞춘 항목

- 인증 경로
  - 소셜 로그인 진입점을 `/api/auth/oauth/kakao`, `/api/auth/oauth/google`로 통일
- 인증 방식
  - 주요 보호 API를 `Authorization: Bearer {accessToken}` 기반 principal 인증으로 정리
  - `X-Member-Id` 임시 헤더 의존 제거
- 판매글 작성/수정
  - `POST /api/listings`, `PATCH /api/listings/{id}`를 JSON + `imageUrls[]` 방식으로 변경
- 프로필 이미지/프로필 수정
  - `POST /api/users/me/profile-image` 업로드 API 추가
  - `PATCH /api/users/me`를 JSON + `profileImageUrl` 방식으로 변경
- 토큰 재발급
  - `POST /api/auth/token/refresh` 응답을 `accessToken`만 반환하는 형태로 정리

## 현재 해야 할 일

### 1. 프론트-백엔드 계약 확정

- 소셜 로그인 콜백 처리 방식 확정
  - 백엔드는 `/api/auth/oauth/.../callback` 공개 API를 두지 않고 `/login/oauth2/code/{provider}`를 사용한다.
  - 프론트는 최종적으로 `app.auth.oauth-success-redirect-uri`로 이동한 뒤 fragment의 `accessToken`, `refreshToken`을 읽는 구조를 기준으로 붙어야 한다.
- 판매글 이미지 업로드 방식 확정
  - 현재 판매글 API는 `imageUrls[]`만 받는다.
  - 별도 업로드 API인 `/api/listings/images`를 통해 먼저 URL 목록을 받은 뒤 판매글 JSON에 넣는 흐름으로 사용하면 된다.
- 프로필 이미지 업로드 방식 확정
  - 현재 프로필 수정 API는 `profileImageUrl`만 받는다.
  - 별도 업로드 API인 `/api/users/me/profile-image`로 먼저 URL을 받은 뒤 프로필 수정 JSON에 넣는 흐름으로 사용하면 된다.
- 포인트 API 경로 확정
  - 프론트 시트는 `/api/users/me/points` 계열을 기대한다.
  - 현재 백엔드는 `/api/points/wallet`, `/api/points/history`를 사용하므로 어느 쪽으로 통일할지 정해야 한다.

### 2. 지금 바로 구현이 필요한 백엔드 작업

- 판매글 목록 조회 조건 보강
  - `/api/listings`에 `league`, `condition`, `sort` 필터를 추가 구현해야 한다.
- 판매글 찜 API 구현
  - `/api/listings/{id}/like`
- 판매글 이미지 업로드 API 구현 여부 결정
  - 구현 완료. 프론트는 업로드한 결과의 `urls[]`를 판매글 작성/수정 JSON에 넣으면 된다.
- 판매글 AI 설명 추천 API 구현
  - `/api/listings/ai/suggest`
- 결제 조회 API 완성
  - `/api/payments/{tradeId}`는 아직 stub 상태
- 포인트 출금 API 구현
  - `/api/users/me/points/withdraw` 또는 대체 경로 확정 후 구현
- 포인트 조회 경로 어댑터 추가 여부 결정
  - 프론트 시트를 그대로 쓸 거면 `/api/users/me/points` 응답 조합 API가 필요
- 프로필 이미지 업로드 API 시트 반영
  - 현재 프론트 시트에는 `/api/users/me/profile-image`가 없으므로 문서 반영이 필요하다.

### 3. 유스케이스 기준으로 아직 큰 덩어리로 남은 영역

- 채팅 REST API
  - 채팅방 목록
  - 메시지 조회
  - 채팅방 생성
  - REST 메시지 전송
- 커뮤니티 전체
  - 게시글 CRUD
  - 댓글 CRUD
  - 좋아요
- 관리자 기능 전체
  - 신고 관리
  - 회원 상태 관리
  - 출금 요청 처리
- 검색/AI 기능
  - 복합 검색
  - 의미 기반 유사 검색
  - 판매글/채팅 위험 탐지

### 4. 프론트 연동 시 체크할 항목

- 소셜 로그인 성공 후 `window.location.hash`에서 토큰 읽기
- 토큰 재발급 후에는 기존 `refreshToken`을 유지하고 `accessToken`만 교체
- 보호 API 호출 시 `Authorization: Bearer {accessToken}` 헤더 사용
- 판매글 작성/수정 시 `multipart/form-data`가 아니라 JSON 본문 사용
- 판매글 이미지 업로드 후 응답으로 받은 `urls[]`를 그대로 판매글 작성/수정 JSON의 `imageUrls[]`에 전달하기
- 프로필 이미지 업로드 후 응답으로 받은 `profileImageUrl`을 프로필 수정 JSON에 전달하기

## 유스케이스 대비 아직 구현되지 않았거나 덜 구현된 영역

[REFORM_USE_CASES.md](C:/dev/RE_FORM_Shop_2605/REFORM_USE_CASES.md) 기준으로 보면, 현재 코드에서 크게 남아 있는 부분은 아래와 같다.

### 우선순위 높음
- 커뮤니티 전체 기능
  - 게시글 CRUD
  - 댓글 / 대댓글
  - 좋아요
  - 인기글 노출
- 채팅 REST 기능
  - 채팅방 목록
  - 메시지 조회
  - 채팅방 생성
  - REST 메시지 전송
  - 읽음 처리 / 미읽음 카운트
- 관리자 기능 전체
  - 신고 관리
  - 회원 상태 변경
  - 출금 승인 / 반려
- 검색 기능
  - 복합 필터
  - 정렬
  - 의미 기반 유사 검색
- AI 기능
  - 판매글 자동 작성 보조
  - 판매글 위험 탐지
  - 채팅 위험 문구 실시간 탐지
  - 위험 탐지 배치 재검사

### 부분 구현
- 결제
  - 결제 조회 API 미완성
- 포인트
  - 출금 요청 API 미구현
  - 내 출금 요청 목록 미구현
- 판매글
  - 찜 토글 API 정리 필요
  - 가격 인하 알림 미구현
  - AI 설명 추천 미구현
- 거래
  - 상태 머신 정교화 필요
  - 분쟁 처리와 결제 상태 연동 보강 필요
- 마이페이지
  - 포인트 API 계약 프론트와 불일치

### 정리 필요
- `temporary`, `NOTICE`, `CHECK` 흔적 파일 정리
- 프론트 시트와 백엔드 경로/요청 형식 재합의

## 추천 정리 순서

1. 판매글/포인트 계약 확정
   - 판매글 이미지 업로드 방식
   - 포인트 API 경로
2. 결제 / 포인트 마무리
   - 결제 조회 완성
   - 포인트 출금 API 추가
   - `/api/users/me/points` 계열 어댑터 여부 결정
3. 판매글 보강
   - 목록 필터 추가
   - 찜 API
   - AI 설명 추천
4. 커뮤니티와 채팅 구현
5. 관리자 기능 구현
6. 검색 / AI 기능 구현
