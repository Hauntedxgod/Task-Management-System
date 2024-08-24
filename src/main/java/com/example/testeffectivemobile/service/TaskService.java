package com.example.testeffectivemobile.service;


import com.example.testeffectivemobile.dto.TaskDto;
import com.example.testeffectivemobile.dto.TaskTakeDto;
import com.example.testeffectivemobile.exceptions.TaskErrorException;
import com.example.testeffectivemobile.exceptions.TaskNotCreatedException;
import com.example.testeffectivemobile.exceptions.TaskNotFoundExceptions;
import com.example.testeffectivemobile.exceptions.TaskNotUpdateException;
import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<TaskTakeDto> getAllTask(){
        ModelMapper modelMapper = new ModelMapper();
        List<TaskTakeDto> taskDtos = new ArrayList<>();
        List<Task> allTask = taskRepository.findAll();
        for (int i = 0; i < allTask.size() ; i++) {
            taskDtos.add(modelMapper.map(allTask.get(i) , TaskTakeDto.class));
        }
        return taskDtos;
    }

    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public Task getTaskById(Long id){
        Optional<Task> findById = taskRepository.findById(id);
        return findById.orElseThrow(TaskNotFoundExceptions::new);
    }

    public Task createTask(Task task , String name){
        task.setAuthor(name);
        return taskRepository.save(task);
    }

    public void takeTask(Long id , String name){
        Task task = getTaskById(id);
        task.setExecutor(name);
        taskRepository.save(task);
    }
    public void updateTask(Long id , TaskDto taskDto){
        Task task = getTaskById(id);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setTaskStatus(taskDto.getTaskStatus());
        task.setPriority(taskDto.getPriority());
        task.setExecutor(taskDto.getExecutor());
        saveTask(task);
    }



    public void taskNotCreated(BindingResult result) {
        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                builder.append(error.getField());
                builder.append("-");
                builder.append(error.getDefaultMessage());
            });
            throw new TaskNotCreatedException(builder.toString());

        }
    }

    public void taskNotUpdate(BindingResult result) {
        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                builder.append(error.getField());
                builder.append("-");
                builder.append(error.getDefaultMessage());
            });
            throw new TaskNotUpdateException(builder.toString());

        }
    }



}
