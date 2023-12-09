package org.flowio.authenticationservice.auth;


import lombok.RequiredArgsConstructor;
import org.flowio.authenticationservice.Entities.LoginUser;
import org.flowio.authenticationservice.Entities.LoginUserRepository;
import org.flowio.authenticationservice.Entities.Role;
import org.flowio.authenticationservice.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {

        if(
                request.getFirstname() == null
                || request.getLastname() == null
                || request.getLoginEmail() == null
                || request.getPassword() == null
                ) {
            return AuthenticationResponse.builder()
//                .message("Firstname, lastname, login email and password must not be null")
                .build();
        }

        var loginUser = LoginUser.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .loginEmail(request.getLoginEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        loginUserRepository.save(loginUser);
        var jwtToken = jwtService.generateJwtToken(loginUser);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLoginEmail(),
                        request.getPassword()
                )
        );
        var loginUser = loginUserRepository.findByLoginEmail(request.getLoginEmail())
                        .orElseThrow();
        var jwtToken = jwtService.generateJwtToken(loginUser);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}
