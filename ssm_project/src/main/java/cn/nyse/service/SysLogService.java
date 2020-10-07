package cn.nyse.service;

import cn.nyse.entity.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService extends BaseService<SysLog> {
    PageInfo<SysLog> selectPage(int pageNum, int pageSize);
}
