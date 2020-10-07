package cn.nyse.mapper;

import cn.nyse.entity.WorkOrder;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WorkOrderMapper extends Mapper<WorkOrder> {
    @SelectProvider(type = WorkOrderSqlProvider.class,method = "selectPage")
    List<WorkOrder> selectPage(Map<String,Object> params);

    @Select("SELECT " +
            " wo.CODE, " +
            " wo.`status`, " +
            " cu.NAME create_user_name, " +
            " cu.phone create_user_phone, " +
            " tu.NAME transport_user_name, " +
            " tu.phone transport_user_phone, " +
            " ru.NAME recipient_user_name, " +
            " ru.phone recipient_user_phone, " +
            " co.`name` create_office_name, " +
            " `to`.`name` transport_office_name, " +
            " ro.`name` recipient_office_name  " +
            "FROM " +
            " work_order AS wo " +
            " LEFT JOIN sys_user AS cu ON wo.create_user_id = cu.id " +
            " LEFT JOIN sys_user AS tu ON wo.transport_user_id = tu.id " +
            " LEFT JOIN sys_user AS ru ON wo.recipient_user_id = ru.id " +
            " LEFT JOIN sys_office AS co ON cu.company_id = co.id " +
            " LEFT JOIN sys_office AS `to` ON tu.company_id = `to`.id " +
            " LEFT JOIN sys_office AS ro ON ru.company_id = ro.id  " +
            "WHERE " +
            " wo.del_flag = 0  " +
            " AND wo.id = #{id}")
    Map<String,Object> selectById(long id);
}