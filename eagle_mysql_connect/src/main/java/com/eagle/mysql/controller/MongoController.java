package com.eagle.mysql.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/mongo")
@Slf4j
public class MongoController {
    @Resource
    private MongoTemplate template;

    @GetMapping("/add")
    public String add() {
        MongoCollection<Document> java = template.getCollection("java");
        FindIterable<Document> documents = java.find();
        documents.forEach(System.out::println);
        return "ok";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam String filename) {

        try {
            System.out.println(file.getSize());
            log.info("contentType = {}", file.getContentType());
            System.out.println(file.getName());
            System.out.println(file.getOriginalFilename());
            System.out.println(filename);
            log.info("Objects.requireNonNull(file.getOriginalFilename()) = {}", Objects.requireNonNull(file.getOriginalFilename()));
            GridFSBucket gridFSBucket = GridFSBuckets.create(template.getDb());
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024) // 设置 chunk 大小，默认为 255KB
                    .metadata(new Document("contentType", file.getContentType())); // 其他元数据信息
            InputStream inputStream = file.getInputStream();

            ObjectId fileId = gridFSBucket.uploadFromStream(filename,
                    inputStream,
                    options);
            // 存储完毕后的逻辑
            log.info("File uploaded with id: {}", fileId);
            inputStream.close();
            return "ok";
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            return "error";
        }
    }

    @GetMapping("/files/getFile")
    public ResponseEntity<org.springframework.core.io.Resource> getFile(@RequestParam("filename") String filename) throws IOException {
        GridFSBucket gridFSBucket = GridFSBuckets.create(template.getDb());

        GridFSFindIterable files = gridFSBucket.find(Filters.eq("filename", filename));
        GridFSFile file = files.first();
        if (file != null) {
            log.info("file={}", file);
            System.out.println(file.getChunkSize());
            System.out.println(file.getObjectId());
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(file.getObjectId());
            GridFsResource resource = new GridFsResource(file, downloadStream);
            InputStream inputStream = resource.getInputStream();
            // 返回文件数据
            HttpHeaders headers = new HttpHeaders();
            System.out.println(resource);
            Document metadata = file.getMetadata();
            assert metadata != null;
            String contentType = metadata.getString("contentType");
            System.out.println(contentType);
            System.out.println(file.getFilename());
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getFilename()).build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(inputStream))
                    ;
        } else {
            // 文件不存在
            return ResponseEntity.notFound().build();
        }
    }
}
