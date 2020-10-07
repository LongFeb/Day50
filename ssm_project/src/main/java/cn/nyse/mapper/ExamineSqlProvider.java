package cn.nyse.mapper;

import cn.nyse.entity.Examine;
import cn.nyse.entity.QualificationCondition;
import org.springframework.util.StringUtils;

import java.util.Map;

public class ExamineSqlProvider {
    //生成sql语句
    public String selectPage(Map<String,Object> params){

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " ex.*, " +
                " su.NAME user_name, " +
                " so.NAME offic_name  " +
                "FROM " +
                " examine ex, " +
                " sys_user su, " +
                " sys_office so  " +
                "WHERE " +
                " ex.del_flag = 0 ");
        if(!params.isEmpty()){
            if(params.containsKey("type")&&!StringUtils.isEmpty(params.get("type"))){
                sb.append(" AND ex.type = #{type} ");
            }
            if(params.containsKey("oid")&&!StringUtils.isEmpty(params.get("oid"))){
                sb.append(" AND so.id = #{oid} ");
            }
            if(params.containsKey("userName")&&!StringUtils.isEmpty(params.get("userName"))){
                sb.append(" AND su.`name` LIKE concat( '%', #{userName}, '%' ) ");
            }
        }
        sb.append("AND ex.examine_user_id = su.id AND su.office_id = so.id");

        return sb.toString();
    }
}
