package cn.nyse.service;

import cn.nyse.entity.Qualification  ;
import cn.nyse.entity.QualificationCondition;
import com.github.pagehelper.PageInfo;


public interface QualificationService extends BaseService<Qualification> {

    PageInfo<Qualification> selectPage(int pageNum, int pageSize, QualificationCondition condition);
}
