package cn.nyse.service.impl;

import cn.nyse.entity.Examine;
import cn.nyse.mapper.ExamineMapper;
import cn.nyse.service.ExamineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExamineServiceImpl extends BaseServiceImpl<Examine> implements ExamineService {

    @Autowired
    ExamineMapper examineMapper;



    @Override
    public PageInfo <Examine> selectPage(int pageNum, int pageSize, Map<String,Object> params ) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo <Examine>(examineMapper.selectPage(params));
    }

}
