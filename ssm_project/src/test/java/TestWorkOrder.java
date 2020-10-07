import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.entity. WorkOrder;
import cn.nyse.mapper. WorkOrderMapper;
import cn.nyse.service. WorkOrderService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
@WebAppConfiguration
public class TestWorkOrder {
    @Autowired
     WorkOrderMapper  workOrderMapper;

    @Autowired
     WorkOrderService  workOrderService;
    

    @Test
    public void testSelectPage(){
        Map<String, Object> map = new HashMap <>();
        map.put("status",2);
        map.put("startDate","2016-09-01");
        map.put("endDate","2016-09-30");
        map.put("oid",56);
        PageInfo < WorkOrder> pageInfo =  workOrderService.selectPage(1, 3, map);
        System.out.println(pageInfo.getList());

    }

    @Test
    public void testSelectDetail(){
        Map <String, Object> map = workOrderService.selectDetail(11);
        System.out.println(map);
    }



}
