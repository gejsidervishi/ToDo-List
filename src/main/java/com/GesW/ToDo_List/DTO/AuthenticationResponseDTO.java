package com.GesW.ToDo_List.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDTO {
    public String token;

    public AuthenticationResponseDTO(String token) {
        this.token = token;
    }

}
