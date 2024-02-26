## 간단한 게시판 프로젝트

### Entity - board, post, reply

## API 명세서

#### [POST] /api/board - 게시판 생성

#### [POST] /api/post - 게시글 작성

#### [GET] /api/post/all - 게시판의 게시글 전체 조회

#### [[POST] /api/post/view - 게시글 상세보기 (1)](https://github.com/k9want/simple_board/blob/39c6e2d68f8cc37aa95809b0a9c2a69889d8ad39/src/main/java/com/example/simpleboard/post/db/service/PostService.java)

- (1) 게시글이 있는가? (2) 비밀번호가 맞는가?
- GET이 아닌 POST인 이유: 비공개 게시글을 관리하는 게시판이기에 비밀번호를 알아야만 조회가 가능하다.
- GET으로 조회해서 가져오기엔 password를 통한 로직을 넣어야 한다. + POST로 body에 넣어서 전달할 필요가 있다.
- status가 "REGISTERED"인 경우만 열람이 가능하다.

#### [[POST] /api/post/view - 게시글 상세보기 + 댓글 리스트 (2)](https://github.com/k9want/simple_board/commit/9cef496320293a2479df4b268f4ce30da37a123f)

- @transient Entity의 컬럼으로 인식하지 않게 한다.
- replyService에서 findAllByPostId 메소드를 통해 댓글 리스트를 가져와서 setReplyList에 넣기

#### [POST] /api/post/delete

- status를 "REGISTERED" -> "UNREGISTERED"로 수정
- POST 혹은 PATCH

#### [POST] /api/reply - 댓글 작성

## JPA 연관관계 설정하기

### 테이블 관계

board : post = 1 : N
<br/>
post : reply = 1 : N
<br/>
<br/>

### 강의내용

- JPA를 사용하면 오브젝트로 ORM으로서 활용이 가능하다.
    - ex) findAllByPostIdAndStatusOrderByIdDesc
    - post_id를 가지고 전체를 찾아오는 부분이 있다.
- 객체지향적으로 데이터베이스를 컨트롤하는게 가능해진다.

### 혼자서 찾아보기

#### 객체 관계 매핑(ORM)이란?

- 객체 관계 매핑(Object-Relational Mapping, ORM)은 객체 지향 프로그래밍 언어를 사용하여 호환되지 않는 유형의 시스템 간에 데이터를 변환하는 프로그래밍 기술입니다. <br/>
- 즉, ORM을 사용하면, 데이터베이스의 테이블을 객체로 매핑하여 데이터베이스의 레코드에 대응하는 객체 인스턴스를 통해 데이터베이스를 조작할 수 있습니다. <br/>
- 이렇게 하면, 개발자는 SQL 쿼리를 직접 작성하는 대신 객체 지향 언어의 문법을 사용하여 데이터베이스 작업을 수행할 수 있게 됩니다.

#### ORM의 핵심 이점

- 개발 생산성과 유지보수: 객체 코드 내에서 SQL 쿼리를 작성하는 대신, ORM 프레임워크가 자동으로 SQL을 생성합니다. 이로 인해 개발 속도가 빨라지고, 코드 유지보수가 쉬워집니다.
- 데이터베이스 독립성: ORM을 사용하면 데이터베이스 변경 시 SQL 쿼리를 다시 작성할 필요 없이, 대부분의 경우 ORM 프레임워크 설정만으로 데이터베이스를 교체할 수 있습니다.
- 객체 지향 개발: 데이터를 객체와 클래스로 다루기 때문에, 객체 지향 프로그래밍의 이점(상속, 다형성, 캡슐화 등)을 데이터베이스 작업에도 적용할 수 있습니다.

#### JPA와 객체 관계 매핑

- Java Persistence API(JPA)는 Java ORM 기술에 대한 API 표준입니다.
- JPA를 사용하면 엔티티라는 객체를 통해 데이터베이스를 객체지향적으로 다룰 수 있습니다.
- JPA는 개발자가 데이터베이스 테이블과 자바 객체 간의 매핑 정보를 설정하면, 이 정보를 바탕으로 애플리케이션에서 필요한 SQL을 자동으로 생성하고 실행합니다.

#### JPA를 사용하는 이유

- 데이터베이스 코드의 객체 지향적 통합: JPA를 사용하면 데이터베이스 작업을 객체 지향적으로 설계하고 구현할 수 있어, 객체 지향 프로그래밍의 장점을 데이터베이스 관리에도 적용할 수 있습니다.
- 표준화된 API: JPA는 자바 진영의 표준으로, 다양한 ORM 프레임워크(Hibernate, EclipseLink, OpenJPA 등)가 JPA를 구현하고 있습니다. 이로 인해, 특정 구현체에 종속되지 않고,
  표준 API를 통해 데이터베이스 작업을 할 수 있습니다.
- 유연성과 확장성: JPA를 사용하면, 복잡한 조회나 트랜잭션 관리도 표준화된 방법으로 처리할 수 있으며, 필요에 따라 쿼리를 최적화하거나, 특정 데이터베이스 기능을 사용하는 등의 확장이 가능합니다.

### 결론

JPA는 개발자로 하여금 SQL을 직접 작성하는 대신 객체 모델을 통해 데이터베이스와 상호 작용하게 함으로써, 객체 지향 설계와 데이터베이스 간의 간극을 줄여줍니다.