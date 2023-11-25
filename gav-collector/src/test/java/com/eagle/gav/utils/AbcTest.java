package com.eagle.gav.utils;

import com.eagle.gav.utiils.RequestMaven2;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AbcTest {
    @Test
    public void testExtractLinkFromText() throws IOException {
        String htmlText = RequestMaven2.getHtmlText("https://repo1.maven.org/maven2/");
        System.out.println(htmlText);
    }
}
