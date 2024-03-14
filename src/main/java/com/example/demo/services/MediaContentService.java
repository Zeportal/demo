package com.example.demo.services;

import com.example.demo.entity.MediaContent;
import com.example.demo.repos.MediaContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaContentService {
    private final MediaContentRepository mediaContentRepository;

    public MediaContent saveMediaContent(MultipartFile file) throws IOException {
        try {
            return saveFile(file);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public Optional<MediaContent> getMediaContentById(String id) {
        return mediaContentRepository.findById(id);
    }

    public void deleteMediaContentById(String id) {
        mediaContentRepository.deleteById(id);
    }

    public List<MediaContent> getAllMediaContent() {
        return mediaContentRepository.findAll();
    }

    private MediaContent saveFile(MultipartFile file) throws IOException {
        byte[] data = file.getBytes();
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        MediaContent mediaContent = new MediaContent(filename, contentType, data);
        return mediaContentRepository.save(mediaContent);
    }
}
