package com.eagle.maven.utils;

import com.eagle.maven.utiils.RequestMaven2;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestMaven2Test {
    @Test
    public void testExtractLinkFromText() throws IOException {
        String filePath = getClass().getResource("/fixtures/1.html").getPath();
        String fileContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        RequestMaven2.extractLinkFromText(fileContent);
    }
    @Test
    public void testExtractUrl(){
//        String fullUrl = "https://repo1.maven.org/maven2/org/apache/activemq/activeio-parent";
//        String subRelativeUrl = "13.1/";
        String baseUrl = "https://www.example.com";
        String path = "api/users/";

        try {
            URL url = new URL(new URL(baseUrl), path);
            String fullUrl = url.toString();
            System.out.println(fullUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
