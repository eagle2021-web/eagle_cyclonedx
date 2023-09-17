package com.eagle.mysql.config;
import com.eagle.mysql.feign.MavenRepositoryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Configuration
@EnableFeignClients
public class FeignConfig {
    // 配置其他 OpenFeign 相关的配置项

}