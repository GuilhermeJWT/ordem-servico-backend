package br.com.systemsgs.ordem_servico_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/health")
public class HealthCheckController {

    @GetMapping("/check")
    public String healthCheck() {
        return "UP";
    }
}
