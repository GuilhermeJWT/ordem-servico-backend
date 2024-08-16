package br.com.systemsgs.ordem_servico_backend.notification.impl;

import br.com.systemsgs.ordem_servico_backend.model.ModelContasPagar;
import br.com.systemsgs.ordem_servico_backend.notification.NotificaEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class ContasPagarNotificationServiceImpl implements NotificaEmailService<ModelContasPagar> {

    @Value("${ordem.servico.backend.nome.admin}")
    private String nomeAdmin;

    @Value("${ordem.servico.backend.email.no.replay}")
    private String emailNoReply;

    @Value("${spring.mail.username}")
    private String emailAdmin;

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public ContasPagarNotificationServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void notificaEmail(List<ModelContasPagar> dadosNotificacao) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("contasPagar", dadosNotificacao);
            context.setVariable("nomeAdmin", nomeAdmin);
            context.setVariable("quantidadeContasPagar", dadosNotificacao.stream().count());
            context.setVariable("somaTotalContasPagar", dadosNotificacao.stream().map(ModelContasPagar::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            String html = templateEngine.process("contas-pagar-email-template", context);

            helper.setFrom(emailNoReply);
            helper.setTo(emailAdmin);
            helper.setText(html, true);
            helper.setSubject("Contas a Pagar Vencendo Hoje!");

            javaMailSender.send(message);
        }catch (MessagingException exception){
            log.error("Erro ao tentar enviar o E-mail {}", exception);
        }
    }
}
