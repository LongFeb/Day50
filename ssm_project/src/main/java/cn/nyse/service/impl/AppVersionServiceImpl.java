package cn.nyse.service.impl;

import cn.nyse.entity.AppVersion;
import cn.nyse.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersion> implements AppVersionService {

    @Override
    public PageInfo<AppVersion> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        AppVersion appVersion=new AppVersion();
        appVersion.setDelFlag("0");
        return new PageInfo <AppVersion>(mapper.select(appVersion));
    }
}
