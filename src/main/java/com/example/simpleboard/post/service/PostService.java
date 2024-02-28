package com.example.simpleboard.post.service;

import com.example.simpleboard.board.db.BoardEntity;
import com.example.simpleboard.board.db.BoardRepository;
import com.example.simpleboard.common.Api;
import com.example.simpleboard.common.Pagination;
import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.db.PostRepository;
import com.example.simpleboard.post.model.PostRequest;
import com.example.simpleboard.post.model.PostViewRequest;
import com.example.simpleboard.reply.service.ReplyService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    private final ReplyService replyService;

    public PostEntity save(
            PostRequest postRequest
    ) {
        BoardEntity boardEntity = boardRepository.findById(postRequest.getBoardId()).get(); // <- 임시 고정

        PostEntity entity = PostEntity.builder()
                // .boardId(1L) // <- 임시 고정
                .board(boardEntity)
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .status("REGISTERED")
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build();

        return postRepository.save(entity);
    }

    /*
     * 1. 게시글이 있는가?
     * 2. 비밀번호가 맞는가?
     * */
    public PostEntity view(PostViewRequest postViewRequest) {
        return postRepository.findFirstByIdAndStatusOrderByIdDesc(
                        postViewRequest.getPostId(), "REGISTERED") //Optional<>로 감싸져있기에 map으로 내용물을 꺼낸다.
                .map(it -> {
                    // entity 존재할 때 (게시글 존재)
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        String format = "패스워드가 맞지 않습니다. %s vs %s";
                        throw new RuntimeException(
                                String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }

//                    // 게시글의 답변도 가져오기
//                    List<ReplyEntity> replyList = replyService.findAllByPostId(it.getId());
//                    it.setReplyList(replyList);
                    /*
                     * 필요없는 이유 :
                     * @OneToMany(mappedBy)를 통해 자동으로 replyList 가져오도록 되어 필요가 없어짐.
                     * */

                    return it;

                }).orElseThrow(
                        () -> new RuntimeException("해당 게시글이 존재하지 않습니다. : " + postViewRequest.getPostId())
                );
    }

    public Api<List<PostEntity>> all(Pageable pageable) {
        Page<PostEntity> list = postRepository.findAll(pageable);

        Pagination pagination = Pagination.builder()
                .page(list.getNumber())
                .size(list.getSize())
                .currentElements(list.getNumberOfElements())
                .totalElement(list.getTotalElements())
                .totalPage(list.getTotalPages())
                .build();

        Api<List<PostEntity>> response = Api.<List<PostEntity>>builder()
                .body(list.toList())
                .pagination(pagination)
                .build();

        return response;
    }

    public void delete(PostViewRequest postViewRequest) {
        postRepository.findById(postViewRequest.getPostId()) //Optional<>로 감싸져있기에 map으로 내용물을 꺼낸다.
                .map(it -> {
                    // entity 존재할 때 (게시글 존재)
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        String format = "패스워드가 맞지 않습니다. %s vs %s";
                        throw new RuntimeException(
                                String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }

                    // 삭제 로직
                    it.setStatus("UNREGISTERED");
                    postRepository.save(it);
                    return it;

                }).orElseThrow(
                        () -> new RuntimeException("해당 게시글이 존재하지 않습니다. : " + postViewRequest.getPostId())
                );

    }
}
