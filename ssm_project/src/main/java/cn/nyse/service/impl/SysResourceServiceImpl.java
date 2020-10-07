package cn.nyse.service.impl;

import cn.nyse.entity.SysResource;
import cn.nyse.mapper.SysResourceMapper;

import cn.nyse.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService {

    @Autowired
    SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> selectByRid(long rid){
        return sysResourceMapper.selectByRid(rid);
    }

    @Override
    public List<SysResource> selectByUid(long uid){
        List<SysResource> sysResources=sysResourceMapper.selectByUid(uid);
        return sysResources;
    }

    @Override
    @Cacheable(cacheNames = "resourceCache",key = "'cn.nyse.service.impl.SysResourceServiceImpl:selectResources'")
    public List<SysResource> selectResources(){
        return sysResourceMapper.selectResources();
    }



}
