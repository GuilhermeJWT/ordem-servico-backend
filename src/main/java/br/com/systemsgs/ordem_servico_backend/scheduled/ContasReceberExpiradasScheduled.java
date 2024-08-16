package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.notification.NotificaEmailService;
import br.com.systemsgs.ordem_servico_backend.repository.ContasReceberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ContasReceberExpiradasScheduled {

    private final ContasReceberRepository contasReceberRepository;
    private final NotificaEmailService notificaEmailService;

    @Autowired
    public ContasReceberExpiradasScheduled(ContasReceberRepository contasReceberRepository,
                                           @Qualifier("contasReceberNotificationServiceImpl")
                                           NotificaEmailService notificaEmailService) {
        this.contasReceberRepository = contasReceberRepository;
        this.notificaEmailService = notificaEmailService;
    }

    @Scheduled(cron = "0 5 7 * * *")
    public void verificaContasReceberVencidas() {
        List<ModelContasReceber> contasReceberVencidasHoje = contasReceberRepository.pesquisaContasReceberExpiradas();

        if(!contasReceberVencidasHoje.isEmpty()){
            contasReceberVencidasHoje.stream().forEach(status -> status.setStatusContasReceber(StatusContas.VENCIDA));

            contasReceberRepository.saveAll(contasReceberVencidasHoje);
            notificaEmailService.notificaEmail(contasReceberVencidasHoje);
        }
    }
}
