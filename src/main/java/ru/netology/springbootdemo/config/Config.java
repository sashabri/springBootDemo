package ru.netology.springbootdemo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.springbootdemo.systemProfile.DevProfile;
import ru.netology.springbootdemo.systemProfile.ProductionProfile;
import ru.netology.springbootdemo.systemProfile.SystemProfile;

@Configuration
public class Config {
    @ConditionalOnProperty(
            name = "netology.profile.dev",
            havingValue = "true"
    )
    @Bean
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @ConditionalOnProperty(
            name = "netology.profile.dev",
            havingValue = "false",
            matchIfMissing = true
    )
    @Bean
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
