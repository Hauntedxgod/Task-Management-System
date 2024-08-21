package com.example.testeffectivemobile.service;

import com.example.testeffectivemobile.exceptions.TaskNotFoundExceptions;
import com.example.testeffectivemobile.exceptions.UserNotFoundException;
import com.example.testeffectivemobile.models.Comment;
import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.models.User;
import com.example.testeffectivemobile.repositories.CommentRepository;
import com.example.testeffectivemobile.repositories.TaskRepository;
import com.example.testeffectivemobile.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public void findById(Long id){
        commentRepository.findById(id);
    }

    public Comment createComment(Long taskId , Long authorId , String content ){
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundExceptions::new);
        User user = userRepository.findById(authorId).orElseThrow(UserNotFoundException::new);

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setAuthor(user.getEmail());
        comment.setContent(content);


        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTaskId(Long tuskId){
        return commentRepository.findByTaskId(tuskId);
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

}
