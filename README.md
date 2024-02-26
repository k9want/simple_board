## 간단한 게시판 프로젝트

### Entity - board, post, reply

## API 명세서

#### [POST] /api/board - 게시판 생성

#### [POST] /api/post - 게시글 작성

#### [GET] /api/post/all - 게시판의 게시글 전체 조회

#### [[POST] /api/post/view - 게시글 상세보기](https://github.com/k9want/simple_board/blob/39c6e2d68f8cc37aa95809b0a9c2a69889d8ad39/src/main/java/com/example/simpleboard/post/db/service/PostService.java)

- (1) 게시글이 있는가? (2) 비밀번호가 맞는가?
- GET이 아닌 POST인 이유: 비공개 게시글을 관리하는 게시판이기에 비밀번호를 알아야만 조회가 가능하다.
- GET으로 조회해서 가져오기엔 password를 통한 로직을 넣어야 한다. + POST로 body에 넣어서 전달할 필요가 있다.
- status가 "REGISTERED"인 경우만 열람이 가능하다.

#### [POST] /api/post/delete

- status를 "REGISTERED" -> "UNREGISTERED"로 수정
- POST 혹은 PATCH

