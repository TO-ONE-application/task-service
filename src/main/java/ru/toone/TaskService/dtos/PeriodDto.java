package ru.toone.TaskService.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;

public class PeriodDto {
    @Schema(example = "1")
    private Long userId;
    @Schema(example = "2023-07-08T19:58:36.915Z")
    private ZonedDateTime start;
    @Schema(example = "2024-07-08T19:58:36.915Z")
    private ZonedDateTime end;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
