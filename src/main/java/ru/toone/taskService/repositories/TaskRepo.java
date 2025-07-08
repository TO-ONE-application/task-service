package ru.toone.taskService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.toone.taskService.models.Task;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    @Query("SELECT t FROM Task t WHERE t.tag IN :tags")
    List<Task> findByTags(@Param("tags") List<String> tags);

    List<Task> findByStatusIn(List<String> status);

    List<Task> findByCreatedAtAfter(ZonedDateTime date);

    List<Task> findByCreatedAtBefore(ZonedDateTime date);

    List<Task> findByCreatedAtBetween(ZonedDateTime start, ZonedDateTime end);
}
