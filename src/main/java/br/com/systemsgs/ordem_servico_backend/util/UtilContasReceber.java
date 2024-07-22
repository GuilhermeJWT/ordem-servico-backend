package br.com.systemsgs.ordem_servico_backend.util;

import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class UtilContasReceber {

    @Autowired
    private ContasReceberRepository contasReceberRepository;

    public Optional<BigDecimal> totalContasReceber(){
        return contasReceberRepository.totalContasReceber();
    }

    public Optional<Integer> quantidadeContasInadimplentes() {
        return contasReceberRepository.quantidadeContasInadimplentes();
    }
}
