package com.eagle.mysql.controller;

import com.eagle.mysql.annotation.Check;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annotation")
@Slf4j
public class AnnotationController {

    @GetMapping("/check")
    @Check
    public void check() {
        System.out.println("1111");
    }
}
