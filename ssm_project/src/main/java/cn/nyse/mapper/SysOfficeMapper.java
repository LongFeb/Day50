package cn.nyse.mapper;

import cn.nyse.entity.SysArea;
import cn.nyse.entity.SysOffice;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysOfficeMapper extends Mapper<SysOffice> {
    @Select("SELECT " +
            " *  " +
            "FROM " +
            " sys_role_office sro, " +
            " sys_office sof  " +
            "WHERE " +
            " sro.role_id = #{rid}  " +
            " AND sof.del_flag = 0  " +
            " AND sro.office_id = sof.id")
    List<SysOffice> selectByRid(long rid);

    @SelectProvider(type = SysOfficeSqlProvider.class,method = "selectPage")
    List<SysOffice> selectPage(Map<String, Object> params);

    @Select("SELECT " +
            " sof.*, " +
            " sar.NAME areaName  " +
            "FROM " +
            " sys_office sof, " +
            " sys_area sar  " +
            "WHERE " +
            " sof.del_flag = 0  " +
            " AND sof.id = #{oid}  " +
            " AND sof.area_id = sar.id")
    SysOffice selectByOid(long oid);
}