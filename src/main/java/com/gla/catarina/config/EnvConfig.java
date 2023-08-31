package com.gla.catarina.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全局配置类
 *
 */
@Component
@ConfigurationProperties(prefix = "catarina.env")
@Data
public class EnvConfig {

    private List<Entry> envs;

    @Data
    public static class Entry {
        private String key;
        private String value;

    }

    public void setEnvs(List<Entry> envs) {
        this.envs = envs;
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");

                factory.addErrorPages(error400Page, error401Page, error404Page, error500Page);
            }

        };
    }
}
