package com.example.demo.repos;

import com.example.demo.entity.MediaContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaContentRepository extends MongoRepository<MediaContent,String> {
}
