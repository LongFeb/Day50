package cn.nyse.service.impl;

import cn.nyse.entity.SysArea;
import cn.nyse.entity.SysOffice;
import cn.nyse.mapper.SysOfficeMapper;
import cn.nyse.service.SysOfficeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@CacheConfig(cacheNames = "officeCache")
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOffice> implements SysOfficeService {

    @Autowired
    SysOfficeMapper sysOfficeMapper;

//    @Autowired
//    RedisTemplate redisTemplate;

//    @Override
//    public List<SysOffice> select(SysOffice sysOffice) {
//        List<SysOffice> offices;
//        ValueOperations ops = redisTemplate.opsForValue();
//        String key="SysOfficeServiceImpl.select.sysOffice:"+sysOffice.toString();
//        Object obj = ops.get(key);
//
//        if(obj!=null&&obj instanceof List){
//            offices= (List <SysOffice>) obj;
//        }else {
//            offices=super.select(sysOffice);
//            ops.set(key,offices);
//        }
//        return offices;
//    }


    /**
     * Cacheable 设置使用缓存：
     * 1.如果当前缓存key存在value值在缓存数据库中，则直接返回缓存中的数据
     * 2.如果不存在，则执行当前方法，并将方法返回值放入缓存中
     * cacheNames：设置需要操作的缓存对象名，必须是缓存管理器中事先声明使用的缓存对象
     * key：设置缓存中的key的名字，使用springEL表达式：
     *      * 1.字符串通过''包括
     *      * 2.方法参数值通过#方法参数.属性名#sysOffice.id
     *      * 3.支持常见运算符
     * condition:指定条件放入缓存
     * @param sysOffice
     * @return
     */
    @Cacheable(/*cacheNames = "officeCache",*/
            key = "'SysOfficeServiceImpl.select.sysOffice:delFlag'+#sysOffice.delFlag",
            condition = "#sysOffice.delFlag!=null&&#sysOffice.id==null"+
                    "&&#sysOffice.parentId==null&&#sysOffice.address==null" +
                    "&&#sysOffice.areaId==null&&#sysOffice.code==null&&#sysOffice.createBy==null" +
                    "&&#sysOffice.createDate==null&&#sysOffice.email==null&&#sysOffice.fax==null" +
                    "&&#sysOffice.grade==null&&#sysOffice.icon==null&&#sysOffice.master==null")
    @Override
    public List <SysOffice> select(SysOffice sysOffice) {
        return super.select(sysOffice);
    }

    @Override
    @Cacheable(/*cacheNames = "officeCache",*/
            key = "'SysOfficeServiceImpl.selectByPrimaryKey.o:'+#o")
    public SysOffice selectByPrimaryKey(Object o) {
        return super.selectByPrimaryKey(o);
    }


    @Override
    @CachePut(/*cacheNames = "officeCache",*/
            key = "'SysOfficeServiceImpl.selectByPrimaryKey.o:'+#sysOffice.id")
    public SysOffice updateByParamarySelective(SysOffice sysOffice) {
        mapper.updateByPrimaryKeySelective(sysOffice);
        return mapper.selectByPrimaryKey(sysOffice.getId());
    }

    @Override
    public PageInfo<SysOffice> selectPage(int pageNum, int pageSize,Map <String, Object> params) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo <SysOffice>(sysOfficeMapper.selectPage(params));
    }


    @Override
    @CacheEvict(allEntries = true)
    public int updateByPrimaryKeySelective(SysOffice sysOffice) {
        return super.updateByPrimaryKeySelective(sysOffice);
    }

    @Override
    public List <SysOffice> selectByRid(long rid) {
        return sysOfficeMapper.selectByRid(rid);
    }

    @Override
    public SysOffice selectByOid(long oid) {
        return sysOfficeMapper.selectByOid(oid);
    }
}
