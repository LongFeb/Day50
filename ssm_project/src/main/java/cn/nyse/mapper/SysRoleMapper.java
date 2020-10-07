package cn.nyse.mapper;

import cn.nyse.entity.SysOffice;
import cn.nyse.entity.SysResource;
import cn.nyse.entity.SysRole;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends Mapper<SysRole> {

    @SelectProvider(type = SysRoleSqlProvider.class,method = "selectPage")
    List<SysRole> selectPage(Map<String,Object> params);

    @DeleteProvider(type = SysRoleSqlProvider.class,method = "deleteBatch")
    int deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids);

    @InsertProvider(type = SysRoleSqlProvider.class,method = "insertBatch")
    int insertBatch(@Param("rid") long rid, @Param("cids") List<Long> cids);

    @Select("SELECT " +
            " sro.*, " +
            " sof.`name` office_name  " +
            "FROM " +
            " sys_role sro, " +
            " sys_office sof  " +
            "WHERE " +
            " sro.del_flag = 0  " +
            "AND sro.id = #{rid}" +
            " AND sro.office_id = sof.id ")
    SysRole selectByRid(Object rid);

    @Delete("delete from sys_role_resource where role_id=#{rid}")
    int deleteByRid(Long rid);

    @InsertProvider(type=SysRoleSqlProvider.class,method = "insertRoleResources")
    int insertRoleResources(@Param("rid") long rid,@Param("resources") List<SysResource> resources);

    @Delete("delete from sys_role_office where role_id=#{rid}")
    int deleteOfficeByRid(long rid);

    @InsertProvider(type=SysRoleSqlProvider.class,method = "insertRoleOffices")
    int insertRoleOffices(@Param("rid") long rid,@Param("offices") List<SysOffice> offices);

}