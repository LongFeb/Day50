package cn.nyse.service.impl;

import cn.nyse.entity.Demand;
import cn.nyse.service.DemandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DemandServiceImpl extends BaseServiceImpl<Demand> implements DemandService{

    @Override
    public PageInfo<Demand> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Demand demand = new Demand();
        demand.setDelFlag("0");
        return new PageInfo <Demand>(mapper.select(demand));
    }
}
