package org.flowio.authenticationservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ping")
public class PingPongController {

    @GetMapping
    public ResponseEntity<String> pingPong() {
        return ResponseEntity.ok("Pong");
    }

}
