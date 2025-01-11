package com.GesW.ToDo_List.Repository;

import com.GesW.ToDo_List.Model.Task;
import com.GesW.ToDo_List.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByUserId(Long userid);
    public List<Task> findAllByUserUsername(String username);
    boolean existsByTitleAndUserUsername(String title, String username);


}
