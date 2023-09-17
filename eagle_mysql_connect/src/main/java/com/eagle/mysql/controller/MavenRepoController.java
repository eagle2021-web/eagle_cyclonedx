package com.eagle.mysql.controller;

import com.eagle.mysql.feign.MavenRepositoryClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/maven")
@Slf4j
public class MavenRepoController {
    private static final String COL_MAVEN_REP = "col_maven_rep";
    @Resource
    private MavenRepositoryClient mavenRepositoryClient;
    @Resource
    private MongoTemplate template;
    @GetMapping("/getRep")
    public String getRep() {
        String repositoryData = mavenRepositoryClient.getRepositoryData();
        String url = "/";

        // 查询文档是否存在
        Document document = template.getCollection(COL_MAVEN_REP).find(Filters.eq("url", url)).first();

        if (document == null) {
            // 文档不存在，插入新文档
            Document newDocument = new Document();
            newDocument.append("url", url)
                    .append("repositoryData", repositoryData);
            template.getCollection(COL_MAVEN_REP).insertOne(newDocument);
        } else {
            // 文档存在，更新文档
            Document updatedDocument = new Document();
            updatedDocument.append("url", url)
                    .append("repositoryData", repositoryData);
            template.getCollection(COL_MAVEN_REP).replaceOne(Filters.eq("url", url), updatedDocument,
                    new ReplaceOptions().upsert(true));
        }

        System.out.println(repositoryData);
        return repositoryData;
    }

    @GetMapping("/query")
    public String add() {
        MongoCollection<Document> java = template.getCollection(COL_MAVEN_REP);
        System.out.println(java.countDocuments());
        FindIterable<Document> documents = java.find();
        if(documents.first() == null){
            return null;
        }
        return documents.toString();
    }
}
