package com.zh.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtils {
    private PathUtils() {
    }

    public static String generateFilePath(String fileName) {
        // 根据日期生成新的文件路径
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = dateFormat.format(new Date());
        // uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 后缀和文件名一致
        String fileType = fileName.substring(fileName.lastIndexOf('.'));
        return datePath +
                '/' +
                uuid +
                fileType;
    }
}
