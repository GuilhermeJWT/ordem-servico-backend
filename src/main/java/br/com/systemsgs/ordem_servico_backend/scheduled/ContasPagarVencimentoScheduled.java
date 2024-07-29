package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContasPagarVencimentoScheduled {

    @Autowired
    private ContasPagarRepository contasPagarRepository;

    @Scheduled(cron = "0 0 8 1 * ?")
    public void identificaContasPagarComVencimento(){

    }
}
