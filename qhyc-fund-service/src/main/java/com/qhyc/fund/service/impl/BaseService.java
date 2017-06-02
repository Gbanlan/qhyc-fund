package com.qhyc.fund.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.qhyc.fund.entity.CutPageBean;


@Service
public class BaseService<T> {
	/**
     * SqlSesion模板类
     */
    @Resource
    protected SqlSessionTemplate sqlSession;

    /**
     * @Description 所有的查询分页对象的超类方法
     * @param pageNO 页码
     * @param pageSize 每页显示记录数
     * @param paramMap 需要查询的字段集合
     * @param listSql 查询某页记录的SQL语句
     * @param countSql 查询总记录数的SQL语句
     * @return 分页对象
     */
    public CutPageBean<T> cutpage(int pageNO, int pageSize,
            Map<String, Object> paramMap, String listSql, String countSql) {
        CutPageBean<T> cutBean = new CutPageBean<T>();
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        // 设置查询的起始页
        paramMap.put("start", (pageNO - 1) * pageSize);
        // 设置查询页开始向后的记录数
        paramMap.put("pageSize", pageSize);

        // 查询Mybatis配置文件下，命名空间内的查询结果
        List<T> list = this.sqlSession.selectList(listSql, paramMap);
        cutBean.setList(list);

        // 设置总记录数
        int count = this.sqlSession.selectOne(countSql, paramMap);
        cutBean.setCount(count);

        // 设置总页数
        cutBean.setTotalPage(count / pageSize);
        if (count % pageSize != 0) {
            cutBean.setTotalPage(count / pageSize + 1);
        }
        return cutBean;
    }
}
