package com.example.testeffectivemobile.repositories;

import com.example.testeffectivemobile.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {

    List<Comment> findByTaskId (Long taskId);

}
