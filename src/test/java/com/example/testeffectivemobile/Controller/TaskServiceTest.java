package com.example.testeffectivemobile.Controller;

import com.example.testeffectivemobile.TestEffectiveMobileApplication;
import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.models.enums.TaskPriority;
import com.example.testeffectivemobile.models.enums.TaskStatus;
import com.example.testeffectivemobile.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestEffectiveMobileApplication.class)
@AutoConfigureMockMvc
public class TaskServiceTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private  TaskRepository taskRepository;




//    @BeforeEach
//    public void setUp() {
//        taskRepository.deleteAll(); // Очистка базы данных перед каждым тестом
//    }

//    @Test
//    @WithMockUser(username = "ccc", password = "123")
//    public void testCreateTask() throws Exception {
//        String taskJson = "{ \"title\": \"Test Task\", \"description\": \"This is a test task.\", \"status\": \"progress\", \"priority\": \"medium\",\"author_name\": \"ccc\", \"executor_name\": \"ccc\"}";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/task/newTask")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(taskJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title").value("Test Task"))
//                .andDo(print()); // Вывод тела ответа для отладки
//    }

    @Test
    @WithMockUser(username = "ccc", password = "123")
    public void testGetTaskById() throws Exception {
        Task task = new Task();
        task.setTitle("Existing Task");
        task.setDescription("This task already exists.");
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        task.setAuthor("ccc");
        task.setPriority(TaskPriority.MEDIUM);

        Task savedTask = taskRepository.save(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/task/{id}", savedTask.getId())) // Используем {id} правильно
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Existing Task"));
    }
//    @Test
//    @WithMockUser(username = "ccc", password = "123")
//    public void testDeleteTask() throws Exception {
//        Task task = new Task();
//        task.setTitle("Task to be deleted");
//        task.setTaskStatus(TaskStatus.PENDING);
//        task.setDescription("This task will be deleted");
//        task.setAuthor("ccc");
//        task.setExecutor("ccc");
//        task.setPriority(TaskPriority.HIGH);
//        task = taskRepository.save(task);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/task/delete/{id}", task.getId()))
//                .andExpect(status().isNoContent());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/task/{id}", task.getId()))
//                .andExpect(status().isNotFound());
//    }

}
