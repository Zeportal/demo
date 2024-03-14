package com.example.demo.controller;

import com.example.demo.entity.MediaContent;
import com.example.demo.services.MediaContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MediaContentController {

    private final MediaContentService mediaContentService;

    @PostMapping("/media/upload")
    public MediaContent uploadMedia(@RequestParam("file") MultipartFile file) throws IOException {
        return mediaContentService.saveMediaContent(file);
    }

    @GetMapping("/media/{id}")
    public Optional<MediaContent> getMediaContentById(@PathVariable String id) {
        return mediaContentService.getMediaContentById(id);
    }

    @GetMapping("/media")
    public List<MediaContent> getAllMediaContent() {
        return mediaContentService.getAllMediaContent();
    }

    @DeleteMapping("/media/{id}")
    public void deleteMediaContentById(@PathVariable String id) {
        mediaContentService.deleteMediaContentById(id);
    }
}
