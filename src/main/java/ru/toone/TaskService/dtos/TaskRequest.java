package ru.toone.TaskService.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    @Schema(example = "Завершить проект")
    private String title;

    @Size(max = 500, message = "Description is too long")
    @Schema(example = "Необходимо завершить разработку сервиса задач до конца недели")
    private String description;

    @NotNull(message = "Status is required")
    @Schema(example = "TODO")
    private String status;

    @NotNull(message = "Tag is required")
    @Schema(example = "important")
    private String tag;


    public @NotBlank(message = "Title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") String title) {
        this.title = title;
    }

    public @NotNull(message = "Status is required") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required") String status) {
        this.status = status;
    }

    public @Size(max = 500, message = "Description is too long") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description is too long") String description) {
        this.description = description;
    }

    public @NotNull(message = "Tag is required") String getTag() {
        return tag;
    }

    public void setTag(@NotNull(message = "Tag is required") String tag) {
        this.tag = tag;
    }
}
