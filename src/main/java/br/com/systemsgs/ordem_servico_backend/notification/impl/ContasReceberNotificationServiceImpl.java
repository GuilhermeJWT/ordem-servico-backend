package br.com.systemsgs.ordem_servico_backend.notification.impl;

import br.com.systemsgs.ordem_servico_backend.model.ModelContasReceber;
import br.com.systemsgs.ordem_servico_backend.notification.NotificaEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ContasReceberNotificationServiceImpl implements NotificaEmailService<ModelContasReceber> {

    @Value("${ordem.servico.backend.nome.admin}")
    private String nomeAdmin;

    @Value("${ordem.servico.backend.email.no.replay}")
    private String emailNoReply;

    @Value("${spring.mail.username}")
    private String emailAdmin;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public ContasReceberNotificationServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void notificaEmail(List<ModelContasReceber> dadosNotificacao){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("contasReceber", dadosNotificacao);
            context.setVariable("nomeAdmin", nomeAdmin);
            context.setVariable("quantidadeContasReceber", dadosNotificacao.stream().count());
            context.setVariable("somaTotalContasReceber", dadosNotificacao.stream().map(ModelContasReceber::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            String html = templateEngine.process("contas-receber-email-template", context);

            helper.setFrom(emailNoReply);
            helper.setTo(emailAdmin);
            helper.setText(html, true);
            helper.setSubject("Contas a Receber Vencendo Hoje!");

            javaMailSender.send(message);
        }catch (MessagingException exception){
            log.error("Erro ao tentar enviar o E-mail {}", exception);
        }
    }
}
