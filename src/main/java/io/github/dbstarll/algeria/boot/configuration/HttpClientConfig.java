package io.github.dbstarll.algeria.boot.configuration;

import io.github.dbstarll.utils.http.client.HttpClientFactory;
import io.github.dbstarll.utils.lang.wrapper.EntryWrapper;
import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Optional;

@Configuration
class HttpClientConfig {
    private static final int TIMEOUT = 5000;

    @Bean
    @ConditionalOnMissingBean(HttpClientFactory.class)
    HttpClientFactory httpClientFactory() {
        return new HttpClientFactory().setConnectTimeout(TIMEOUT).setSocketTimeout(TIMEOUT);
    }

    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    CloseableHttpClient httpClient(final HttpClientFactory httpClientFactory) {
        return httpClientFactory.build(this::connectionManager);
    }

    @Bean(initMethod = "start")
    @ConditionalOnMissingBean(HttpAsyncClient.class)
    CloseableHttpAsyncClient httpAsyncClient(final HttpClientFactory httpClientFactory) {
        return httpClientFactory.buildAsync();
    }


    private Map.Entry<SSLContext, HostnameVerifier> trustAll() {
        try {
            return EntryWrapper.wrap(SSLContexts.custom().loadTrustMaterial(TrustAllStrategy.INSTANCE).build(),
                    NoopHostnameVerifier.INSTANCE);
        } catch (GeneralSecurityException e) {
            throw new UnsupportedOperationException("SSLContext定制异常", e);
        }
    }

    private void connectionManager(final HttpClientBuilder builder) {
        Optional.of(trustAll())
                .map(e -> new SSLConnectionSocketFactory(e.getKey(), e.getValue()))
                .map(PoolingHttpClientConnectionManagerBuilder.create()::setSSLSocketFactory)
                .map(PoolingHttpClientConnectionManagerBuilder::build)
                .ifPresent(builder::setConnectionManager);
    }

}
