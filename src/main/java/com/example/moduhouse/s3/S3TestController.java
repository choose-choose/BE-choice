package com.example.moduhouse.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3TestController {
    private final S3Uploader s3Uploader;

//    @PostMapping("/images")
//    public String upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
//        //s3 Bucket 내부에 "static"이라는 이름의 디렉토리가 생성됨
//        s3Uploader.upload(multipartFile);   userDetails.getUser(), boardrequestDto, multipartFile)
//        return "test";
//    }

//    //불러오기
//    @GetMapping("/images/{id}")
//    public String download(@PathVariable String id) {
//        String imgPath = s3Uploader.download(id);
//        return imgPath;
//    }
}

