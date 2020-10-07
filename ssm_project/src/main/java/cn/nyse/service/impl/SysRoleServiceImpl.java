package cn.nyse.service.impl;

import cn.nyse.entity.SysOffice;
import cn.nyse.entity.SysResource;
import cn.nyse.entity.SysRole ;
import cn.nyse.mapper.SysRoleMapper;
import cn.nyse.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;
    

    @Override
    public PageInfo<SysRole> selectPage(int pageNum, int pageSize, Map<String,Object> params) {
        PageHelper.startPage(pageNum, pageSize);
        List <SysRole> list = sysRoleMapper.selectPage(params);
        PageInfo <SysRole> pageInfo = new PageInfo <>(list);
        return pageInfo;
    }

    @Override
    public int deleteBatch(long rid, long[] ids) {
        return sysRoleMapper.deleteBatch(rid,ids);
    }

    @Override
    public int insertBatch(long rid, List<Long> cids) {
        return sysRoleMapper.insertBatch(rid,cids);
    }

    @Override
    public  SysRole selectByPrimaryKey(Object rid){
        return sysRoleMapper.selectByRid(rid);
    }

    @Override
    public int updateByPrimaryKeySelective(String username, Map <String, Object> params){
        int result=0;
        //更新role信息
        SysRole sysRole = new SysRole();
        sysRole.setUpdateDate(new Date());
        sysRole.setUpdateBy(username);
        if(params.containsKey("id")){
            int id=(int)params.get("id");
            sysRole.setId((long)id);
        }
        if(params.containsKey("officeId")){
            int officeId=(int)params.get("officeId");
            sysRole.setOfficeId((long)officeId);
        }
        if(params.containsKey("name")){
            sysRole.setName((String) params.get("name"));
        }
        if(params.containsKey("dataScope")){
            sysRole.setDataScope((String) params.get("dataScope"));
        }
        result+=sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        //更新资源授权信息
        List<SysResource> resources=null;
        if(params.containsKey("resources")){
            resources= (List <SysResource>) params.get("resources");
        }
        List<SysResource> oldResources = null;
        if (params.containsKey("oldResources")) {
            oldResources = (List<SysResource>) params.get("oldResources");//旧资源
        }
        //新旧资源一致
        if(resources.size()==oldResources.size()&&resources.containsAll(oldResources)){

        }else {
            sysRoleMapper.deleteByRid(sysRole.getId());
            result+=sysRoleMapper.insertRoleResources(sysRole.getId(),resources);
        }


        //更新公司授权信息
        List<SysOffice> offices = null;
        if (params.containsKey("offices")) {
            offices = (List<SysOffice>) params.get("offices");//新资源
        }
        List<SysOffice> oldOffices = null;
        if (params.containsKey("oldOffices")) {
            oldOffices = (List<SysOffice>) params.get("oldOffices");//旧资源
        }
        if(offices==null&&oldOffices==null){
            result += 0;
        }else if(offices!=null&&oldOffices!=null&&offices.size()==oldOffices.size()&&offices.containsAll(oldOffices)){
            result += 0;
        }else {
            sysRoleMapper.deleteOfficeByRid(sysRole.getId());
            result +=sysRoleMapper.insertRoleOffices(sysRole.getId(),offices);
        }
        return result;
    }

}
