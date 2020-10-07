package cn.nyse.service;

import cn.nyse.entity.Statute;
import com.github.pagehelper.PageInfo;

import java.util.Map;


public interface StatuteService extends BaseService<Statute> {

    PageInfo<Statute> selectPage(int pageNum, int pageSize,Integer type);

}
