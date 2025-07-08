package ru.toone.TaskService.mappers;

import org.springframework.stereotype.Component;
import ru.toone.TaskService.dtos.TaskResponse;
import ru.toone.TaskService.models.Task;

import java.util.List;

@Component
public class TaskMapper {
    public Task dtoToModel(TaskResponse taskResponse){
        Task task = new Task();
        task.setId(taskResponse.getId());
        task.setDescription(taskResponse.getDescription());
        task.setTag(taskResponse.getTag());
        task.setStatus(taskResponse.getStatus());
        task.setTitle(taskResponse.getTitle());
        task.setCreatedAt(taskResponse.getCreatedAt());
        task.setUserId(taskResponse.getUserId());
        return task;
    }

    public TaskResponse modelToDto(Task task){
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setTag(task.getTag());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setCreatedAt(task.getCreatedAt());
        taskResponse.setUserId(task.getUserId());
        return taskResponse;
    }

    public List<Task> listDtoToListModel(List<TaskResponse> taskResponses){
        return taskResponses.stream().map(this::dtoToModel).toList();
    }
    public List<TaskResponse> listModelToListDto(List<Task> tasks){
        return tasks.stream().map(this::modelToDto).toList();
    }
}
