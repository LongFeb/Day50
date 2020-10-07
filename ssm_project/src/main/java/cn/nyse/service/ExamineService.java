package cn.nyse.service;

import cn.nyse.entity.Examine;
import com.github.pagehelper.PageInfo;

import java.util.Map;


public interface ExamineService extends BaseService<Examine> {

    PageInfo <Examine> selectPage(int pageNum, int pageSize, Map<String,Object> params);
}
