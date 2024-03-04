package com.example.simpleboard.reply.service;

import com.example.simpleboard.crud.Converter;
import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.db.PostRepository;
import com.example.simpleboard.reply.db.ReplyEntity;
import com.example.simpleboard.reply.model.ReplyDto;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyConverter implements Converter<ReplyDto, ReplyEntity> {

    private final PostRepository postRepository;

    @Override
    public ReplyDto toDto(ReplyEntity replyEntity) {

        return ReplyDto.builder()
                .id(replyEntity.getId())
                .postId(replyEntity.getPost().getId())
                .status(replyEntity.getStatus())
                .content(replyEntity.getContent())
                .title(replyEntity.getTitle())
                .userName(replyEntity.getUserName())
                .password(replyEntity.getPassword())
                .repliedAt(replyEntity.getRepliedAt())
                .build();

    }

    @Override
    public ReplyEntity toEntity(ReplyDto replyDto) {

        Optional<PostEntity> postEntity = postRepository.findById(replyDto.getPostId());

        return ReplyEntity.builder()
                .id(replyDto.getId()) // <-- null save , not null update
                .post(postEntity.orElse(null))
                .status((replyDto.getStatus() != null) ? replyDto.getStatus() : "REGISTERED")
                .content(replyDto.getContent())
                .title(replyDto.getTitle())
                .userName(replyDto.getUserName())
                .password(replyDto.getPassword())
                .repliedAt((replyDto.getRepliedAt() != null) ? replyDto.getRepliedAt() : LocalDateTime.now())
                .build();
    }
}
