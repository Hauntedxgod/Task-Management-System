package com.example.testeffectivemobile.dto;

import com.example.testeffectivemobile.models.enums.TaskPriority;
import com.example.testeffectivemobile.models.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTakeDto {

    private Long id;

    private String title;

    private String description;

    private TaskStatus taskStatus;

    private String executor;

    private String author;

    private TaskPriority priority;
}
