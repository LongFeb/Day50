package cn.nyse.mapper;

import cn.nyse.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {

    @Select("SELECT  " +
            "  su.id,  " +
            "  su.NAME   " +
            "FROM  " +
            "  sys_user_role sur,  " +
            "  sys_user su   " +
            "WHERE  " +
            "  su.del_flag = 0   " +
            "  AND sur.role_id = #{rid}  " +
            "  AND sur.user_id = su.id")
    List<SysUser> selectByRid(long rid);

    @Select("SELECT name, " +
            " id  " +
            "FROM " +
            " sys_user su  " +
            "WHERE " +
            " su.office_id = 56  " +
            " AND NOT EXISTS ( SELECT 1 FROM sys_user_role sur WHERE sur.role_id = 2 AND su.id = sur.user_id )")
    List<SysUser> selectNoRole(@Param("rid") long rid, @Param("oid") long oid);
}