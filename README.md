## 간단한 게시판 프로젝트

### Entity - board, post, reply

## API 명세서

#### [POST] /api/board - 게시판 생성

#### [POST] /api/post - 게시글 작성

#### [GET] /api/post/all - 게시판의 게시글 전체 조회 (1)

#### [[GET] /api/post/all?page=?&size=? 페이지네이션 적용 - 게시판의 게시글 전체 조회(2)](https://github.com/k9want/simple_board/commit/9477bc3a705668d80eec54b8aa772d91e9d80adc)

- pagination [request] index(=page), size [response] - total, total element, current element
- page, size, currentElements, totalPage, totalElement
- ex) @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable

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

---

### 강의내용

- JPA를 사용하면 오브젝트로 ORM으로서 활용이 가능하다.
    - ex) findAllByPostIdAndStatusOrderByIdDesc
    - post_id를 가지고 전체를 찾아오는 부분이 있다.
- 객체지향적으로 데이터베이스를 컨트롤하는게 가능해진다.

### 혼자 이해하기

#### 객체 관계 매핑(ORM)이란?

- 객체 관계 매핑(Object-Relational Mapping, ORM)은 객체 지향 프로그래밍 언어를 사용하여 호환되지 않는 유형의 시스템 간에 데이터를 변환하는 프로그래밍 기술입니다. <br/>
- 즉, ORM을 사용하면, 데이터베이스의 테이블을 객체로 매핑하여 데이터베이스의 레코드에 대응하는 객체 인스턴스를 통해 데이터베이스를 조작할 수 있습니다. <br/>
- 이렇게 하면, 개발자는 SQL 쿼리를 직접 작성하는 대신 객체 지향 언어의 문법을 사용하여 데이터베이스 작업을 수행할 수 있게 됩니다.

#### ORM의 핵심 이점

- **개발 생산성과 유지보수**: 객체 코드 내에서 SQL 쿼리를 작성하는 대신, ORM 프레임워크가 자동으로 SQL을 생성합니다. 이로 인해 개발 속도가 빨라지고, 코드 유지보수가 쉬워집니다.
- **데이터베이스 독립성**: ORM을 사용하면 데이터베이스 변경 시 SQL 쿼리를 다시 작성할 필요 없이, 대부분의 경우 ORM 프레임워크 설정만으로 데이터베이스를 교체할 수 있습니다.
- **객체 지향 개발**: 데이터를 객체와 클래스로 다루기 때문에, 객체 지향 프로그래밍의 이점(상속, 다형성, 캡슐화 등)을 데이터베이스 작업에도 적용할 수 있습니다.

#### JPA와 객체 관계 매핑

- Java Persistence API(JPA)는 Java ORM 기술에 대한 API 표준입니다.
- JPA를 사용하면 엔티티라는 객체를 통해 데이터베이스를 객체지향적으로 다룰 수 있습니다.
- JPA는 개발자가 데이터베이스 테이블과 자바 객체 간의 매핑 정보를 설정하면, 이 정보를 바탕으로 애플리케이션에서 필요한 SQL을 자동으로 생성하고 실행합니다.

#### JPA를 사용하는 이유

- **데이터베이스 코드의 객체 지향적 통합**: JPA를 사용하면 데이터베이스 작업을 객체 지향적으로 설계하고 구현할 수 있어, 객체 지향 프로그래밍의 장점을 데이터베이스 관리에도 적용할 수 있습니다.
- **표준화된 API**: JPA는 자바 진영의 표준으로, 다양한 ORM 프레임워크(Hibernate, EclipseLink, OpenJPA 등)가 JPA를 구현하고 있습니다. 이로 인해, 특정 구현체에 종속되지
  않고,
  표준 API를 통해 데이터베이스 작업을 할 수 있습니다.
- **유연성과 확장성**: JPA를 사용하면, 복잡한 조회나 트랜잭션 관리도 표준화된 방법으로 처리할 수 있으며, 필요에 따라 쿼리를 최적화하거나, 특정 데이터베이스 기능을 사용하는 등의 확장이 가능합니다.

### 결론

JPA는 개발자로 하여금 SQL을 직접 작성하는 대신 객체 모델을 통해 데이터베이스와 상호 작용하게 함으로써, 객체 지향 설계와 데이터베이스 간의 간극을 줄여줍니다.

---

### 강의 내용

- [@JsonIgnore, @ToString.Exclude](https://github.com/k9want/simple_board/commit/5fc8109adec4c7fc887b5f02e95f51c237b4266a)
- 해당 어노테이션을 통해 관계를 끊어줌을 통해서 무한반복되는 걸 막아줄 수 있다.
- 하지만! 결론적인 문제는 BoardEntity를 ToString를 찍어서 문제인거고, JSON으로 만들어야할 애가 아님에도 불구하고 엔티티의 내용이 내려가면서 문제가 발생하는 것이다.

#### 해당 문제를 해결하기 위해선

- 엔티티를 사용해서 뷰까지 내리는 거를 사용하면 안 된다.
- 하지만 설정을 할 때 이런 일이 일어날 수 있기에 엔티티에 작성을 해준다.
- ✨ 가장 좋은 방법은 API에 엔티티를 내리는 게 아니라 엔티티에 상응하는 DTO를 만들어서 해당 DTO를 내려야 한다.

### 혼자 이해하기

- Java의 Spring Framework에서 JPA(Java Persistence API)를 사용하여 데이터베이스 엔티티를 관리하면서 발생할 수 있는 문제와 그 해결 방안에 대해 설명하고 있습니다.
- 특히, 엔티티 간의 양방향 관계에서 발생할 수 있는 무한 반복 문제와 JSON 직렬화 문제를 어떻게 해결할 수 있는지에 대해 언급하고 있습니다.

#### 문제의 원인

- **양방향 관계와 무한 반복**: JPA에서 엔티티 간에 양방향 관계가 설정되어 있을 경우, 한 엔티티를 통해 연관된 다른 엔티티에 접근하고, 그 연관된 엔티티가 다시 원래 엔티티를 참조하는 상황이 발생할 수
  있습니다. 이를 통해 데이터를 조회하거나 JSON 형태로 직렬화할 때 무한 반복이 발생할 수 있습니다.

- **엔티티의 직렬화 문제**: 엔티티 클래스가 데이터베이스의 테이블 구조를 반영한 모델이기 때문에, 이를 그대로 JSON으로 직렬화하여 클라이언트에 전송하면 불필요한 정보가 노출되거나, 엔티티 간의 관계로 인한
  무한 반복 문제가 발생할 수 있습니다.

#### 해결방안

- [@JsonIgnore, @ToString.Exclude 어노테이션 사용
  :](https://github.com/k9want/simple_board/commit/5fc8109adec4c7fc887b5f02e95f51c237b4266a)
    - 해당 어노테이션들을 사용하여 엔티티의 특정 필드가 JSON 직렬화나 toString() 메소드 호출에서 제외되도록 설정함으로써 무한 반복 문제를 해결할 수 있습니다.
    - @JsonIgnore는 JSON 직렬화 시 특정 필드를 무시하게 하고, @ToString.Exclude는 toString() 메소드에서 특정 필드를 제외시킵니다.

- [✨DTO(Data Transfer Object) 사용
  :](https://github.com/k9want/simple_board/commit/36267a78c8ebb5dc77e264660793fe939d635b1a)
    - API 응답으로 엔티티를 직접 반환하지 않고, 엔티티와 상응하는 DTO를 만들어서 반환하는 방법을 사용합니다.
    - DTO는 클라이언트에 전송될 데이터의 구조를 정의하는 객체로, 엔티티의 데이터를 필요한 형태로 가공하여 JSON 직렬화 문제와 무한 반복 문제를 예방할 수 있습니다.

---

### 혼자 이해하기

#### 연관관계 주인이란 무엇인가?

- 외래 키가 있는 테이블이 연관 관계의 주인이다.
    - ex) post 테이블에 board_id라는 외래키가 있다.
    - post가 연관관계의 주인이다.
- 외래 키를 실제로 가지고 있기에 데이터베이스에서 관계를 직접 관리(생성, 업데이트, 삭제)할 수 있기에 주인이라 한다.

#### @JoinColumn(name="외래키 컬럼")

- JoinColumn을 명시적으로 사용하지 않는 경우에는 JPA 구현체는 기본 규칙을 사용하여 외래 키 컬럼을 자동으로 결정한다.
- 명시적으로 직접 지정하는 것이 좋은 관례다.
- 데이터의 외래 키를 직접 지정하므로, 연관관계 매핑에 있어서 구체적인 구현을 제공한다.

#### mappedBy="참조하는 필드 이름"

- 주인이 아닌 쪽에 관계를 편리하게 사용하기 위해 엔티티에서 관계를 매핑만 해준다.
- 외래 키가 없는 테이블이기에 관계를 관리 권한이 없다.

### 정리

- 외래키를 가지고 있는 엔티티가 연관관계의 주인이 된다.
- 연관관계의 주인만이 관계를 관리할 수 있는 권한을 가진다.
- mappedBy는 연관관계가 매핑된 필드의 이름을 사용하여, 연관관계의 주인이 아닌 쪽에서 해당 관계를 지정해준다.
- @JoinColumn은 연관관계의 주인 쪽에서 외래 키를 명시적으로 지정해야할 때 필수적으로 사용해줘야 한다.

---

### 강의 내용

- [DTO를 사용하자](https://github.com/k9want/simple_board/commit/36267a78c8ebb5dc77e264660793fe939d635b1a)
- entity에 상응하는 dto를 만들고 항상 converter를 통해서 entity를 dto로 변환한다.
- 따라서 데이터를 내렸을 때 값이 이상하다면?! 해당 entity를 관리하는 converter를 먼저 찾아가서 확인하자!

### 혼자서 이해하기

- 엔티티(Entity)를 직접 API 응답으로 사용하는 것보다 DTO(Data Transfer Object)를 사용하는 것에는 여러 이유와 이점이 있습니다.

#### Entity란?

- **정의**: 데이터베이스의 테이블을 객체화한 것입니다. 각 엔티티 인스턴스는 데이터베이스의 행(row)에 해당하며, JPA(Java Persistence API)와 같은 ORM(Object-Relational
  Mapping)을 사용하여 관리됩니다.
- **용도**: 데이터의 영속성(persistence)을 관리하는 데 사용됩니다. 즉, 데이터를 생성(create), 조회(read), 업데이트(update), 삭제(delete)하는 CRUD 작업을 위해
  사용됩니다.
- **특징**: 데이터베이스의 구조를 반영하므로, 테이블의 구조가 변경되면 엔티티도 함께 변경되어야 합니다.

#### DTO란?

- **정의**: 계층 간 데이터 교환을 위해 사용되는 객체로, 필요한 데이터만을 가지고 있습니다. 이는 엔티티의 데이터 구조와 다를 수 있으며, 사용자의 요청에 맞추어 데이터를 전송하는 데 초점을 맞춥니다.
- DTO는 특정 뷰나 API 요구 사항에 맞춰서 데이터를 전달하는 객체입니다.
- 엔티티 클래스와 데이터베이스 구조를 직접적으로 노출하지 않으면서, 필요한 데이터만 클라이언트에 전달할 수 있게 해줍니다.
- DTO를 사용하면 엔티티의 변경이 API 스펙에 영향을 미치지 않도록 할 수 있으며, 복잡한 연관 관계를 갖는 엔티티의 경우에도 클라이언트에 필요한 데이터만 선택적으로 전달할 수 있습니다.

#### DTO를 사용해야하는 이유와 이점

- **분리된 데이터 뷰**: 엔티티는 데이터베이스의 테이블 구조를 반영한 객체이며, 이러한 구조가 항상 API 응답으로 적합한 형태는 아닙니다. DTO를 사용하면 API 클라이언트에게 전달하고자 하는 데이터의
  형태와 구조를 자유롭게 설계할 수 있습니다.
- **보안**: 엔티티에는 API를 통해 외부에 노출되어서는 안 되는 민감한 정보가 포함될 수 있습니다. DTO를 사용하면 이러한 정보를 의도적으로 제외시키거나 숨길 수 있습니다.
- **성능 최적화**: DTO를 사용하면 필요한 데이터만 클라이언트에 전송하여 네트워크 사용량을 줄이고 성능을 최적화할 수 있습니다. 엔티티에는 많은 양의 데이터와 연관관계가 포함될 수 있는데, 이 모든 정보가
  클라이언트에게 필요하지 않을 수 있습니다.
- **유연성**: 엔티티 구조가 변경되더라도 DTO를 통해 API 응답 구조를 유지할 수 있습니다. 이는 API 호환성을 유지하면서 내부 구조를 자유롭게 변경할 수 있게 해 줍니다.

#### 구현 방식

- **DTO 정의**: 엔티티와는 별개로, API 응답에 적합한 형태의 DTO 클래스를 정의합니다. (기준은 당연히 Entity)
- **변환 로직 구현(@Service Converter)**: 엔티티 객체를 DTO 객체로 변환하는 로직을 구현합니다. 이 변환 과정은 수동으로 할 수도 있고, ModelMapper, MapStruct 같은
  라이브러리를 사용하여 자동화할 수도 있습니다.
- **서비스 또는 컨트롤러에서 DTO 사용**: 엔티티를 조회한 후, 변환 로직(Converter)을 통해 DTO로 변환하고, 이 DTO를 클라이언트에 응답으로 반환합니다.

#### 정리

- 엔티티를 직접 사용하는 것이 아니라 상응하는 DTO를 만들고, 필요에 따라 엔티티와 DTO 간 변환을 처리하는 것은 API 설계에서 매우 중요한 패턴입니다.
- 이 방식은 API의 유연성, 보안, 성능을 향상시키는 데 큰 도움이 됩니다.

### ✨결론

- 엔티티와 API 계층을 분리하는 것이 좋으며, 이를 위해 DTO를 사용하는 것이 일반적으로 권장된다.
- 물론 필요에 따라 @JsonIgnore, @ToString.Exclude 같은 어노테이션을 DTO나 엔티티 클래스에 적용할 수 있지만,
- 이는 단순히 특정한 문제를 해결하기 위한 임시적인 조치일 가능성이 높다고 한다.
- 따라서, DTO를 사용하여 데이터 전송 객체를 명확히 정의하고 엔티티를 DTO로 변환한 뒤 앞서 공부한 Api<T>클래스로 감싸서 내려보내는 방식을 사용하는 것이 바람직하다.

#### 참고 - @JsonIgnore, @ToString.Exclude와 DTO의 사용

- @JsonIgnore와 @ToString.Exclude 어노테이션은 엔티티의 특정 필드를 JSON 직렬화 과정이나 toString() 메소드 호출에서 제외시키는 데 사용됩니다.
- 이는 무한 루프나 데이터 노출 같은 문제를 방지할 수 있지만, 근본적으로는 뷰 레이어와 서비스 레이어 사이의 역할 분리 문제를 해결하지는 못합니다.
- 따라서, DTO를 사용하여 엔티티의 데이터를 필요한 형태로 가공하는 것이 더 권장되는 접근 방식입니다.
- DTO를 사용하면 엔티티의 구조 변경이 뷰에 미치는 영향을 최소화하고, 데이터 노출을 효과적으로 관리할 수 있습니다.

---

### 기본 CRUD 추상화해보기

- CRUDInterface (CRUD)
- Converter (toDto, toEntity)
- CRUDAbstractService implements CRUDInterface
    - dto -> entity (converter 필요 - 인터페이스로 구현)
    - entity -> save
    - save -> dto
- CRUDApiAbstractController implements CRUDInterface



