시스템 설계방법: 
  Contoller -> MultiService -> OrderService -> OrderRepository
  추후 데이터베이스 도입을위해 MultiService를 만들어 트랜잭션을 관리하고자 했습니다. 
  한 매서드안에서 모든 기능을 수행하는게 아닌 모듈화를 진행하여 다른 매서드에서 참조할수있게 만들어 유지보수의 용의성을 생각했습니다.

기능 구현 방법:
  주문 관리 시스템
  RESTful API를 통한 주문 CRUD 작업 구현 및
  WebSocket Stomp 를 통한 실시간 주문 업로드 구현

성능 최적화 기법: 
  스프링 캐시(Spring Cache) 도입 -> 추후 Redis 도입 예정
    주문 리스트 조회 성능을 향상시키기 위해 스프링 캐시를 도입했습니다. 
    추후 데이터베이스 사용을 위해 불필요한 데이터베이스 조회를 줄여 네트워크 리소스 사용과 서버의 부하를 최적화했습니다.
    캐시된 데이터를 즉시 반환함으로써 API 응답 시간을 단축했습니다.
