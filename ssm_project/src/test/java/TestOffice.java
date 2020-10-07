import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.entity.SysOffice;
import cn.nyse.mapper.SysOfficeMapper;
import cn.nyse.service.SysOfficeService;
import cn.nyse.service.impl.SysOfficeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
@WebAppConfiguration
public class TestOffice {
    @Autowired
     SysOfficeMapper  sysOfficeMapper;

    @Autowired
     SysOfficeService  sysOfficeService;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void testConn(){
        System.out.println(redisConnectionFactory.getConnection().getNativeConnection());
    }

    @Test
    public void testRedisTemplate(){
        ValueOperations <Object, Object> ops = redisTemplate.opsForValue();
        ops.set("springDataRedis","hello ,springDataRedis");
        String springDataRedis = (String) ops.get("springDataRedis");
        System.out.println(springDataRedis);

        ops.set("offices",sysOfficeMapper.selectAll());
        System.out.println(ops.get("offices"));

    }

    @Test
    public void testSelect(){
        SysOffice sysOffice = new SysOffice();
        sysOffice.setDelFlag("0");
        //sysOffice.setCode("123");  测试不满足condition
        List <SysOffice> select = sysOfficeService.select(sysOffice);
        System.out.println(select);
    }
    
    @Test
    public void testUpdate(){
        SysOffice sysOffice = sysOfficeService.selectByPrimaryKey(2);
        sysOffice.setUpdateDate(new Date());
        SysOffice sysOffice1 = sysOfficeService.updateByParamarySelective(sysOffice);
        System.out.println(sysOffice1);

    }

    @Test
    public void testUpdateByPrimaryKey(){
        SysOffice sysOffice = sysOfficeService.selectByPrimaryKey(2);
        sysOffice.setUpdateDate(new Date());
        int i = sysOfficeService.updateByPrimaryKeySelective(sysOffice);
        System.out.println(i);
    }




}
