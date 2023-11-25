package com.eagle.gav.utiils;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenw
 */
public class RequestMaven2 {
    static class Task implements Runnable {
        private final String name;

        Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " is running on thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " is done");
        }
    }

    private static final String MAVEN_REPO_PREFIX = "https://repo1.maven.org/maven2/";

    public static String getHtmlText(String fullUrl) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(fullUrl)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() == null) {
            return null;
        }
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
            if (url.endsWith("../")) {
                continue;
            }
            urls.add(url);
        }
        return urls;
    }


}
