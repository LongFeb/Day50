package cn.nyse.mapper;

import cn.nyse.entity.SysArea;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysAreaMapper extends Mapper<SysArea> {
    @SelectProvider(type = SysAreaSqlProvider.class,method = "selectPage")
    List<SysArea> selectPage(Map<String,Object> params);

    @Select("SELECT " +
            " parent.NAME parent_name, " +
            " sub.*  " +
            "FROM " +
            " sys_area parent, " +
            " sys_area sub  " +
            "WHERE " +
            " sub.del_flag=0  " +
            " and sub.id=#{id}"+
            " and " +
            " parent.id=sub.parent_id"
    )
    SysArea selectByAid(long id);

    @Update("UPDATE sys_area " +
            "SET parent_ids = REPLACE ( parent_ids, #{oldParentIds}, #{parentIds} ) " +
            "WHERE " +
            " FIND_IN_SET( #{id}, parent_ids )")
    int updateSubAreas(SysArea parent);

    @Select("SELECT " +
            " sub.*  " +
            "FROM " +
            " sys_area sub, " +
            " sys_area parent  " +
            "WHERE " +
            " sub.del_flag = 0  " +
            " AND sub.parent_id = parent.id  " +
            " AND sub.parent_ids NOT LIKE concat( '%,', #{id}, '%,' )")
    SysArea selectParent(long id);

    @InsertProvider(type = SysAreaSqlProvider.class,method = "insertBatch")
    int insertBatch(@Param("areas")List<SysArea> areas);
}