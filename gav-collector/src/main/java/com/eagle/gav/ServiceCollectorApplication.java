package com.eagle.gav;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenw
 */
@SpringBootApplication
@ServletComponentScan("com.eagle")
@EntityScan("com.eagle.gav.pojo.entity")
//@MapperScan(basePackages = "com.eagle.mysql.convertor")
public class ServiceCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCollectorApplication.class, args);
    }

    @RestController
    @RequestMapping("")
    public static class HelloController {
        @GetMapping("/hello")
        public String hello(){
            return "hello";
        }
    }
}
