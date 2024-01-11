package org.zixiangxu.controller;/*
 * @author Zixiang Xu
 * @date 2024/1/3 22:46
 */


import java.io.IOException;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zixiangxu.pojo.Result;
import org.zixiangxu.utils.AliOSSUtil;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {

        // Get file original name and set destination directory
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString(); // unique name
        if (originalFileName != null && !originalFileName.isEmpty()) {
            // Add filename suffix if original name is non-null and non-empty
            fileName += originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String url = AliOSSUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}