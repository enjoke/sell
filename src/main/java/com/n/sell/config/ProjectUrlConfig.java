package com.n.sell.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

    private String wxMpAuthorizeUrl;

    private String wxOpenAuthorizeUrl;

    private String projectUrl;
}
