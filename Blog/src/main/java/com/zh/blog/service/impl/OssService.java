package com.zh.blog.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.zh.blog.constants.OssConstants;
import com.zh.blog.handler.exception.BlogException;
import com.zh.blog.utils.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class OssService {
    private static final String[] imageSuffixList = {
            "jpg", "jpeg", "png", "gif"
    };

    /**
     * 上传图片文件
     *
     * @param imageFile 要上传的图片文件
     * @return 上传后返回的url路径
     */
    public String uploadImage(MultipartFile imageFile) {
        // 获取文件原始名
        String fileName = imageFile.getOriginalFilename();
        // 判断是否为图片
        if (!fileIsImage(Objects.requireNonNull(fileName))) {
            throw new BlogException("文件不为图片");
        }
        // 上传文件
        try {
            return uploadFile(fileName, imageFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BlogException("上传文件失败");
        }
    }

    /**
     * 上传文件
     *
     * @param file 要上传的文件
     * @return 上传后返回的url路径
     */
    public String uploadFile(MultipartFile file) {
        // 获取文件原始名
        String fileName = file.getOriginalFilename();
        // 上传文件
        try {
            return uploadFile(fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BlogException("上传文件失败");
        }
    }

    /**
     * 上传文件
     *
     * @param fileName    要上传的文件的文件名
     * @param inputStream 输入流
     * @return 上传后返回的url路径
     */
    private String uploadFile(String fileName, InputStream inputStream) {
        // 获取阿里云存储相关变量
        String endpoint = OssConstants.END_POINT;
        String accessKeyId = OssConstants.ACCESS_KEY_ID;
        String accessKeySecret = OssConstants.ACCESS_KEY_SECRET;
        String bucketName = OssConstants.BUCKET_NAME;
        // 创建OSS实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象
        String filePath = PathUtils.generateFilePath(fileName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream);
        // 上传图片
        ossClient.putObject(putObjectRequest);
        // 关闭ossClient
        ossClient.shutdown();
        // 把上传后的文件路径返回
        return "https://" + bucketName + "." + endpoint + "/" + filePath;
    }

    /**
     * 判断文件名是否为图片
     *
     * @param fileName 文件名
     * @return 是否为图片的后缀
     */
    private static boolean fileIsImage(String fileName) {
        // 获取后缀并转为小写
        String fileSuffix = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        for (String suffix : imageSuffixList) {
            if (fileSuffix.equals(suffix)) {
                return true;
            }
        }
        return false;
    }
}
