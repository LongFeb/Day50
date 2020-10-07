import cn.nyse.config.SpringMybatisConfig;
import cn.nyse.controller.QualificationController;
import cn.nyse.entity.AppVersion;
import cn.nyse.entity.Qualification;
import cn.nyse.entity.Result;
import cn.nyse.mapper.QualificationMapper;
import cn.nyse.service.QualificationService;
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
public class TestQualification {
    @Autowired
    QualificationMapper qualificationMapper;

    @Autowired
    QualificationService qualificationService;

    @Autowired
    QualificationController qualificationController;

    @Test
    public void testSelectPage(){
        //开启分页
        PageHelper.startPage(1,5);// 页码,每页显示条数
        List<Qualification> qualifications = qualificationMapper.selectAll();
        for (Qualification qualification : qualifications) {
            System.out.println(qualification);
        }
        PageInfo<Qualification> pageInfo = new PageInfo<>(qualifications);//分页信息对象
        //System.out.println(pageInfo);
    }



}
