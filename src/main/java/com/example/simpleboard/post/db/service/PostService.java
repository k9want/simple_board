package com.example.simpleboard.post.db.service;

import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.db.PostRepository;
import com.example.simpleboard.post.db.model.PostRequest;
import com.example.simpleboard.post.db.model.PostViewRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostEntity save(PostRequest postRequest) {
        PostEntity entity = PostEntity.builder()
                .boardId(1L) // <- 임시 고정
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
                    return it;

                }).orElseThrow(
                        () -> new RuntimeException("해당 게시글이 존재하지 않습니다. : " + postViewRequest.getPostId())
                );
    }

    public List<PostEntity> all() {
        return postRepository.findAll();
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
