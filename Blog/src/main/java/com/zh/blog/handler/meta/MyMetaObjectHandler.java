package com.zh.blog.handler.meta;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zh.blog.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

// 自动填充时间数据
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            // 设置为-1L表示为自己创建,因为注册时还不存在id,所以设置为-1
            userId = -1L;
        }
        // fieldName为属性名
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId;
        // fieldName为属性名
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            userId = -1L;
        }
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }
}