package com.GesW.ToDo_List.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
   public String username;
   public String email;

   public UserResponseDTO(String username, String email) {
      this.username = username;
      this.email = email;
   }
}
