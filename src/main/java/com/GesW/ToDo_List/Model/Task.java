package com.GesW.ToDo_List.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "title", unique = true)
    private String title;
    @Column(nullable = true, name = "description")
    private String description;
    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "updatedAt")
    private LocalDateTime updatedAt;
    @Column(nullable = false, name = "isCompleted")
    private Boolean isCompleted;

    public Task(String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt,
                Boolean isCompleted, User user) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isCompleted = isCompleted;
        this.user = user;
    }

    public Task() {

    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    @JsonIgnore
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
