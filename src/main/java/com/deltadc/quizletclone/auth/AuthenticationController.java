package com.deltadc.quizletclone.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignUpRequest request) {
        if(request.getUsername().isEmpty() || request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            AuthenticationResponse.builder()
                                    .message("khong duoc de trong")
                                    .build()
                    );
        }

        System.out.println(request.getUsername() + " " + request.getPassword() + " " + request.getPassword());
        return authenticationService.signup(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        if(request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            AuthenticationResponse.builder()
                                    .message("khong duoc de trong")
                                    .build()
                    );
        }


        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
