package cn.nyse.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class WorkOrderSqlProvider {
    public String selectPage(Map<String,Object> params){
        return new SQL(){{
            SELECT("wo.*, " +
                    " cu.NAME create_user, " +
                    " tu.NAME transport_user, " +
                    " ru.NAME recipient_user, " +
                    " co.`name` create_office ");
            FROM("work_order AS wo");
            LEFT_OUTER_JOIN("sys_user AS cu ON wo.create_user_id = cu.id " +
                    " LEFT JOIN sys_user AS tu ON wo.transport_user_id = tu.id " +
                    " LEFT JOIN sys_user AS ru ON wo.recipient_user_id = ru.id " +
                    " LEFT JOIN sys_office AS co ON cu.company_id = co.id " +
                    " LEFT JOIN sys_office AS `to` ON tu.company_id = `to`.id " +
                    " LEFT JOIN sys_office AS ro ON ru.company_id = ro.id ");
            WHERE("wo.del_flag = 0");
            if (params.containsKey("status")&&!StringUtils.isEmpty(params.get("status"))){
                WHERE("wo.STATUS = #{status}");
            }
            if (params.containsKey("startDate")&&!StringUtils.isEmpty(params.get("startDate"))){
                WHERE(" wo.create_date >=  #{startDate}");
            }
            if (params.containsKey("endDate")&&!StringUtils.isEmpty(params.get("endDate"))){
                WHERE(" wo.create_date <=  #{endDate}");
            }
            if (params.containsKey("oid")&&!StringUtils.isEmpty(params.get("oid"))){
                WHERE("( co.id = #{oid} OR TO.id = #{oid} OR ro.id = #{oid} )");
            }
        }}.toString();
    }
}
