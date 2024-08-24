package com.example.testeffectivemobile.dto;

import com.example.testeffectivemobile.models.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {


    private String content;

    private String author;

    private TaskTakeDto task;
}
