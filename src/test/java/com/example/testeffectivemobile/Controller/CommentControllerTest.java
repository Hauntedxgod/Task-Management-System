package com.example.testeffectivemobile.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.testeffectivemobile.TestEffectiveMobileApplication;
import com.example.testeffectivemobile.controllers.CommentController;
import com.example.testeffectivemobile.models.Comment;
import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@SpringBootTest(classes = TestEffectiveMobileApplication.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private Comment comment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        comment = new Comment();
        comment.setId(1L);
        comment.setContent("This is a test comment");
    }

    @Test
    @WithMockUser(username = "ccc", password = "123")
    public void testCreateComment() throws Exception {
        Long taskId = 1L;
        Long authorId = 1L;
        String content = "This is a test comment";

        when(commentService.createComment(taskId, authorId, content)).thenReturn(comment);

        mockMvc.perform(post("/comment/newComment")
                        .param("taskId", String.valueOf(taskId))
                        .param("authorId", String.valueOf(authorId))
                        .param("content", content)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Устанавливаем тип контента
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content)); // Проверка содержимого ответа

        verify(commentService, times(1)).createComment(taskId, authorId, content);
    }
}