package com.example.testeffectivemobile.dto;

import com.example.testeffectivemobile.models.enums.TaskPriority;
import com.example.testeffectivemobile.models.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private String title;

    private String description;

    private TaskStatus taskStatus;

    private String executor;

    private TaskPriority priority;
}
