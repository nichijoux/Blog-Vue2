package com.zh.blog.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zh.blog.domain.vo.PageVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    /**
     * 通过反射复制对象
     *
     * @param source 原始对象
     * @param clazz  目标类型
     * @param <O>    原始类型
     * @param <V>    目标类型泛型
     * @return 目标对象
     */
    public static <O, V> V copyBean(O source, Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    /**
     * 将O对象的数据列表封装为V的对象
     *
     * @param list  O对象的数据列表
     * @param clazz 一个字节码对象
     * @param <O>   原始类型
     * @param <V>   目标类型
     * @return 转换后V对象列表
     */
    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询复制分页对象
     *
     * @param page    分页对象
     * @param records 真正需要的数据
     * @return 分页VO
     */
    @SuppressWarnings("rawtypes")
    public static PageVO copyPageVO(Page page, List records) {
        PageVO pageVO = new PageVO();
        pageVO.setRecords(records);
        pageVO.setCurrent(page.getCurrent());
        pageVO.setTotal(page.getTotal());
        pageVO.setHasPrevious(page.hasPrevious());
        pageVO.setHasNext(page.hasNext());
        return pageVO;
    }
}
