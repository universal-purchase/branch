package com.chocolate.blogsch.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private String apiKey;

    public String getApikey() {
        return apiKey;
    }

    public void setApikey(String apiKey) {
        this.apiKey = apiKey;
    }

}
