package com.example.testeffectivemobile.Controller;

import com.example.testeffectivemobile.TestEffectiveMobileApplication;

import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.models.enums.TaskPriority;
import com.example.testeffectivemobile.models.enums.TaskStatus;
import com.example.testeffectivemobile.repositories.TaskRepository;
import com.example.testeffectivemobile.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;



import static org.hamcrest.Matchers.hasSize;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest(classes = TestEffectiveMobileApplication.class)
@AutoConfigureMockMvc
public class TaskServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;



    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "ccc", password = "123")
    public void testCreateTask() throws Exception {

        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Description of the new task.");
        task.setTaskStatus(TaskStatus.DONE);
        task.setAuthor("ccc");
        task.setPriority(TaskPriority.HIGH);


        when(taskRepository.save(task)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.post("/task/newTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.description").value("Description of the new task."))
                .andExpect(jsonPath("$.taskStatus").value("DONE"))
                .andExpect(jsonPath("$.author").value("ccc"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }



    @Test
    @WithMockUser(username = "ccc", password = "123")
    public void testGetTaskById() throws Exception {
        Task task = new Task();
        task.setTitle("Existing Task");
        task.setDescription("This task already exists.");
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        task.setAuthor("ccc");
        task.setPriority(TaskPriority.MEDIUM);

        Long taskId = 1L;
        given(taskRepository.findById(taskId)).willReturn(java.util.Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/task/{id}", taskId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Existing Task"));
    }



    @Test
    @WithMockUser(username = "ccc", password = "123")
    public void testFindAll() throws Exception {

        Task task1 = new Task();
        task1.setTitle("Test task 1");
        task1.setDescription("This is a test task.");
        task1.setTaskStatus(TaskStatus.DONE);
        task1.setAuthor("ccc");
        task1.setPriority(TaskPriority.HIGH);

        Task task2 = new Task();
        task2.setTitle("Test task 2");
        task2.setDescription("Another test task.");
        task2.setTaskStatus(TaskStatus.IN_PROGRESS);
        task2.setAuthor("ccc");
        task2.setPriority(TaskPriority.LOW);

        List<Task> tasks = Arrays.asList(task1, task2);
        given(taskRepository.findAll()).willReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/task/allTask")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Test task 1"))
                .andExpect(jsonPath("$[1].title").value("Test task 2"));
    }

    @Test
    @WithMockUser(username = "ccc", password = "123")
    public void testDeleteTask() throws Exception {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/task/delete/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
