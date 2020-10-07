package cn.nyse.mapper;



import org.springframework.util.StringUtils;

import java.util.Map;

public class SysOfficeSqlProvider {
    public String selectPage(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " sof.*, " +
                " sar.NAME areaName  " +
                "FROM " +
                " sys_office sof, " +
                " sys_area sar  " +
                "WHERE " +
                " sof.del_flag = 0  " );
        if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
            sb.append("AND sof.`name` LIKE CONCAT( '%', #{name}, '%' )  ");
        }
        sb.append(" AND sof.area_id = sar.id");
        return sb.toString();
    }
}
