package cn.nyse.service;

import cn.nyse.entity.SysArea;
import com.github.pagehelper.PageInfo;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Map;


public interface SysAreaService extends BaseService<SysArea> {

    PageInfo<SysArea> selectPage(int pageNum, int pageSize, Map <String, Object> params);

    SysArea selectByAid(long id);

    SysArea selectParent(long id);

    void upload(InputStream is);

    void download(OutputStream os);


}
