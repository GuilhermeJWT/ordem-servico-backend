package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContasPagarVencidasScheduled {

    private final ContasPagarRepository contasPagarRepository;

    @Autowired
    public ContasPagarVencidasScheduled(ContasPagarRepository contasPagarRepository) {
        this.contasPagarRepository = contasPagarRepository;
    }

    //Todo dia as 07:05 da manhã, o Job será executado para listar as Contas a Pagar vencida, e atualizar para = VENCIDA
    @Scheduled(cron = "0 5 7 * * *")
    public void identificaContasPagarComVencimento(){
        contasPagarVencidas();
    }

    public void contasPagarVencidas(){
        List<ModelContasPagar> contasPagarVencidasHoje = contasPagarRepository.pesquisaContasPagarExpiradas();

        if(!contasPagarVencidasHoje.isEmpty()){
            contasPagarVencidasHoje.stream()
                    .forEach(status -> status.setStatusContas(StatusContas.VENCIDA));

            contasPagarRepository.saveAll(contasPagarVencidasHoje);
        }
    }
}
