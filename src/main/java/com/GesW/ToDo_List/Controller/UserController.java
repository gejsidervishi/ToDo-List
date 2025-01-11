package com.GesW.ToDo_List.Controller;
import com.GesW.ToDo_List.DTO.AuthenticationRequestDTO;
import com.GesW.ToDo_List.DTO.AuthenticationResponseDTO;
import com.GesW.ToDo_List.DTO.UserDTO;
import com.GesW.ToDo_List.DTO.UserResponseDTO;
import com.GesW.ToDo_List.Model.User;
import com.GesW.ToDo_List.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    // Register user
    @PostMapping("auth/register")
    public ResponseEntity<UserResponseDTO> registerUser(
           @RequestBody @Valid UserDTO userDTO
            ) {
        User registeredUser = userService.registerUser(userDTO);
        // Manual Mapping
        UserResponseDTO response = new UserResponseDTO(
                registeredUser.getEmail(),
                registeredUser.getUsername()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // LogIn user
    @PostMapping("auth/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(
            @RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(
                userService.authenticateUser(authenticationRequestDTO)
        );
    }

    // control user profile
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserProfile(
            Authentication authentication
    ) {
        final var user =
                userService.getUserByUsername(authentication.getName());

        UserDTO userDTO = new UserDTO(user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail());

        return ResponseEntity.ok(userDTO);
    }

}
