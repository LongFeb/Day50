package cn.nyse.service;

import cn.nyse.entity.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface SysRoleService extends BaseService<SysRole> {

    PageInfo<SysRole> selectPage(int pageNum, int pageSize, Map<String, Object> params);

    int deleteBatch(long rid, long[] ids);

    int insertBatch(long rid, List<Long> cids);

    int updateByPrimaryKeySelective(String username, Map <String, Object> params);
}
