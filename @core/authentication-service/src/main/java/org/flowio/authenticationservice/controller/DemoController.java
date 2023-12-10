package org.flowio.authenticationservice.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ping")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> pingPong() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

}
