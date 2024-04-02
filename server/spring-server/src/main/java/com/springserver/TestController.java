package com.springserver;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String test(){
        return "hello";
    }
}
