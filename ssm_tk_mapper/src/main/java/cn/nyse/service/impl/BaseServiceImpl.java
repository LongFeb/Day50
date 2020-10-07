package cn.nyse.service.impl;

import cn.nyse.service.BaseService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    Mapper<T> mapper;

    public int deleteByPrimaryKey(Object o) {
        return mapper .deleteByPrimaryKey(o);
    }


    public int delete(T t) {
        return mapper .delete(t);
    }


    public int insert(T t) {
        return mapper .insert(t);
    }


    public int insertSelective(T t) {
        return mapper .insertSelective(t);
    }


    public boolean existsWithPrimaryKey(Object o) {
        return mapper .existsWithPrimaryKey(o);
    }


    public List<T> selectAll() {
        return mapper .selectAll();
    }


    public T selectByPrimaryKey(Object o) {
        return mapper .selectByPrimaryKey(o);
    }


    public int selectCount(T t) {
        return mapper .selectCount(t);
    }


    public List<T> select(T t) {
        return mapper .select(t);
    }


    public T selectOne(T t) {
        return mapper .selectOne(t);
    }


    public int updateByPrimaryKey(T t) {
        return mapper .updateByPrimaryKey(t);
    }


    public int updateByPrimaryKeySelective(T t) {
        return mapper .updateByPrimaryKeySelective(t);
    }


    public int deleteByExample(Object o) {
        return mapper .deleteByExample(o);
    }


    public List<T> selectByExample(Object o) {
        return mapper .selectByExample(o);
    }


    public int selectCountByExample(Object o) {
        return mapper .selectCountByExample(o);
    }


    public T selectOneByExample(Object o) {
        return mapper .selectOneByExample(o);
    }


    public int updateByExample(T t, Object o) {
        return mapper .updateByExample(t, o);
    }


    public int updateByExampleSelective(T t, Object o) {
        return mapper .updateByExampleSelective(t, o);
    }


    public List<T> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return mapper .selectByExampleAndRowBounds(o, rowBounds);
    }


    public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
        return mapper .selectByRowBounds(t, rowBounds);
    }
}
