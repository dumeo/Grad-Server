package com.grad.controller;

import com.google.gson.JsonObject;
import com.grad.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@Slf4j
public class FileController {
    @Resource
    FileService fileService;

    @PostMapping("/file/upload-file")
    public ResponseEntity<JsonObject> uploadImg(MultipartHttpServletRequest request){
        log.info("get upload file request!!!!");
        return fileService.storeFile(request);
    }
}
