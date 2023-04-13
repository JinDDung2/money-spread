# DB 설계
----
* ReceivedMoneyUser: 뿌리기와 사용자를 이어주는 역할
  * 식별자 인덱스
  * 해당 뿌리기 Id
  * 뿌리기 돈 받은 사용자 Id (받지 않으면 null)
  * 뿌리기를 통해 받은 금액
* Room : 대화방 관련 테이블
  * 식별자 인덱스
* Money: 뿌리기 관련 테이블 (해당 테이블을 조회 하면 조회API 결과를 얻을 수 있음)
  * 식별자 인덱스
  * 뿌리기 고유 토큰 (3자리 문자열)
  * 대화방 식별자 값 (Room_FK)
  * 뿌리기 만든 사람 (User_FK)
  * 뿌린 금액
  * 분배할 인원 수 (뿌리기 받을 인원 수)
  * 뿌리기를 받는 사용자들 정보 (ReceivedMoneyUser_FK)
  * 뿌리기 만든 날짜
  * 뿌리기 잔액 (MoneyDto 에서 계산하여 응답) 
* User: 사용자 관련 테이블
  * 식별자 인덱스
  * 자산
---
# API End-point
주의사항 -> 서버 포트 9000
## 사용자 등록
### [Post] http://localhost:9000/api/user
requestBody
```json
  {
  "budget" : 30000
}
```
## 방 생성
### [Post] http://localhost:9000/api/room
requestHeader
```json
X-USER-ID: 대화방 생성할 사용자
```
requestBody 없음

## 방 입장
### [Post] http://localhost:9000/api/room/enter
requestHeader
```json
X-USER-ID: User 인덱스
X-ROOM-ID: Room 인덱스
```
requestBody 없음

## 머니 뿌리기
### [Post] http://localhost:9000/api/money
requestHeader
```json
X-USER-ID: User 인덱스
X-ROOM-ID: Room 인덱스
```
requestBody
```json
{
    "budget" : 10000, 
    "quantity" : 3  
}
```
## 머니 받기
### [Post] http://localhost:9000/api/money/receive
requestHeader
```json
X-USER-ID: User 인덱스
X-ROOM-ID: Room 인덱스
```
requestBody
```json
{
  "token":"???"
}
```
## 뿌리기 조회
### [Get] http://localhost:9000/api/money/info?token=???
requestHeader
```json
X-USER-ID: User 인덱스
X-ROOM-ID: Room 인덱스
```

