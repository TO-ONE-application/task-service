package ru.toone.TaskService.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TaskResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "АБВГД")
    private String title;
    @Schema(example = "дэээдээдэдафвав")
    private String description;
    @Schema(example = "IN_PROGRESS")
    private String status;
    @Schema(example = "2022-07-08T19:58:36.915Z")
    private ZonedDateTime createdAt;
    @Schema(example = "important")
    private String tag;
    @Schema(example = "21")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
