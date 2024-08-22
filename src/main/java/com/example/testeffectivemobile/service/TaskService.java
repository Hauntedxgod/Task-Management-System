package com.example.testeffectivemobile.service;


import com.example.testeffectivemobile.dto.TaskTakeDto;
import com.example.testeffectivemobile.exceptions.TaskNotFoundExceptions;
import com.example.testeffectivemobile.models.Task;
import com.example.testeffectivemobile.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {


    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;
    @Autowired
    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }




    public List<TaskTakeDto> getAllTask(){
        List<TaskTakeDto> taskDtos = new ArrayList<>();
        List<Task> allTask = taskRepository.findAll();
        for (int i = 0; i < allTask.size() ; i++) {
            taskDtos.add(modelMapper.map(allTask.get(i) , TaskTakeDto.class));
        }
        return taskDtos;
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
    public void updateTask(Long id , TaskTakeDto taskTakeDto){
        taskRepository.findById(id);
        taskTakeDto.setTitle(taskTakeDto.getTitle());
        taskTakeDto.setDescription(taskTakeDto.getDescription());
        taskTakeDto.setTaskStatus(taskTakeDto.getTaskStatus());
        taskTakeDto.setPriority(taskTakeDto.getPriority());


    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

}
