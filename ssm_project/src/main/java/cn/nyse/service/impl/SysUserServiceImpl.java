package cn.nyse.service.impl;

import cn.nyse.entity.SysUser;
import cn.nyse.mapper.SysUserMapper;
import cn.nyse.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public PageInfo<SysUser> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        SysUser sysUser=new SysUser();
        sysUser.setDelFlag("0");
        return new PageInfo <SysUser>(sysUserMapper.select(sysUser));
    }

    @Override
    public List<SysUser> selectByRid(long rid) {
        return sysUserMapper.selectByRid(rid);
    }

    @Override
    public List <SysUser> selectNoRole(long rid, long oid) {
        return sysUserMapper.selectNoRole(rid,oid);
    }
}
