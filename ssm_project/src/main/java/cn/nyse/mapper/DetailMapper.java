package cn.nyse.mapper;

import cn.nyse.entity.Detail;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface DetailMapper extends Mapper<Detail> {

    @Select("SELECT " +
            " de.*, " +
            " wt.`name` waste_type_name, " +
            " wt.`code` waste_type_code, " +
            " wa.`code` waste_code  " +
            "FROM " +
            " detail de, " +
            " waste wa, " +
            " waste_type wt  " +
            "WHERE " +
            " de.work_order_id = #{id}  " +
            " AND de.del_flag = 0  " +
            " AND de.waste_type_id = wt.id  " +
            " AND de.waste_id = wa.id")
    List<Map<String,Object>> selectById(long id);
}