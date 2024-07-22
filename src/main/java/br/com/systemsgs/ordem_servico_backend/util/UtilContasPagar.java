package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class UtilContasPagar {

    @Autowired
    private ContasPagarRepository contasPagarRepository;

    public Optional<BigDecimal> totalContasPagar() {
        return contasPagarRepository.totalContasPagar();
    }
}
