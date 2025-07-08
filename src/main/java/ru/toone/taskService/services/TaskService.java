package ru.toone.taskService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.toone.taskService.models.Task;
import ru.toone.taskService.repositories.TaskRepo;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    // Сохраняет задачу если задачи с таким айдишником еще нету
    @Transactional
    public void save(Task task){
        taskRepo.save(task);
    }

    // Удаляет задачу по айдишнику если задача с таким айдишником имеется
    @Transactional
    public void deleteById(Long id){
        taskRepo.deleteById(id);
    }

    // Удаляет задачу по объекту если задача с таким айдишником имеется
    @Transactional
    public void delete(Task task){
        if(taskRepo.findById(task.getId()).isPresent()){
            taskRepo.delete(task);
        }
    }

    // Обновляет задачу в БД присваивая айдишник старой задачи обновленной задаче проверяя есть ли ваще такая задача
    @Transactional
    public void update(Long id, Task updatedTask){
        if(taskRepo.findById(id).isPresent()){
            updatedTask.setId(id);
            taskRepo.save(updatedTask);
        }
    }

    // Выводит список задач если таковые имеются
    public List<Task> findAll(){
        if(taskRepo.findAll().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"задач не найдено");
        } return taskRepo.findAll();
    }

    // Выводит задачу по айдишнику если такая имеется
    public Task findById(Long id){
        return taskRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException
                                ("Не существует задачи с таким идентификатором: "+id));
    }

    // Выводит все задачи определенного пользователя если они имеются
    public List<Task> findByUserId(Long userId){
        return taskRepo.findByUserId(userId);
    }

    // находит задачи по тегу
    public List<Task> findByTags(List<String> tag){
        List<Task> tasks = taskRepo.findByTags(tag);
        if(tasks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Задачи с таким тегом не найдены");
        } return tasks;
    }

    // находит задачи по статусу
    public List<Task> findByStatus(List<String> status){
        List<Task> tasks = taskRepo.findByStatusIn(status);
        if(tasks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Задач с таким статусом не найдено");
        } return tasks;
    }

    // находит задачи после определенной даты
    public List<Task> findByCreatedAtAfter(ZonedDateTime dateTime){
        List<Task> tasks = taskRepo.findByCreatedAtAfter(dateTime);
        if(tasks.isEmpty()){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Задач после этой даты не найдено: "+dateTime.toString());
        } return tasks;
    }

    // находит задачи перед определенной датой
    public List<Task> findByCreatedAtBefore(ZonedDateTime dateTime){
        List<Task> tasks = taskRepo.findByCreatedAtBefore(dateTime);
        if(tasks.isEmpty()){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND,"Задач до этой даты не найдено: "+dateTime.toString());
        } return tasks;
    }

    // находит задачи между датами
    public List<Task> findByCreatedAtBetween(ZonedDateTime start, ZonedDateTime end){
        List<Task> tasks = taskRepo.findByCreatedAtBetween(start, end);
        if(tasks.isEmpty()){
            throw new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Между "+start.toString()+" и "+end.toString()+" не найдено задач");
        } return tasks;
    }


}
