## 🔥 실시간 알림 sse 이해를 위한 코드

[sse 관련 블로그 정리](https://haebing.tistory.com/146)



### ✔️ main 브랜치
- 기본 이해를 위한 코드
  - SseController에 ConcurrentHashMap을 public static으로 생성하여 연결및 send할때 사용
  - NotificationService에 send메서드만 생성
  
### ✔️ advance1 브랜치
- EmitterRepository 생성
  - ConcurrentHashMap을 생성하여 save, find, delete 메서드 생성
- NotificationService 상세 기능 생성
  - send, save 메서드 생성

### ✔️ advance2 브랜치
- Redis pub/sub 이용할 예정 + cache이용

