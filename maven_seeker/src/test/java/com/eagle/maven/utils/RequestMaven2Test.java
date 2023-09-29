package com.eagle.maven.utils;

import com.eagle.maven.utiils.RequestMaven2;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RequestMaven2Test {
    @Test
    public void testExtractLinkFromText() throws IOException {
        String filePath = Objects.requireNonNull(getClass().getResource("/fixtures/1.html")).getPath();
        String fileContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        RequestMaven2.extractLinkFromText(fileContent);
    }

}
