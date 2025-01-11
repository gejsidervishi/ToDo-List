package com.GesW.ToDo_List.Service;
import com.GesW.ToDo_List.DTO.TaskDTO;
import com.GesW.ToDo_List.Exceptions.DuplicateRecordException;
import com.GesW.ToDo_List.Exceptions.ResourceNotFoundException;
import com.GesW.ToDo_List.Model.Task;
import com.GesW.ToDo_List.Model.User;
import com.GesW.ToDo_List.Repository.TaskRepository;
import com.GesW.ToDo_List.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserService userService;

/* TO DO
    // ERROR HANDLING +++
    // PAGINATION

Bonus
Implement filtering and sorting for the to-do list
Implement unit tests for the API
Implement rate limiting and throttling for the API
Implement refresh token mechanism for the authentication
*/


    // TO DO -> If the authorization token is missing/ invalid -> HTTP 401 UNAUTHORIZED.
    public Task addTask(TaskDTO taskDTO) {
        Task task = new Task();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if a task with the same title exists for the user
        if (taskRepository.existsByTitleAndUserUsername(taskDTO.getTitle(), username)) {
            throw new DuplicateRecordException("A task named '" + taskDTO.getTitle() +"' already exists.");
        }

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUser(user);
        task.setCompleted(taskDTO.getCompleted());
        return taskRepository.save(task);
    }

    public void updateTask(Long id, TaskDTO taskDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            // Ensure the task belongs to the authenticated user
            if (task.getUser().getUsername().equals(username)) {
                // Only update the completion status if it is specified in the DTO
                if (taskDTO.getTitle() != null) {
                    task.setTitle(taskDTO.getTitle());
                } else if (taskDTO.getDescription() != null) {
                    task.setDescription(taskDTO.getDescription());
                } else if (taskDTO.getCompleted() != null) {
                    task.setCompleted(taskDTO.getCompleted());
                }
                task.setUpdatedAt(LocalDateTime.now());
                taskRepository.save(task);
            } else {
                throw new ResourceNotFoundException("You are not authorized to update record with id :" + id);
           }
        } else {
                throw new ResourceNotFoundException("Task not found with id: " + id);
            }
        }


    @Transactional
    public void deleteTask(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            // Ensure the task belongs to the authenticated user
            if (task.getUser().getUsername().equals(username)) {
                taskRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException("You are not authorized to delete record with id :" + id);
            }
        } else {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
    }


    public List<Task> getTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findAllByUserUsername(username);
    }


}
