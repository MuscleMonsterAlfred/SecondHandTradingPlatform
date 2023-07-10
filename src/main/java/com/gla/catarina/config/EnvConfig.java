package com.gla.catarina.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全局配置类
 *
 * @author yangfan
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
}
