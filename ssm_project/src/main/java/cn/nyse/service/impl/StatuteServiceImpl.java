package cn.nyse.service.impl;

import cn.nyse.entity.Statute;
import cn.nyse.entity.WorkOrder;
import cn.nyse.mapper.StatuteMapper;
import cn.nyse.service.StatuteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class StatuteServiceImpl extends BaseServiceImpl< Statute> implements  StatuteService {

    @Autowired
     StatuteMapper statuteMapper;


    @Override
    public PageInfo <Statute> selectPage(int pageNum, int pageSize, Integer type) {
        PageHelper.startPage(pageNum,pageSize);
        Statute statute = new Statute();
        statute.setType(type);
        return new PageInfo<Statute>(statuteMapper.select(statute));
    }
}
