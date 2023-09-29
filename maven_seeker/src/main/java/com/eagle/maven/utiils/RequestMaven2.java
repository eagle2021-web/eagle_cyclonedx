package com.eagle.maven.utiils;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestMaven2 {
    private static final String MAVEN_REPO_PREFIX = "https://repo1.maven.org/maven2/";

    public static String getHtmlText(String url) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String getRelativePath(String url) {
        return url.replace(MAVEN_REPO_PREFIX, "");
    }

    public static List<String> extractLinkFromText(String html) {
        // 解析 HTML
        Document doc = Jsoup.parse(html);

        // 选择所有的 A 标签
        Elements links = doc.select("a");
        ArrayList<String> urls = new ArrayList<>();
        // 遍历 A 标签并提取信息
        for (Element link : links) {
            String url = link.attr("href");
            if(url.endsWith("../")){
                continue;
            }
            urls.add(url);
        }
        return urls;
    }
}
