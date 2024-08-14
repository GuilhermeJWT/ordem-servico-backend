package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class UtilContasReceber {

    private final ContasReceberRepository contasReceberRepository;

    @Autowired
    public UtilContasReceber(ContasReceberRepository contasReceberRepository) {
        this.contasReceberRepository = contasReceberRepository;
    }

    public Optional<BigDecimal> totalContasReceber(){
        return contasReceberRepository.totalContasReceber();
    }

    public Optional<Integer> quantidadeContasInadimplentes() {
        return contasReceberRepository.quantidadeContasInadimplentes();
    }

    public Optional<Integer> quantidadeContasReceberVencidas() {
        return contasReceberRepository.quantidadeContasReceberVencidas();
    }
}
