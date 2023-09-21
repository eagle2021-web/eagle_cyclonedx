package com.eagle.mysql.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HelloController {
    @GetMapping("hello")
    public String hello(HttpServletRequest request){
        System.out.println(request.getRequestedSessionId());
        System.out.println(request.getSession().getId());
        System.out.println("hello");
        return "hello";
    }
}