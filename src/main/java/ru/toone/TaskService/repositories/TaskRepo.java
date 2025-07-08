package ru.toone.TaskService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.toone.TaskService.dtos.TaskResponse;
import ru.toone.TaskService.models.Task;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    @Query("SELECT t FROM Task t WHERE t.tag IN :tags")
    List<Task> findByTags(@Param("tags") List<String> tags);

    List<Task> findByStatusIn(List<String> status);

    List<Task> findByCreatedAtAfter(ZonedDateTime date);

    List<Task> findByCreatedAtBefore(ZonedDateTime date);

    List<Task> findByCreatedAtBetween(ZonedDateTime start, ZonedDateTime end);

    void deleteByUserId(Long userId);
}
