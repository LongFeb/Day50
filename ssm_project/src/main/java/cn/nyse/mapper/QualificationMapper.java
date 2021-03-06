package cn.nyse.mapper;

import cn.nyse.entity.Qualification;
import cn.nyse.entity.QualificationCondition;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface QualificationMapper extends Mapper<Qualification> {

    @SelectProvider(type = QualificationSqlProvider.class,method = "selectPage")
    List<Qualification> selectPage(QualificationCondition condition);

}
