package cn.nyse.listener;

import cn.nyse.entity.SysArea;
import cn.nyse.mapper.SysAreaMapper;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class SysAreaListener extends AnalysisEventListener<SysArea> {

    private List<SysArea> list=new ArrayList <>();
    private SysAreaMapper sysAreaMapper;

    public SysAreaListener() {
    }

    public SysAreaListener(SysAreaMapper sysAreaMapper) {
        this.sysAreaMapper = sysAreaMapper;
    }

    @Override
    public void invoke(SysArea data, AnalysisContext context) {
        list.add(data);
        if(list.size()==10){
            sysAreaMapper.insertBatch(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(list.size()>0){
            sysAreaMapper.insertBatch(list);
            list.clear();
        }
    }
}
