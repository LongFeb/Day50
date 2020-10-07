import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.controller.SysLogController;
import cn.nyse.entity.AppVersion;
import cn.nyse.entity.Result;
import cn.nyse.entity.SysLog;
import cn.nyse.mapper.AppVersionMapper;
import cn.nyse.mapper.SysLogMapper;
import cn.nyse.service.AppVersionService;
import cn.nyse.service.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
@WebAppConfiguration
public class TestSysLog {


    @Autowired
    SysLogMapper sysLogMapper;

    @Autowired
    SysLogService sysLogService;



    @Test
    public void testLogInsert(){
        SysLog sysLog = sysLogMapper.selectByPrimaryKey(40);
        System.out.println(sysLog);
    }

}
