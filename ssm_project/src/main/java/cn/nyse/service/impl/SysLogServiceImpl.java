package cn.nyse.service.impl;

import cn.nyse.entity.SysLog;
import cn.nyse.mapper.SysLogMapper;
import cn.nyse.service.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *spring事务策略：
 * 事务类型：
 * 只读事务：read-only:true  查询  不需要额外设置回滚空间等资源
 * 非只读事务：默认   增删改
 *
 *传播行为：
 *Propagation  事务传播行为
 * Propagation.REQUIRED  默认 必须有事务 有则加入事务，无则创建新事务
 *
 * 隔离级别：
 * Isolation:
 * default  数据库默认隔离级别 90%
 *
 * rollbackFor:设置发生哪些异常进行回滚，默认只对非检查异常进行回滚
 * */
@Service
@Transactional(readOnly = false,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {
    @Autowired
    SysLogMapper sysLogMapper;

    @Override
    public int insertSelective(SysLog sysLog) {
        return super.insertSelective(sysLog);
    }

    @Override
    public PageInfo<SysLog> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo <SysLog>(mapper.selectAll());
    }
}
