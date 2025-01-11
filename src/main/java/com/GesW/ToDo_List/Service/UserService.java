package com.GesW.ToDo_List.Service;
import com.GesW.ToDo_List.DTO.AuthenticationRequestDTO;
import com.GesW.ToDo_List.DTO.AuthenticationResponseDTO;
import com.GesW.ToDo_List.DTO.UserDTO;
import com.GesW.ToDo_List.Exceptions.DuplicateRecordException;
import com.GesW.ToDo_List.Exceptions.IncorrectPasswordException;
import com.GesW.ToDo_List.Exceptions.ResourceNotFoundException;
import com.GesW.ToDo_List.Model.User;
import com.GesW.ToDo_List.Repository.UserRepository;
import com.GesW.ToDo_List.config.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.GONE;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    // register User
    // USERNAME ALREADY EXISTS + PASSWORD DOES NOT SATISFY CRITERIA EXCEPTIONS
    @Transactional
    public User registerUser(UserDTO userDTO) {
        Optional<User> users = userRepository.findByUsername(userDTO.getUserName());

        if(users.isPresent())
            throw new DuplicateRecordException("Username '" + userDTO.getUserName() + "' already exists.");

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }


    // Authenticate User
    // USER NOT FOUND and (INCORRECT PASSWORD) EXCEPTIONS
    public AuthenticationResponseDTO authenticateUser(
            AuthenticationRequestDTO authenticationRequestDTO) {
        String users = userRepository.findByUsername(authenticationRequestDTO.getUsername())
                .map(User::getUsername)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User '" + authenticationRequestDTO.getUsername() + "' does not exist.")
                );

        // Optional<> get() method to retrieve the value returned by the OPTIONAL
        Optional<String> password = userRepository.findByUsername(authenticationRequestDTO.getUsername())
                .map(User::getPassword);

        if(!passwordEncoder.matches(authenticationRequestDTO.getPassword(),password.get())) {
            throw new IncorrectPasswordException("Incorrect password entered.");
        }

        final var authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(authenticationRequestDTO.getUsername(),
                        authenticationRequestDTO.getPassword());
        final var authentication = authenticationManager
                .authenticate(authToken);
        final var token = jwtService.generateToken(authenticationRequestDTO.getUsername());
        return new AuthenticationResponseDTO(token);
    }

    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(GONE,
                        "The user account has been deleted or inactivated"));
    }

}
