package com.eagle.maven.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eagle.maven.mapper.MavenGavMapper;
import com.eagle.maven.pojo.entity.MavenGav;
import com.eagle.maven.pojo.entity.MavenRepo;
import com.eagle.maven.mapper.MavenRepoMapper;
import com.eagle.maven.service.IMavenRepoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eagle.maven.utiils.RequestMaven2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public String getHtmlText(String url) {
        String text;
        try {
            text = RequestMaven2.getHtmlText(url);
        } catch (IOException e) {
            log.error("获取{}对于text失败", url);
            throw new RuntimeException(e);
        }
        return text;
    }

    @Override
    public void seekHtmlTextIfNotStored(String fullUrl) {
        log.info("fullUrl={}", fullUrl);
        String relativeUrl = RequestMaven2.getRelativePath(fullUrl);
        LambdaQueryWrapper<MavenRepo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MavenRepo::getRelativePath, relativeUrl);
        MavenRepo mavenRepo = mavenRepoMapper.selectOne(queryWrapper);
        if (mavenRepo != null) {
            log.info("url对应repo已存在");
            return ;
        }
        String htmlText = getHtmlText(fullUrl);
        if (htmlText == null) {
            return ;
        }
        List<String> strings = RequestMaven2.extractLinkFromText(htmlText);
        boolean fileEndJar = strings.stream().anyMatch(v -> Stream.of(".pom", ".jar", ".war", "aar").anyMatch(v::endsWith));
        if(fileEndJar){
            handleJar(fullUrl);
            return ;
        }
        for (String subRelativeUrl : strings) {
            if (!subRelativeUrl.endsWith("/")) {
                continue;
            }
            // 下一个
            String nextFullUrl = joinUrl(fullUrl, subRelativeUrl);
            System.out.println(nextFullUrl);
            seekHtmlTextIfNotStored(nextFullUrl);
        }
        mavenRepo = new MavenRepo();
        mavenRepo.setText(htmlText);
        mavenRepo.setRelativePath(relativeUrl);
        mavenRepoMapper.insert(mavenRepo);
    }

    private void handleJar(String curFullUrl) {

        String restUrl = RequestMaven2.getRelativePath(curFullUrl);
        if (restUrl.isEmpty()) {
            return;
        }
        if(restUrl.endsWith("/")){
            restUrl = restUrl.substring(0, restUrl.length() - 1);
        }
        log.info("handleJar, restUrl = {}", restUrl);
        String[] split = restUrl.split("/");
        List<String> list = Arrays.stream(split).collect(Collectors.toList());
        if(list.size() <= 2){
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

    private String joinUrl(String prefix, String sub) {
        if (!prefix.endsWith("/")) {
            prefix += "/";
        }
        if (sub.startsWith("/")) {
            sub = sub.substring(1);
        }

        return prefix + sub;
    }
}

