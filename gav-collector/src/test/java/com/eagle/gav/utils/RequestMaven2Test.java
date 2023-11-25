package com.eagle.gav.utils;

import com.eagle.gav.utiils.RequestMaven2;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class RequestMaven2Test {
    @Test
    public void testExtractLinkFromText() throws IOException {
        System.out.println(1);
        String filePath = Objects.requireNonNull(getClass().getResource("/fixtures/1.html")).getPath();
        String fileContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        List<String> list = RequestMaven2.extractLinkFromText(fileContent);
        for (String s : list) {
            System.out.println(s);
        }
    }


}
