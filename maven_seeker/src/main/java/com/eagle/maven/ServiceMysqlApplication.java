package com.eagle.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.eagle")
@EntityScan("com.eagle.maven.pojo.entity")
//@MapperScan(basePackages = "com.eagle.mysql.convertor")
public class ServiceMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMysqlApplication.class, args);
    }

}
