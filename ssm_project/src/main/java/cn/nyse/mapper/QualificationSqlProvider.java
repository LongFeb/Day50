package cn.nyse.mapper;

import cn.nyse.entity.QualificationCondition;
import org.springframework.util.StringUtils;

public class QualificationSqlProvider {
    //生成sql语句
    public String selectPage(QualificationCondition condition){

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                "  qu.*, " +
                "  uu.`name` update_name, " +
                "  cu.`name` check_name  " +
                "FROM " +
                "  qualification qu " +
                "  LEFT JOIN sys_user uu ON qu.upload_user_id = uu.id " +
                "  LEFT JOIN sys_user cu ON qu.check_user_id = cu.id  " +
                "WHERE " +
                "  qu.del_flag = '0' ");
        if (!StringUtils.isEmpty(condition.getStartDate())){
            sb.append("AND qu.create_date >=#{startDate}");
        }
        if (!StringUtils.isEmpty(condition.getEndDate())){
            sb.append("AND qu.create_date <= #{endDate}");
        }
        if (!StringUtils.isEmpty(condition.getCheck())){
            sb.append("AND qu.`check` = #{check} ");
        }
        if (!StringUtils.isEmpty(condition.getType())){
            sb.append("AND qu.type = #{type}");
        }
        return sb.toString();
    }
}
