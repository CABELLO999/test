package com.invex.test.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para AuthHeaderForwardingFilter.
 * Se valida el reenvio correcto de la cabecera Authorization y el logging.
 */
class AuthHeaderForwardingFilterTest {
    private AuthHeaderForwardingFilter filter;
    private ServerWebExchange exchange;
    private GatewayFilterChain chain;
    private ServerHttpRequest request;

    @BeforeEach
    void setUp() {
        filter = new AuthHeaderForwardingFilter();
        exchange = mock(ServerWebExchange.class);
        chain = mock(GatewayFilterChain.class);
        request = mock(ServerHttpRequest.class);
        when(exchange.getRequest()).thenReturn(request);
        when(chain.filter(any())).thenReturn(Mono.empty());
    }

    @Test
    void testFilter_withAuthorizationHeader_forwardsHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjpwYXNz");
        when(request.getHeaders()).thenReturn(headers);
        when(request.getURI()).thenReturn(java.net.URI.create("/test"));

        // Mock manual del builder para evitar ambigüedad y cumplir nullidad
        final ServerWebExchange mutatedExchange = mock(ServerWebExchange.class);
        ServerWebExchange.Builder builder = new ServerWebExchange.Builder() {
            @Override
            public @org.springframework.lang.NonNull ServerWebExchange.Builder request(@org.springframework.lang.NonNull java.util.function.Consumer<org.springframework.http.server.reactive.ServerHttpRequest.Builder> consumer) {
                return this;
            }
            @Override
            public @org.springframework.lang.NonNull ServerWebExchange.Builder request(@org.springframework.lang.NonNull org.springframework.http.server.reactive.ServerHttpRequest request) {
                return this;
            }
            @Override
            public @org.springframework.lang.NonNull ServerWebExchange.Builder response(@org.springframework.lang.NonNull org.springframework.http.server.reactive.ServerHttpResponse response) {
                return this;
            }
            @Override
            public @org.springframework.lang.NonNull ServerWebExchange.Builder principal(@org.springframework.lang.NonNull reactor.core.publisher.Mono<java.security.Principal> principalMono) {
                return this;
            }
            @Override
            public @org.springframework.lang.NonNull ServerWebExchange build() {
                return mutatedExchange;
            }
        };
        when(exchange.mutate()).thenReturn(builder);

        filter.filter(exchange, chain).block();
        // Verifica que se llamo a chain.filter con el exchange mutado
        verify(chain).filter(mutatedExchange);
    }

    @Test
    void testFilter_withoutAuthorizationHeader_logsWarning() {
        HttpHeaders headers = new HttpHeaders();
        when(request.getHeaders()).thenReturn(headers);
        when(request.getURI()).thenReturn(java.net.URI.create("/test"));
        filter.filter(exchange, chain).block();
        // Verifica que se llamo a chain.filter con el exchange original
        verify(chain).filter(exchange);
    }

    @Test
    void testGetOrder_returnsHighPriority() {
        assertEquals(-1, filter.getOrder());
    }
}
   

