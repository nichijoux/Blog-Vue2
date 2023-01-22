package com.zh.blog.service;

import com.zh.blog.domain.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    Result uploadFile(MultipartFile file) throws IOException;
}
