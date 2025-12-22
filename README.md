# spring-infra
Dự án mang tính chất nghiên cứu, thử nghiệm các cấu hình của công nghệ đó trong một dự án Spring Boot. 
Dự án không nhằm phực vụ giải quyết bài toàn nào cụ thể.

## Ví dụ: spring.config.import

Module `test` minh họa cách import cấu hình bổ sung bằng Spring Boot property `spring.config.import`.

- File cấu hình chính: `test/src/main/resources/application.properties`
- File cấu hình bổ sung: `test/.env.properties` (nằm ở thư mục gốc của module `test`)

Trong `application.properties`:

```
spring.config.import=optional:file:.env.properties
```

Ý nghĩa:
- Nếu tồn tại file `test/.env.properties` thì các key trong file này sẽ được nạp và có thể ghi đè giá trị đã khai báo ở `application.properties`.
- Từ khóa `optional:` giúp ứng dụng vẫn chạy kể cả khi file không tồn tại.

Demo nhanh (đã cấu hình sẵn):
- `application.properties` khai báo `a=8001`.
- `test/.env.properties` khai báo `a=8002` để ghi đè.
- Ứng dụng có API `GET /a` trả về giá trị của `a` và cũng in giá trị này ra console khi khởi động.

Cách chạy và kiểm tra:
1. Chạy module `test` (mvnw.cmd spring-boot:run trên Windows hoặc chạy class `TestApplication`).
2. Gọi `curl http://localhost:8080/a` → Kết quả mong đợi: `8002` (giá trị từ `.env.properties`).
