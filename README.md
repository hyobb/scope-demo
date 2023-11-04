# ScopeDemoApp

Scope demo app

## Contents

- [API](#api)
  - [API Description](#api-description)
- [Development](#development)
  - [Requirements](#requirements)
  - [Test](#test)
  - [Run](#run)

## API

### API Description

http://localhost:8080/springdoc/swagger-ui/index.html

#### 1. #POST /users/sign-up

- 회원가입을 위한 API

`$ curl --location 'http://localhost:8080/users/sign-up' --header 'Content-Type: application/json' --data-raw '{
    "email": "test@test.com",
    "password": "Aabc1234"
}'`

#### 2. #POST /users/sign-in

- 로그인을 위한 API
  Authorization Token 정보를 헤더와 바디에 응답합니다.
  Token 정보는 다른 엔드포인트 이용시 필요합니다.

`$ curl --location 'http://localhost:8080/users/sign-in' --header 'Content-Type: application/json' --data-raw '{
    "email": "test@test.com",
    "password": "Aabc1234"
}'`

#### 3. #POST /slides

- 슬라이드 업로드(생성) API
  첨부된 파일을 저장하고, 슬라이드 정보를 생성합니다.

`$ curl --location 'http://localhost:8080/slides' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiJ9.90yLDxa_u6aUGrKkmtsY-DQbzHp39SQZJMUNjwTOcBg8N24HXRRiCNsKo9a-4o9An7npv3ExUXLLF_gNJVB7Pw' --form 'file=@"/Users/hyob/Desktop/Screenshot 2023-10-16 at 2.17.39 PM.png"'`

#### 4. #GET /slides/{slideId}/download

- 해당 슬라이드의 파일을 다운로드 하는 API

`$ curl --location 'http://localhost:8080/slides/1/download' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiJ9.90yLDxa_u6aUGrKkmtsY-DQbzHp39SQZJMUNjwTOcBg8N24HXRRiCNsKo9a-4o9An7npv3ExUXLLF_gNJVB7Pw'`

#### 5. #GET /users/{userId}/slides

- 해당 유저의 슬라이드 리스트를 조회하는 API
  유저는 자신이 생성한 슬라이드만 조회할 수 있습니다.
  fileName 필드로 파일 검색이 가능합니다.

`$ curl --location 'http://localhost:8080/users/1/slides?fileName=%E1%84%87%E1%85%B3%E1%84%8B%E1%85%B5%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiJ9.90yLDxa_u6aUGrKkmtsY-DQbzHp39SQZJMUNjwTOcBg8N24HXRRiCNsKo9a-4o9An7npv3ExUXLLF_gNJVB7Pw'`

#### 6. #POST /slides/{slideId}/infer

- 해당 슬라이드의 파일 분석을 요청하는 API
  유저는 자신이 생성한 슬라이드만 요청 가능합니다.
  성공 혹은 실패 로그를 응답합니다.

`$ curl --location --request POST 'http://localhost:8080/slides/2/infer' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiJ9.90yLDxa_u6aUGrKkmtsY-DQbzHp39SQZJMUNjwTOcBg8N24HXRRiCNsKo9a-4o9An7npv3ExUXLLF_gNJVB7Pw'`

### 7. #GET /users/{userId}/inference-logs

- 해당 유저의 슬라이드 분석 요청 로그를 조회하는 API

`$ curl --location 'http://localhost:8080/users/1/inference-logs?page=0&size=3' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiJ9.90yLDxa_u6aUGrKkmtsY-DQbzHp39SQZJMUNjwTOcBg8N24HXRRiCNsKo9a-4o9An7npv3ExUXLLF_gNJVB7Pw'`

## Development

### Requirements

- docker
- docker-compose
- gradle

### Test

```zsh
$ gradle test
```

### Run

```zsh
$ docker-compose up --build
```
