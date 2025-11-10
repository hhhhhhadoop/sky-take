package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {
    private AliOssUtil aliOssUtil;

    @RequestMapping("/upload")
    @ApiOperation("上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传:{}", file);
        try {

            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString()+suffix;

            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            log.error(e.getMessage());

        }
        return Result.error("文件上传失败");
    }
}
