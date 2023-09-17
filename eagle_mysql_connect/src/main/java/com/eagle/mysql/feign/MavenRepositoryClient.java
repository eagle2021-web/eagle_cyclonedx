package com.eagle.mysql.feign;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name="maven-seeker", url = "https://repo1.maven.org/maven2")
public interface MavenRepositoryClient {

    @GetMapping("/")
    String getRepositoryData();
}