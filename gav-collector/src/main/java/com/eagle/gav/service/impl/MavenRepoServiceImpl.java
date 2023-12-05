package com.eagle.gav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eagle.gav.mapper.MavenGavMapper;
import com.eagle.gav.pojo.entity.MavenGav;
import com.eagle.gav.pojo.entity.MavenRepo;
import com.eagle.gav.mapper.MavenRepoMapper;
import com.eagle.gav.service.IMavenRepoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eagle.gav.utiils.RequestMaven2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author eagle
 * @since 2023-09-29
 */
@Service
@Slf4j
public class MavenRepoServiceImpl extends ServiceImpl<MavenRepoMapper, MavenRepo> implements IMavenRepoService {
    @Resource
    private MavenRepoMapper mavenRepoMapper;
    @Resource
    private MavenGavMapper mavenGavMapper;
    private static final int THREAD_POOL_SIZE = 20; // 可以根据需要调整线程池大小
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    private static final ConcurrentLinkedQueue<String> QUEUE = new ConcurrentLinkedQueue<>();

    public void seekHtmlTextIfNotStoredWithQueue(String fullUrl) {
        QUEUE.add(fullUrl);
        AtomicInteger counter = new AtomicInteger(0);
        int maxWait = 1000_1000;
        Runnable runnable = () -> {
            while (true) {
                if (QUEUE.isEmpty()) {
                    counter.incrementAndGet();
                    if (counter.get() >= maxWait) {
                        break;
                    }
                    continue;
                }
                counter.set(0);
                String url = QUEUE.poll();
                if (url == null) {
                    continue;
                }
                String htmlText = seekHtmlTextIfNotStored(url);
                if (htmlText == null) {
                    try {
                        QUEUE.add(url);
                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException e) {
                        log.error("等待结束");
                    }
                    continue;
                }
                List<String> strings = RequestMaven2.extractLinkFromText(htmlText);
                List<String> list = strings.stream().filter(v -> v.endsWith("/"))
                        .map(sub -> joinUrl(url, sub))
                        .collect(Collectors.toList());
                QUEUE.addAll(list);
            }
        };
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executorService.submit(runnable);
        }
        // 你可以选择在某个地方等待所有的任务完成，或者根据你的逻辑来终止executorService。
        // 例如，在代码的某个地方可以执行以下关闭操作
        // 注意：这将等待现有任务完成，不再接受新任务
//        System.out.println("here");
//        executorService.shutdown();
        try {
            // 等待1小时以完成所有作业（根据实际情况调整这个时间）
            if (!executorService.awaitTermination(100, TimeUnit.HOURS)) {
                log.error("timeout");
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            log.error("shutdown");
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String getHtmlText(String url) {
        String text = null;
        try {
            text = RequestMaven2.getHtmlText(url);
        } catch (IOException e) {
            log.error("获取{}对于text失败", url);
        }
        return text;
    }

    @Override
    public String seekHtmlTextIfNotStored(String fullUrl) {
        log.info("fullUrl={}", fullUrl);
        String relativeUrl = RequestMaven2.getRelativePath(fullUrl);
        LambdaQueryWrapper<MavenRepo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MavenRepo::getRelativePath, relativeUrl);
        MavenRepo mavenRepo = mavenRepoMapper.selectOne(queryWrapper);
        if (mavenRepo != null) {
            log.info("url对应repo已存在");
            return mavenRepo.getText();
        }
        String htmlText = getHtmlText(fullUrl);
        if (htmlText == null) {
            return null;
        }

        List<String> strings = RequestMaven2.extractLinkFromText(htmlText);
        boolean fileEndJar = strings.stream().anyMatch(v -> Stream.of(".pom", ".jar", ".war", "aar").anyMatch(v::endsWith));
        if (fileEndJar) {
            handleJar(fullUrl);
        }
        mavenRepo = new MavenRepo();
        mavenRepo.setText(htmlText);
        mavenRepo.setRelativePath(relativeUrl);
        mavenRepoMapper.insert(mavenRepo);
        return htmlText;
    }


    private void handleJar(String curFullUrl) {
        String restUrl = RequestMaven2.getRelativePath(curFullUrl);
        if (restUrl.isEmpty()) {
            return;
        }
        if (restUrl.endsWith("/")) {
            restUrl = restUrl.substring(0, restUrl.length() - 1);
        }
        log.info("handleJar, restUrl = {}", restUrl);
        String[] split = restUrl.split("/");
        List<String> list = Arrays.stream(split).collect(Collectors.toList());
        if (list.size() <= 2) {
            return;
        }
        List<String> strings = list.subList(0, list.size() - 2);
        String groupId = String.join(".", strings);
        String artifactId = list.get(list.size() - 2);
        String version = list.get(list.size() - 1);
        log.info("{} {} {}", groupId, artifactId, version);
        MavenGav mavenGav = new MavenGav();
        mavenGav.setGroupId(groupId);
        mavenGav.setArtifactId(artifactId);
        mavenGav.setVersion(version);
        mavenGavMapper.insert(mavenGav);
    }

    private static String joinUrl(String prefix, String sub) {
        if (!prefix.endsWith("/")) {
            prefix += "/";
        }
        if (sub.startsWith("/")) {
            sub = sub.substring(1);
        }

        return prefix + sub;
    }
}

