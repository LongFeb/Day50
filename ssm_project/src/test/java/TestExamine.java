import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.entity.Examine;
import cn.nyse.mapper.ExamineMapper;
import cn.nyse.service.ExamineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
@WebAppConfiguration
public class TestExamine {
    @Autowired
    ExamineMapper examineMapper;

    @Autowired
    ExamineService examineService;
    

    @Test
    public void testSelectPage(){
        Map<String, Object> map = new HashMap <>();
        map.put("oid",56);
        map.put("type",1);
        map.put("userName","员");
        PageInfo <Examine> pageInfo = examineService.selectPage(1, 3, map);
        System.out.println(pageInfo.getList());

    }



}
