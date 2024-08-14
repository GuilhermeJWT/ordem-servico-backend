package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class UtilContasPagar {

    private final ContasPagarRepository contasPagarRepository;

    @Autowired
    public UtilContasPagar(ContasPagarRepository contasPagarRepository) {
        this.contasPagarRepository = contasPagarRepository;
    }

    public Optional<BigDecimal> totalContasPagar() {
        return contasPagarRepository.totalContasPagar();
    }

    public Optional<Integer> quantidadeContasPagarVencidas() {
        return contasPagarRepository.quantidadeContasPagarVencidas();
    }
}
