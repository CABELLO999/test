package com.invex.test.gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * Filtro global para reenviar la cabecera Authorization (Basic Auth) a los microservicios.
 */
@Component
public class AuthHeaderForwardingFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(AuthHeaderForwardingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        logger.info("[Gateway] Request URI: {}", exchange.getRequest().getURI());
        logger.info("[Gateway] Authorization header received: {}", authHeader);
        if (authHeader != null) {
            exchange = exchange.mutate()
                .request(r -> r.header("Authorization", authHeader))
                .build();
            logger.info("[Gateway] Authorization header forwarded to downstream service.");
        } else {
            logger.warn("[Gateway] No Authorization header present in request.");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Alta prioridad
    }
}
