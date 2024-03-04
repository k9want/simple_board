package com.example.simpleboard.reply.controller;

import com.example.simpleboard.crud.CRUDApiAbstractController;
import com.example.simpleboard.reply.db.ReplyEntity;
import com.example.simpleboard.reply.model.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyApiController extends CRUDApiAbstractController<ReplyDto, ReplyEntity> {

/*    private final ReplyService replyService;

    @PostMapping("")
    public ReplyEntity create(
            @Valid
            @RequestBody
            ReplyRequest replyRequest
    ) {
        return replyService.create(replyRequest);
    }*/
}
