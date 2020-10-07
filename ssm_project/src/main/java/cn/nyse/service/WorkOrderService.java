package cn.nyse.service;

import cn.nyse.entity. WorkOrder;
import com.github.pagehelper.PageInfo;

import java.util.Map;


public interface WorkOrderService extends BaseService<WorkOrder> {

    PageInfo<WorkOrder> selectPage(int pageNum, int pageSize, Map <String, Object> params);

    Map<String,Object> selectDetail(long id);
}
