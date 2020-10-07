package cn.nyse.service;

import cn.nyse.entity.Demand;
import com.github.pagehelper.PageInfo;


public interface DemandService extends BaseService<Demand> {

    PageInfo<Demand> selectPage(int pageNum, int pageSize);
}
