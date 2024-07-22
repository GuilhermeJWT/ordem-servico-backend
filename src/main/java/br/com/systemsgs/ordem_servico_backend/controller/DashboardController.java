package br.com.systemsgs.ordem_servico_backend.controller;

import br.com.systemsgs.ordem_servico_backend.dto.response.DashboardResponse;
import br.com.systemsgs.ordem_servico_backend.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Api para retornar os dados do Dashboard - V1")
@RestController
@RequestMapping("/api/dashboard/v1")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Operation(summary = "Prepara dados para o Dashboard", description = "Api para montar os dados do Dashboard do Sistema")
    @GetMapping("/dados")
    public ResponseEntity<DashboardResponse> retornaDadosDashboard(){
        return ResponseEntity.ok(dashboardService.retornaDadosDashboard());
    }
}
