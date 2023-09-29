package com.eagle.maven.controller;

import com.eagle.maven.service.IMavenRepoService;
import com.eagle.maven.utiils.RequestMaven2;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author eagle
 * @since 2023-09-29
 */
@RestController
@RequestMapping("/mavenRepo")
public class MavenRepoController {
    @Resource
    private IMavenRepoService mavenRepoService;
    @GetMapping("/getHtmlText")
    public String getHtmlText(){
        return mavenRepoService.getHtmlText("https://repo1.maven.org/maven2/");
    }
    //https://repo1.maven.org/maven2/org/apache/activemq/activeio-parent/
    @GetMapping("/collectHtmlText")
    public String collectHtmlText(String fullUrl){
        mavenRepoService.seekHtmlTextIfNotStored(fullUrl);
        return "ok";
    }
}
