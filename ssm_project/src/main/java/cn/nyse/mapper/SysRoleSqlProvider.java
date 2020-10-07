package cn.nyse.mapper;


import cn.nyse.entity.SysOffice;
import cn.nyse.entity.SysResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class SysRoleSqlProvider {
    public String selectPage(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " sro.*, " +
                " sof.NAME office_name  " +
                "FROM " +
                " sys_role sro, " +
                " sys_office sof  " +
                "WHERE " +
                " sro.del_flag = 0 ");
        if(params.containsKey("dataScore")&&!StringUtils.isEmpty(params.get("dataScore"))){
            sb.append("AND sro.data_scope = #{dataScore} ");
        }

        if(params.containsKey("officeId")&&!StringUtils.isEmpty(params.get("officeId"))){
            sb.append("AND sro.office_id = #{officeId} ");
        }

        if(params.containsKey("name")&&!StringUtils.isEmpty(params.get("name"))){
            sb.append("AND sro.`name` LIKE CONCAT( '%', #{name}, '%' )  ");
        }

        if(params.containsKey("remarks")&&!StringUtils.isEmpty(params.get("remarks"))){
            sb.append("AND sro.remarks LIKE CONCAT( '%', #{remarks}, '%' ) ");
        }

        sb.append("AND sro.office_id = sof.id");

        return sb.toString();
    }

    public String  deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM sys_user_role WHERE role_id = #{rid} AND id IN (");
        for(int i=0;i<ids.length;i++){
            sb.append("#{ids["+i+"]},");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

    public String  insertBatch(@Param("rid") long rid, @Param("cids") List<Long> cids){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_user_role`(`role_id`, `user_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");
        for(int i=0;i<cids.size();i++){
            sb.append("(#{rids},#{cids["+i+"]},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String insertRoleResources(@Param("rid") long rid,@Param("resources") List<SysResource> resources){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_role_resource`(`role_id`, `resource_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < resources.size(); i++) {
            sb.append("(#{rid},#{resources["+i+"].id},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String insertRoleOffices(@Param("rid") long rid,@Param("offices") List<SysOffice> offices){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `sys_role_office`(`role_id`, `office_id`, `create_by`, `create_date`, `update_by`, `update_date`, `del_flag`) VALUES ");

        for (int i = 0; i < offices.size(); i++) {
            sb.append("(#{rid},#{offices["+i+"].id},null,now(),null,now(),0),");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}
