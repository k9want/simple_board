package com.example.simpleboard.post.db.controller;

import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.db.model.PostRequest;
import com.example.simpleboard.post.db.model.PostViewRequest;
import com.example.simpleboard.post.db.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping("")
    public PostEntity create(
            @Valid
            @RequestBody
            PostRequest postRequest
    ) {
        return postService.save(postRequest);
    }

    /*
     * 비공개 게시글을 보기 위해 비밀번호를 입력해야한다.
     * */
    @PostMapping("/view")
    public PostEntity view(
            @Valid
            @RequestBody
            PostViewRequest postViewRequest
    ) {
        return postService.view(postViewRequest);
    }

    @GetMapping("/all")
    public List<PostEntity> all() {
        return postService.all();
    }


    /*
     * deleteMapping일 경우에는 비밀번호를 전달해주지 못한다.
     * 게시글을 삭제하기 위해선 비밀번호가 필요한 상황이다.
     * 따라서 PostMapping을 쓴 것 (비밀번호를 전달하기 위해)
     * */
    @PostMapping("/delete")
    public void delete(
            @Valid
            @RequestBody
            PostViewRequest postViewRequest
    ) {
        postService.delete(postViewRequest);
    }


}
