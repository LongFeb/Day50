package cn.nyse.mapper;

import cn.nyse.entity.SysResource;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysResourceMapper extends Mapper<SysResource> {
    @Select("SELECT " +
            " sre.*  " +
            "FROM " +
            " sys_role_resource srr, " +
            " sys_resource sre  " +
            "WHERE " +
            " sre.del_flag = 0  " +
            " AND srr.role_id = #{rid}  " +
            " AND srr.resource_id = sre.id")
    List<SysResource> selectByRid(long rid);


    @Select("SELECT DISTINCT " +
            "sre.*  " +
            "FROM " +
            "sys_user_role sur, " +
            "sys_role_resource srr, " +
            "sys_resource sre  " +
            "WHERE " +
            "sur.user_id = #{uid}  " +
            "AND sre.del_flag = 0  " +
            "AND srr.del_flag = 0  " +
            "AND sre.type = 0 " +
            "AND sur.role_id = srr.role_id  " +
            "AND sre.id = srr.resource_id")
    List<SysResource> selectByUid(long uid);


    @Select("SELECT " +
            "*  " +
            "FROM " +
            "sys_resource  " +
            "WHERE " +
            "type = 0  " +
            "AND del_flag = 0  " +
            "AND url <> '' ")
    List<SysResource> selectResources();
}