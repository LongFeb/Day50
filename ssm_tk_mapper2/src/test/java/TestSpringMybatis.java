import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.entity.AppVersion;
import cn.nyse.mapper.AppVersionMapper;
import cn.nyse.service.AppVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class TestSpringMybatis {
    @Autowired
    DataSource  dataSource;

    @Autowired
    AppVersionMapper appVersionMapper;

    @Autowired
    AppVersionService appVersionService;

    @Test
    public void testConn() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void  test(){
        AppVersion appVersion = appVersionMapper.selectByPrimaryKey(1);
        System.out.println(appVersion);
    }

    @Test
    public void test2(){
        AppVersion appVersion = appVersionService.selectByPrimaryKey(1);
        System.out.println(appVersion);
    }

}
