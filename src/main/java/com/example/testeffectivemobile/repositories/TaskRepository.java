package com.example.testeffectivemobile.repositories;

import com.example.testeffectivemobile.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task , Long> {

}
