package com.zh.blog.service.impl;

import com.zh.blog.domain.Result;
import com.zh.blog.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public Result uploadFile(MultipartFile file) throws IOException {
        return null;
    }
}
