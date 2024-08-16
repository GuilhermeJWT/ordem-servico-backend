package br.com.systemsgs.ordem_servico_backend.notification;

import java.util.List;

public interface NotificaEmailService<T> {

    void notificaEmail(List<T> dadosNotificacao);

}
