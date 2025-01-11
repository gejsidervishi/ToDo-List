package com.GesW.ToDo_List.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.io.Serializable;

public class UserDTO implements Serializable {
    @Getter
    public String firstName;
    @Getter
    public String lastName;
    @Getter
    public String userName;
    @Getter
    public String email;

    // Jakarta Validation
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include an uppercase letter, lowercase letter, number, and special character."
    )

    @Getter
    public String password;

    public UserDTO(String firstName, String lastName, String userName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
    }
}
