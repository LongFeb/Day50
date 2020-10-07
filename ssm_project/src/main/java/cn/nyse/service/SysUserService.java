package cn.nyse.service;

import cn.nyse.entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface SysUserService extends BaseService<SysUser> {

    PageInfo<SysUser> selectPage(int pageNum, int pageSize);

    List<SysUser> selectByRid(long rid);

    List<SysUser> selectNoRole(long rid, long oid);
}
