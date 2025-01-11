package com.GesW.ToDo_List.Controller;
import com.GesW.ToDo_List.DTO.TaskDTO;
import com.GesW.ToDo_List.Model.Task;
import com.GesW.ToDo_List.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/api")
public class TaskController {
    private static final Logger logger = Logger.getLogger(TaskController.class);
    @Autowired
    private TaskService taskService;

    // add task
    @PostMapping("/add")
    public Task addTask(@RequestBody TaskDTO taskDTO){
    /* or using Principal object to get authenticated user
        String username = principal.getName(); */

        logger.info("TaskController: Adding task with title: " + taskDTO.getTitle());
        return taskService.addTask(taskDTO);
    }


    // update task
    @PutMapping("/update{id}")
    public void updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        logger.info("TaskController: Updating task with id: " + id);
        taskService.updateTask(id, taskDTO);
    }

    // delete task
    @DeleteMapping("/delete{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        logger.info("TaskController: Deleting task with id: " + id);
        taskService.deleteTask(id);
    }

    // list tasks
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        logger.info("TaskController: Fetching all tasks.");
        List<Task> tasks = taskService.getTasks();
        if (tasks.isEmpty()) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }


}
