package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "external-validation-service", url = "http://localhost:8090")
public interface ExternalValidationClient {
    @GetMapping("/topic/{topicId}")
    boolean validateComment(@RequestParam("text") String text, @RequestParam("author") String author, @PathVariable Long topicId);
}
