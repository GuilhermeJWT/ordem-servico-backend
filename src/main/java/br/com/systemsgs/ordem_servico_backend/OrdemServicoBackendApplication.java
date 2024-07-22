package br.com.systemsgs.ordem_servico_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class OrdemServicoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdemServicoBackendApplication.class, args);
	}

}
