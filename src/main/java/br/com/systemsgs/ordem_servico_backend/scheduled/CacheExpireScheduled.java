package br.com.systemsgs.ordem_servico_backend.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheExpireScheduled {

    @Autowired
    public CacheManager cacheManager;

    public void limpandoCaches() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    @Scheduled(fixedRate = 3600000) // 1 Hora
    public void jobCache() {
        limpandoCaches();
        log.info("Limpando os Caches da aplicação...");
    }
}
