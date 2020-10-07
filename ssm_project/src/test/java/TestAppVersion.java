import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.controller.AppVersionController;
import cn.nyse.entity.AppVersion;
import cn.nyse.entity.Result;
import cn.nyse.mapper.AppVersionMapper;
import cn.nyse.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
@WebAppConfiguration
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
    public void testServiceSelectAll(){
        List<AppVersion> appVersions = appVersionService.selectAll();
        for (AppVersion appVersion : appVersions) {
            System.out.println(appVersion);
        }
    }

    /**
     * 分页原理：
     * 程序运行到开启分页逻辑处，基于拦截方式，拦截执行sql 自动生成查询总记录数和
     * 重新处理sql语句添加分页逻辑
     * 封装Page对象返回
     */
    @Test
    public void testSelectPage(){
        //开启分页
        PageHelper.startPage(1,5);// 页码,每页显示条数
        List<AppVersion> appVersions = versionMapper.selectAll();
        for (AppVersion appVersion : appVersions) {
            System.out.println(appVersion);
        }
        PageInfo<AppVersion> pageInfo = new PageInfo<>(appVersions);//分页信息对象
        //System.out.println(pageInfo);
    }
}
