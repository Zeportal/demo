package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collation = "media_content")
@Data
public class MediaContent {
    @Id
    private String id;
    private String filename;
    private String contentType;
    private byte[] content;

    public MediaContent(String filename, String contentType, byte[] content) {
        this.filename = filename;
        this.contentType = contentType;
        this.content = content;
    }
}
