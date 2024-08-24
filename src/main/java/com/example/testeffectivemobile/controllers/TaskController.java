package com.example.testeffectivemobile.controllers;


import com.example.testeffectivemobile.dto.TaskDto;
import com.example.testeffectivemobile.dto.TaskTakeDto;
import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.repositories.TaskRepository;
import com.example.testeffectivemobile.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/allTask")
    public ResponseEntity<List<TaskTakeDto>> getAllTask(){
        return new ResponseEntity<>(taskService.getAllTask() , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskTakeDto> getTaskId(@PathVariable("id") Long id){
        return new ResponseEntity<>( modelMapper.map(taskService.getTaskById(id) , TaskTakeDto.class) , HttpStatus.OK);
    }

    @PostMapping("/take/{id}")
    public ResponseEntity<HttpStatus> takeTask(@AuthenticationPrincipal UserDetails userDetails
            , @PathVariable("id") Long id ){
        taskService.takeTask(id , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/newTask")
    public ResponseEntity<Task> newTask(@RequestBody TaskDto taskDto , @AuthenticationPrincipal UserDetails userDetails,
                                        BindingResult bindingResult){

        taskService.taskNotCreated(bindingResult);

        Task task = modelMapper.map(taskDto, Task.class);

        return new ResponseEntity<>(taskService.createTask(task , userDetails.getUsername()) , HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskTakeDto> updateTask(@PathVariable("id") Long id, @RequestBody TaskDto taskDto,
                                                  BindingResult bindingResult){

        taskService.taskNotUpdate(bindingResult);

        taskService.updateTask(id , taskDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id){
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
