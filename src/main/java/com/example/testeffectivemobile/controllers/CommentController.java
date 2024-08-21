package com.example.testeffectivemobile.controllers;

import com.example.testeffectivemobile.models.Comment;
import com.example.testeffectivemobile.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {


    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestParam Long taskId , @RequestParam Long authorId
            , @RequestParam String content){
        Comment comment = commentService.createComment(taskId , authorId , content);

        return ResponseEntity.ok(comment);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<List<Comment>> getCommentsByTaskId(@PathVariable("id") Long taskId){

        List<Comment> comments = commentService.getCommentsByTaskId(taskId);

        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }


}
