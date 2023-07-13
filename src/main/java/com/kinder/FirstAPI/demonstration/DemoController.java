package com.kinder.FirstAPI.demonstration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    /**
     * Exemple GetMapping, need to register and authenticate for having your token
     * See how in AuthController.java.
     *
     * Then get this route for test the authentication with your Bearer Token
     */
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured World!");
    }

}
