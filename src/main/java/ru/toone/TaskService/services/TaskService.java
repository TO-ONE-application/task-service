package ru.toone.TaskService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.toone.TaskService.dtos.AnalyticsDto;
import ru.toone.TaskService.dtos.PeriodDto;
import ru.toone.TaskService.dtos.TaskRequest;
import ru.toone.TaskService.dtos.TaskResponse;
import ru.toone.TaskService.mappers.TaskMapper;
import ru.toone.TaskService.models.TaskStatus;
import ru.toone.TaskService.repositories.TaskRepo;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepo taskRepo, TaskMapper taskMapper) {
        this.taskRepo = taskRepo;
        this.taskMapper = taskMapper;
    }

    // Сохраняет задачу если задачи с таким айдишником еще нету
    @Transactional
    public void save(TaskResponse task){
        taskRepo.save(taskMapper.dtoToModel(task));
    }

    // Удаляет задачу по её айдишнику
    @Transactional
    public void deleteById(Long id){
        taskRepo.deleteById(id);
    }

    // Удаляет задачу по айдишнику пользователя
    @Transactional
    public void deleteByUserId(Long userId){
        taskRepo.deleteByUserId(userId);
    }


    // Обновляет задачу в БД присваивая айдишник старой задачи обновленной задаче проверяя есть ли ваще такая задача
    @Transactional
    public void update(Long id, TaskResponse updatedTask){
        if(taskRepo.findById(id).isPresent()){
            updatedTask.setId(id);
            taskRepo.save(taskMapper.dtoToModel(updatedTask));
        }
    }

    // Выводит список задач если таковые имеются
    public List<TaskResponse> findAll(){
        if(taskRepo.findAll().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"задач не найдено");
        } return taskMapper.listModelToListDto(taskRepo.findAll());
    }

    // Выводит задачу по айдишнику если такая имеется
    public TaskResponse findById(Long id){
        if(taskRepo.findById(id).isPresent()){
            return taskMapper.modelToDto(taskRepo.findById(id).get());
        }else throw new NoSuchElementException("Нет задачи с айди "+id);
    }

    // Выводит все задачи определенного пользователя если они имеются
    public List<TaskResponse> findByUserId(Long userId){
        return taskMapper.listModelToListDto(taskRepo.findByUserId(userId));
    }

    // находит задачи по тегу
    public List<TaskResponse> findByTags(List<String> tag){
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByTags(tag));
        if(tasks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Задачи с таким тегом не найдены");
        } return tasks;
    }

    // находит задачи по статусу
    public List<TaskResponse> findByStatus(List<String> status){
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByStatusIn(status));
        if(tasks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Задач с таким статусом не найдено");
        } return tasks;
    }

    // находит задачи после определенной даты
    public List<TaskResponse> findByCreatedAtAfter(ZonedDateTime dateTime){
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByCreatedAtAfter(dateTime));
        if(tasks.isEmpty()){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Задач после этой даты не найдено: "+dateTime.toString());
        } return tasks;
    }

    // находит задачи перед определенной датой
    public List<TaskResponse> findByCreatedAtBefore(ZonedDateTime dateTime){
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByCreatedAtBefore(dateTime));
        if(tasks.isEmpty()){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Задач до этой даты не найдено: "+dateTime.toString());
        } return tasks;
    }

    // находит задачи между датами
    public List<TaskResponse> findByCreatedAtBetween(ZonedDateTime start, ZonedDateTime end){
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByCreatedAtBetween(start, end));
        if(tasks.isEmpty()){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Между "+start.toString()+" и "+end.toString()+" не найдено задач");
        } return tasks;
    }

    // находит конкретную задачу у пользователя
    public TaskResponse getExactUserTask(Long userId, Long taskId){
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByUserId(userId));
        return tasks.stream().filter(task -> task.getId()
                .equals(taskId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("У этого юзера нету задачи с айди "+taskId));
    }

    // находит 5 задач у пользователя в зависимости от статуса
    public List<TaskResponse> getFiveTasks(Long userId, String status){
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByUserId(userId));
        List<TaskResponse> importantTasks = tasks.stream()
                .filter(task -> "important"
                .equals(task.getTag()))
                .toList();
        if(TaskStatus.IN_PROGRESS.name().equals(status)){
            List<TaskResponse> inProgressTasks = importantTasks.stream()
                                .filter(task -> TaskStatus.IN_PROGRESS.name()
                                .equals(task.getStatus()))
                                .toList();
            return inProgressTasks;
        }if(status.equals(TaskStatus.TODO.name())){
            List<TaskResponse> toDoTasks = importantTasks.stream()
                                .filter(task -> TaskStatus.TODO.name()
                                .equals(task.getStatus())).toList();
            return toDoTasks;
        }if(status.equals(TaskStatus.DONE.name())){
            List<TaskResponse> doneTasks = importantTasks.stream()
                                .filter(task -> TaskStatus.DONE.name()
                                .equals(task.getStatus())).toList();
            return doneTasks;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Не найдено статуса такого");
        }
    }


    public TaskResponse reqToResp(TaskRequest request) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setDescription(request.getDescription());
        taskResponse.setTitle(request.getTitle());
        taskResponse.setStatus(request.getStatus());
        taskResponse.setTag(request.getTag());
        return taskResponse;
    }

    public AnalyticsDto getTaskAnalytics(PeriodDto periodDto) {
        List<TaskResponse> tasks = taskMapper.listModelToListDto(taskRepo.findByUserId(periodDto.getUserId()));
        Long todoTasks = tasks.stream().filter(task -> TaskStatus.TODO.name().equals(task.getStatus())).count();
        Long inProgressTasks = tasks.stream().filter(task -> TaskStatus.IN_PROGRESS.name().equals(task.getStatus())).count();
        Long doneTasks = tasks.stream().filter(task -> TaskStatus.DONE.name().equals(task.getStatus())).count();
        Long importantTasks = tasks.stream().filter(task -> "important".equals(task.getTag())).count();

        AnalyticsDto analyticsDto = new AnalyticsDto(periodDto.getStart(), periodDto.getEnd(), todoTasks, inProgressTasks, doneTasks, importantTasks);

        String analytics = "In period between "+periodDto.getStart().toString()+" and "+periodDto.getEnd().toString()
                +"\n There was"+todoTasks+ " todo tasks,"
                +"\n"+inProgressTasks+" in progress tasks,"
                +"\n"+importantTasks+" important tasks"
                +"\n and"+doneTasks+ " done tasks";
        return analyticsDto;
    }
}
