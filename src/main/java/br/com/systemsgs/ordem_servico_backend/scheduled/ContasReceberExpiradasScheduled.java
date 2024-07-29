package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContasReceberExpiradasScheduled {

    @Autowired
    private ContasReceberRepository contasReceberRepository;

     //Todo dia as 7 da manhã, o Job será executado para listar as Contas a Receber vencida, e atualizar para = EXPIRADA
    @Scheduled(cron = "0 0 7 * * *")
    public void verificaContasReceberVencidas() {
        contasReceberVencidas();
    }

    public void contasReceberVencidas(){
        List<ModelContasReceber> contasReceberVencidasHoje = contasReceberRepository.pesquisaContasReceberExpiradas();

        if(!contasReceberVencidasHoje.isEmpty()){
            contasReceberVencidasHoje.stream()
                    .forEach(status -> status.setStatusContasReceber(StatusContas.VENCIDA));

            contasReceberRepository.saveAll(contasReceberVencidasHoje);
        }
    }
}
