package cn.nyse.service.impl;

import cn.nyse.entity.SysArea;
import cn.nyse.entity.SysOffice;
import cn.nyse.listener.SysAreaListener;
import cn.nyse.mapper.SysAreaMapper;
import cn.nyse.service.SysAreaService;
import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Service
@Transactional
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea> implements SysAreaService {

    @Autowired
    SysAreaMapper sysAreaMapper;//与父实现抽象类的属性Mapper 是同一个对象


    @Override
    public PageInfo <SysArea> selectPage(int pageNum, int pageSize, Map <String, Object> params) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo <SysArea>(sysAreaMapper.selectPage(params));
    }

    @Override
    public SysArea selectByAid(long id) {
        return sysAreaMapper.selectByAid(id);
    }

    @Override
    public SysArea selectParent(long id) {
        return sysAreaMapper.selectParent(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysArea sysArea) {
        int result = 0;
        try {
            result += sysAreaMapper.updateByPrimaryKeySelective(sysArea);
        } catch (Exception e) {
            throw new RuntimeException("更新父区域失败");
        }

        try {
            if (!sysArea.getParentIds().equals(sysArea.getOldParentIds())) {
                result += sysAreaMapper.updateSubAreas(sysArea);
            }
        } catch (Exception e) {
            throw new RuntimeException("更新子区域失败");
        }


        return result;
    }

    @Override
    public void upload(InputStream is){
        EasyExcel.read(is,SysArea.class,new SysAreaListener(sysAreaMapper)).sheet().doRead();
    }


    @Override
    public void download(OutputStream os){
        EasyExcel.write(os,SysArea.class).sheet().doWrite(sysAreaMapper.selectAll());
    }
}
