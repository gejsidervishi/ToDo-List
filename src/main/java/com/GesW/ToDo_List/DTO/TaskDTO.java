package com.GesW.ToDo_List.DTO;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDTO implements Serializable {

    public String title;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public Boolean isCompleted = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
