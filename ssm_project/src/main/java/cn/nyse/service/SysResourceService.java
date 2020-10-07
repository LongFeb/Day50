package cn.nyse.service;

import cn.nyse.entity.SysResource;

import java.util.List;


public interface SysResourceService extends BaseService<SysResource> {

    List<SysResource> selectByRid(long rid);

    List<SysResource> selectByUid(long uid);

    List<SysResource> selectResources();
}
