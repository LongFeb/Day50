package cn.nyse.service;

import cn.nyse.entity.AppVersion;
import cn.nyse.entity.SysArea;
import cn.nyse.entity.SysOffice;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface SysOfficeService extends BaseService<SysOffice> {
    SysOffice updateByParamarySelective(SysOffice sysOffice);

    PageInfo<SysOffice> selectPage(int pageNum, int pageSize,Map <String, Object> params);

    List<SysOffice> selectByRid(long rid);

    SysOffice selectByOid(long oid);
}
