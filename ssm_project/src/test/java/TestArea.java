import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.entity.SysArea;
import cn.nyse.mapper.SysAreaMapper;

import cn.nyse.service.SysAreaService;
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
public class TestArea {
    @Autowired
    SysAreaMapper sysAreaMapper;

    @Autowired
    SysAreaService sysAreaService;
    

    @Test
    public void testSelectPage(){
        Map<String, Object> map = new HashMap <>();
        map.put("id",3);

        PageInfo <SysArea> pageInfo =  sysAreaService.selectPage(1, 3, map);
        System.out.println(pageInfo.getList());

    }

    @Test
    public void testInsertBatch(){
        List<SysArea> sysAreas = sysAreaMapper.selectAll();

        int i = sysAreaMapper.insertBatch(sysAreas);
    }

}
