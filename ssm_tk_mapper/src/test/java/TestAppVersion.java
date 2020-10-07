import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.entity.AppVersion;
import cn.nyse.mapper.AppVersionMapper;
import cn.nyse.service.AppVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class TestAppVersion {

    @Autowired
    AppVersionMapper versionMapper;//注入tkmapper生成的代理子类

    @Autowired
    AppVersionService appVersionService;

    @Test
    public  void testSelect(){
        //根据继承关系树可以 查看到  其父接口 SelectAllMapper  提供了调用动态sql返回执行sql语句字符串
        List<AppVersion> appVersions = versionMapper.selectAll();
        for (AppVersion appVersion : appVersions) {
            System.out.println(appVersion);
        }
        System.out.println("主键查询......");
        //必须指定主键注解配置才能正常使用
        AppVersion appVersion = versionMapper.selectByPrimaryKey(1);
        System.out.println(appVersion);


    }

    @Test
    public void testInsert(){
        AppVersion appVersion = versionMapper.selectByPrimaryKey(1);
        appVersion.setId(null);

        int i = versionMapper.insertSelective(appVersion);
        System.out.println(i);
    }

    //动态查询sql测试
    @Test
    public void testSelect2(){
        AppVersion appVersion = new AppVersion();
        appVersion.setDelFlag("0");
        List<AppVersion> list = versionMapper.select(appVersion);
        for (AppVersion version : list) {
            System.out.println(version);
        }
    }


    @Test
    public void test(){
        AppVersion appVersion = appVersionService.selectByPrimaryKey(1);
        System.out.println(appVersion);
    }

}
