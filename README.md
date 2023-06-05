# Librarian API

## Tech Stack
- Java 11
- Spring Boot 2.1.15
- Postgres 14
- Lombok
- JUnit 4

## API Spec
- Member API
  - Menambahkan Peminjam
    - Method: POST
    - URL: /api/library/member
    - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
    - Request Body ```{
      "idCardNumber": "123456",
      "name": "John Doe"
      }```
    - Response Body ```{
      "data": {
      "name": "John Doe",
      "memberId": "ME000000000008"
      }
      }```
- Book API
  - Menambahkan Buku
    - Method: POST
    - URL: /api/library/book
    - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
    - Request Body ```{
      "bookName": "Lord of The Ring",
      "isbn": "ISB.1122.3335",
      "stock": 2
      }```
    - Response Body ```{
      "data": {
      "bookId": "BK000000000002",
      "bookName": "Lord of The Ring",
      "isbn": "ISB.1122.3335",
      "stock": 2
      }
      }```
  - Mendapatkan Data Buku
    - Method: GET
    - URL: /api/library/book
    - Semua Data
      - Request Parameter ```?is_all=true```
      - Response Body ```{
        "data": [
        {
        "bookId": "BK000000000001",
        "bookName": "Avatar",
        "isbn": "ISB.1122.3334",
        "stock": 4
        },
        {
        "bookId": "BK000000000002",
        "bookName": "Lord of The Ring",
        "isbn": "ISB.1122.3335",
        "stock": 0
        }
        ]
        }```
    - Buku Tersedia
      - Request Parameter ```?is_all=true```
      - Response Body ```{
        "data": [
        {
        "bookId": "BK000000000001",
        "bookName": "Avatar",
        "isbn": "ISB.1122.3334",
        "stock": 4
        }
        ]
        }```
  - Perubahan Stok Buku
    - Method: PUT
    - URL: /api/library/book
    - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
    - Request Body ```{
      "bookId":"BK000000000001",
      "stock":4
      }```
    - Response Body ```{
      "data": {
      "bookId": "BK000000000002",
      "bookName": "Lord of The Ring",
      "isbn": "ISB.1122.3335",
      "stock": 4
      }
      }```
- Transaction API
  - Inquiry Transaksi
    - Method: POST
    - URL: /api/library/transaction/inquiry
    - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
    - Request Body ```{
      "bookId": "BK000000000001",
      "quantity": 4,
      "memberId": "ME000000000003",
      "duration": 3
      }```
    - Response Body ```{
      "data": {
      "transactionId": "TR000000000008",
      "memberId": "ME000000000003"
      }
      }```
  - Execute Transaksi
    - Method: POST
    - URL: /api/library/transaction/execute
    - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
    - Request Body ```{
      "transactionId": "TR000000000006"
      }```
    - Response Body ```{
      "data": {
      "transactionId": "TR000000000008",
      "bookName": "Avatar",
      "status": "SUCCESS"
      }
      }```
  - Mendapatkan Data Transaksi
    - Method: GET
    - URL: /api/library/transaction
      - Berdasarkan state
        - Request Parameter ```?state=INQUIRY```
        - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
        - Response Body ```{
          "data": [
          {
          "transactionId": "TR000000000002",
          "memberId": "ME000000000003",
          "memberName": "John Doe",
          "bookId": "BK000000000001",
          "bookName": "Avatar",
          "quantity": 4,
          "status": "SUCCESS",
          "duration": 3,
          "isLate": false,
          "transactionDate": "2023-06-05T06:18:39.536+0000",
          "state": "INQUIRY"
          } ] }```
      - Berdasarkan transactionId
        - Request Parameter ```?transaction_id=TR000000000008```
        - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
        - Response Body ```{
          "data": {
          "transactionId": "TR000000000008",
          "memberId": "ME000000000003",
          "memberName": "John Doe",
          "bookId": "BK000000000001",
          "bookName": "Avatar",
          "quantity": 4,
          "status": "SUCCESS",
          "duration": 3,
          "isLate": false,
          "transactionDate": "2023-06-05T09:39:01.758+0000"
          }
          }```
      - Semua Data
        - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
        - Response Body ```{"data": [        {
          "transactionId": "TR000000000004",
          "memberId": "ME000000000003",
          "memberName": "John Doe",
          "bookId": "BK000000000001",
          "bookName": "Avatar",
          "quantity": 4,
          "status": "SUCCESS",
          "duration": 3,
          "isLate": false,
          "transactionDate": "2023-06-05T06:18:58.314+0000",
          "state": "INQUIRY"
          },
          {
          "transactionId": "TR000000000006",
          "memberId": "ME000000000003",
          "memberName": "John Doe",
          "bookId": "BK000000000001",
          "bookName": "Avatar",
          "quantity": 4,
          "status": "RETURNED",
          "duration": 3,
          "isLate": true,
          "transactionDate": "2023-06-05T06:19:26.406+0000",
          "returnDate": "2023-06-15T06:28:54.232+0000",
          "state": "EXECUTE"
          }]}```
  - Mengembalikan Buku
    - Method: PUT
    - URL: /api/library/transaction/returned
    - Request Header ```X-Key: 21232f297a57a5a743894a0e4a801fc3```
    - Request Body ```{
      "transactionId": "TR000000000008",
      "status":"RETURNED"
      }```
    - Response Body ```{
      "data": {
      "transactionId": "TR000000000008",
      "bookName": "Avatar",
      "status": "RETURNED",
      "isLate": false
      }
      }```