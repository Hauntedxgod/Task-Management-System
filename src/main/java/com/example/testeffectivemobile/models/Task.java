package com.example.testeffectivemobile.models;


import com.example.testeffectivemobile.models.enums.TaskPriority;
import com.example.testeffectivemobile.models.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
@Entity
public class Task {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @Column(name = "executor_name")
    private String executor;

    @Column(name = "author_name")
    private String author;

    @OneToMany(mappedBy = "task" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments;




}
