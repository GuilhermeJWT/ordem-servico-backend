package br.com.systemsgs.ordem_servico_backend.scheduled;

import br.com.systemsgs.ordem_servico_backend.enums.StatusContas;
import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.notification.NotificaEmailService;
import br.com.systemsgs.ordem_servico_backend.repository.ContasPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContasPagarVencidasScheduled {

    private final ContasPagarRepository contasPagarRepository;
    private final NotificaEmailService notificaEmailService;

    @Autowired
    public ContasPagarVencidasScheduled(ContasPagarRepository contasPagarRepository,
                                        @Qualifier("contasPagarNotificationServiceImpl")
                                        NotificaEmailService notificaEmailService) {
        this.contasPagarRepository = contasPagarRepository;
        this.notificaEmailService = notificaEmailService;
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void identificaContasPagarComVencimento(){
        List<ModelContasPagar> contasPagarVencidasHoje = contasPagarRepository.pesquisaContasPagarExpiradas();

        if(!contasPagarVencidasHoje.isEmpty()){
            contasPagarVencidasHoje.stream()
                    .forEach(status -> status.setStatusContas(StatusContas.VENCIDA));

            contasPagarRepository.saveAll(contasPagarVencidasHoje);
            notificaEmailService.notificaEmail(contasPagarVencidasHoje);
        }
    }
}
