package cn.nyse.mapper;

import cn.nyse.entity.SysArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class SysAreaSqlProvider {
    public String selectPage(Map<String,Object> params){
        return new SQL(){{
            SELECT("parent.`name` parent_name, sub.* ");
            FROM("sys_area parent, sys_area sub ");
            WHERE("sub.del_flag = 0 ");
            if (params.containsKey("id")&&!StringUtils.isEmpty(params.get("id"))){
                WHERE("( sub.parent_ids LIKE concat( '%,', #{id}, '%,') OR sub.id = #{id} )");
            }else if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
                WHERE("sub.`name` like concat('%',#{name},'%')");
            }
            WHERE("sub.parent_id = parent.id");
        }}.toString();
    }

    public String insertBatch(@Param("areas") List<SysArea> areas){
        return new SQL(){{
            INSERT_INTO("sys_area");
            INTO_COLUMNS("  `parent_id`, `parent_ids`, `code`, `name`, `type`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `icon` ");
            for (int i = 0; i < areas.size(); i++) {
                INTO_VALUES(" #{areas["+i+"].parentId}, #{areas["+i+"].parentIds}, #{areas["+i+"].code}, #{areas["+i+"].name}, #{areas["+i+"].type}, #{areas["+i+"].createBy}, #{areas["+i+"].createDate}, #{areas["+i+"].updateBy}, #{areas["+i+"].updateDate}, #{areas["+i+"].remarks}, #{areas["+i+"].delFlag}, #{areas["+i+"].icon}");
                ADD_ROW();//设置添加一条记录标记，自动处理(),
            }
        }}.toString();
    }
}
