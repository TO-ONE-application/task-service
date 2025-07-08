package ru.toone.TaskService.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;

public class AnalyticsDto {
    @Schema(example = "2021-07-08T19:58:36.915Z")
    private ZonedDateTime start;
    @Schema(example = "2023-07-08T19:58:36.915Z")
    private ZonedDateTime end;
    @Schema(example = "21")
    private Long toDoTasksCount;
    @Schema(example = "12")
    private Long inProgressTasksCount;
    @Schema(example = "3")
    private Long doneTasksCount;
    @Schema(example = "7")
    private Long importantTasksCount;


    public AnalyticsDto(ZonedDateTime start, ZonedDateTime end, Long toDoTasksCount, Long inProgressTasksCount, Long doneTasksCount, Long importantTasksCount) {
        this.start = start;
        this.end = end;
        this.toDoTasksCount = toDoTasksCount;
        this.inProgressTasksCount = inProgressTasksCount;
        this.doneTasksCount = doneTasksCount;
        this.importantTasksCount = importantTasksCount;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Long getToDoTasksCount() {
        return toDoTasksCount;
    }

    public void setToDoTasksCount(Long toDoTasksCount) {
        this.toDoTasksCount = toDoTasksCount;
    }

    public Long getInProgressTasksCount() {
        return inProgressTasksCount;
    }

    public void setInProgressTasksCount(Long inProgressTasksCount) {
        this.inProgressTasksCount = inProgressTasksCount;
    }

    public Long getDoneTasksCount() {
        return doneTasksCount;
    }

    public void setDoneTasksCount(Long doneTasksCount) {
        this.doneTasksCount = doneTasksCount;
    }

    public Long getImportantTasksCount() {
        return importantTasksCount;
    }

    public void setImportantTasksCount(Long importantTasksCount) {
        this.importantTasksCount = importantTasksCount;
    }
}
