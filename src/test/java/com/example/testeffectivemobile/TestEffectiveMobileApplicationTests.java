package com.example.testeffectivemobile;

import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.repositories.TaskRepository;
import com.example.testeffectivemobile.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class TestEffectiveMobileApplicationTests {



    class TaskServiceTest{
        private TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
//        private TaskService taskService = new TaskService(taskRepository);


        @Test
        public void testTaskCreate(){
            Task task = new Task();
            task.setTitle("New Project");

            taskRepository.save(task);

//            Task createTask = taskService.createTask(task , task.getAuthor());


//            assertEquals("New project" , createTask.getTitle());
            verify(taskRepository , times(1)).save(task);
        }
    }

}
