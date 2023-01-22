package com.zh.blog.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 随机数生成器
 */
public class RandomUtils {
    private static final DecimalFormat codeFormat = new DecimalFormat("000000");

    /**
     * 获取6位的手机验证码
     *
     * @return 6位的手机验证码
     */
    public static String getSixBitRandom() {
        Random random = new Random();
        return codeFormat.format(random.nextInt(1000000));
    }
}
