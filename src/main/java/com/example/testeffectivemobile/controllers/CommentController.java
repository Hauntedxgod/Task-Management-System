package com.example.testeffectivemobile.controllers;

import com.example.testeffectivemobile.dto.CommentDto;
import com.example.testeffectivemobile.models.Comment;
import com.example.testeffectivemobile.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/newComment")
    public ResponseEntity<Comment> createComment(@RequestParam Long taskId , @RequestParam Long authorId
            , @RequestParam String content ){


        Comment comment = commentService.createComment(taskId , authorId , content);

        return new ResponseEntity<>(comment , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(){
        return new ResponseEntity<>(commentService.getAllComments() , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }


}
