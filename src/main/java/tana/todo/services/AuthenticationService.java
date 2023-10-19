package tana.todo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import tana.todo.dtos.AuthenticationRequestDTO;
import tana.todo.dtos.AuthenticationResponseDTO;
import tana.todo.entities.User;
import tana.todo.exceptions.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        try {
            User user = userService.getUserByUsername(request.getUsername());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return new AuthenticationResponseDTO(token, refreshToken);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Password does not match for username: " + request.getUsername());
        }
    }
}
