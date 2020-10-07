package cn.nyse.service.impl;

import cn.nyse.entity.Qualification;
import cn.nyse.entity.QualificationCondition;
import cn.nyse.entity.SysUser;
import cn.nyse.mapper.QualificationMapper;
import cn.nyse.mapper.SysUserMapper;
import cn.nyse.service.QualificationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QualificationServiceImpl extends BaseServiceImpl<Qualification> implements QualificationService {

    @Autowired
    QualificationMapper qualificationMapper;

    @Autowired
    SysUserMapper userMapper;

    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    public PageInfo<Qualification> selectPage(int pageNum, int pageSize, QualificationCondition condition) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo <Qualification>(qualificationMapper.selectPage(condition));
    }

    @Override
    public Qualification selectByPrimaryKey(Object o) {
        Qualification qualification = super.selectByPrimaryKey(o);
        SysUser sysUser = userMapper.selectByPrimaryKey(qualification.getUploadUserId());
        qualification.setAddress(uploadPath+sysUser.getOfficeId()+"/"+qualification.getAddress());
        return qualification;
    }
}
