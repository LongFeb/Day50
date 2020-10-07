package cn.nyse.service.impl;

import cn.nyse.entity. WorkOrder;
import cn.nyse.mapper.DetailMapper;
import cn.nyse.mapper.TransferMapper;
import cn.nyse.mapper. WorkOrderMapper;
import cn.nyse.service. WorkOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkOrderServiceImpl extends BaseServiceImpl< WorkOrder> implements  WorkOrderService {

    @Autowired
     WorkOrderMapper workOrderMapper;

    @Autowired
    DetailMapper detailMapper;

    @Autowired
    TransferMapper transferMapper;

    @Override
    public PageInfo < WorkOrder> selectPage(int pageNum, int pageSize, Map<String,Object> params ) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo < WorkOrder>(workOrderMapper.selectPage(params));
    }

    @Override
    public  Map<String,Object> selectDetail(long id){
        Map <String, Object> workOrder = workOrderMapper.selectById(id);
        List <Map <String, Object>> details = detailMapper.selectById(id);
        List <Map <String, Object>> transfers = transferMapper.selectById(id);
        workOrder.put("details",details);
        workOrder.put("transfers",transfers);
        return workOrder;
    }

}
