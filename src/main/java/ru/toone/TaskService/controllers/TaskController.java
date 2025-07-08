package ru.toone.TaskService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.toone.TaskService.dtos.AnalyticsDto;
import ru.toone.TaskService.dtos.PeriodDto;
import ru.toone.TaskService.dtos.TaskRequest;
import ru.toone.TaskService.dtos.TaskResponse;
import ru.toone.TaskService.services.TaskService;

import java.time.ZonedDateTime;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Получение всех задач у пользователя", description = "Выводит все задачи у пользователя")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> currentTasks(@Parameter(description = "ID пользователя", example = "1")
                                                           @RequestParam(value = "user_id") Long userId){
        List<TaskResponse> tasks = taskService.findByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Получение конкретной задачи у пользователя", description = "Выводит конкретную задачу у пользователя")
    @GetMapping("/task")
    public ResponseEntity<TaskResponse> currentTask(@Parameter(description = "ID пользователя", example = "1")
                                                    @RequestParam(value = "user_id") Long userId,
                                                    @Parameter(description = "Айди задачи", example = "2")
                                                    @RequestParam(value = "task_id") Long taskId){
        TaskResponse task = taskService.getExactUserTask(userId,taskId);
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Получение 5 задач с тегом important", description = "Выводит 5 задач с тегом important разных статусов")
    @GetMapping("/task/{user_id}")
    public ResponseEntity<List<TaskResponse>> fiveImportantTasks(@Parameter(description = "ID пользователя", example = "1")
                                                                 @PathVariable(value = "user_id") Long userId,
                                                                 @Parameter(description = "Статус задачи", example = "TODO")
                                                                 @RequestParam(value = "status") String status){
        List<TaskResponse> sortedTasks = taskService.getFiveTasks(userId,status);
        return ResponseEntity.ok(sortedTasks);
    }

    @Operation(summary = "Получение аналитики по задачам по определенному периоду", description = "Выводит аналитику по задачам за определённый промежуток времени")
    @GetMapping("/task/analytics")
    public ResponseEntity<AnalyticsDto> taskAnalyze(@Parameter(description = "Period DTO")
                                                    @RequestBody PeriodDto period){
        AnalyticsDto analyticsDto = taskService.getTaskAnalytics(period);
        return ResponseEntity.ok(analyticsDto);
    }



    @Operation(summary = "Создание задачи", description = "Создаёт и сохраняет задачу в БД")
    @PostMapping("/task")
    public ResponseEntity<TaskResponse> createTask(@Parameter(description = "Реквест задачи в виде JSON")
                                                   @Valid @RequestBody TaskRequest request){
        TaskResponse taskResponse = taskService.reqToResp(request);
        taskService.save(taskResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @Operation(summary = "Обновление задачи", description = "Обновляет задачу в БД")
    @PatchMapping("/task")
    public ResponseEntity<TaskResponse> updateTask(@Parameter(description = "ID пользователя", example = "1")
                                                   @Valid @RequestParam(value = "user_id") Long userId,
                                                   @Parameter(description = "Реквест задачи в виде JSON")
                                                   @Valid @RequestBody TaskRequest request){
        taskService.update(userId,taskService.reqToResp(request));
        TaskResponse taskResponse = taskService.findByUserId(userId).get(0);
        return ResponseEntity.ok(taskResponse);
    }

    @Operation(summary = "Удаление задачи", description = "Удаляет задачу из БД")
    @DeleteMapping("/task")
    public ResponseEntity<TaskResponse> deleteTask(@Parameter(description = "ID пользователя", example = "1")
                                                   @RequestParam(value = "user_id") Long userId){
        taskService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }


}
