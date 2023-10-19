package tana.todo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tana.todo.dtos.AuthenticationRequestDTO;
import tana.todo.dtos.AuthenticationResponseDTO;
import tana.todo.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    public AuthenticationResponseDTO authenticate(@Valid @RequestBody AuthenticationRequestDTO request) {
        return authenticationService.authenticate(request);
    }
}
