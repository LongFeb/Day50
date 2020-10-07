package cn.nyse.service;

import cn.nyse.entity.AppVersion;
import com.github.pagehelper.PageInfo;


public interface AppVersionService extends BaseService<AppVersion> {

    PageInfo<AppVersion> selectPage(int pageNum, int pageSize);
}
