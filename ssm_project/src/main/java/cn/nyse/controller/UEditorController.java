package cn.nyse.controller;

import com.baidu.ueditor.ActionEnter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 富文本处理统一请求接口：
 * 1.处理富文本初始化的时候默认发送的请求获取后端配置json
 * 2.处理富文本ajax文件上传逻辑
 */
@RestController
public class UEditorController {

    @Value("${realPath}")
    private  String realPath;
    @Value("${uploadPath}")
    private String uploadPath;
    /*
    每次执行请求，都必须返回json对象
    如果是文件上传操作，必须是指定返回格式的对象
    action参数：用于统一请求处理接口区分是哪个处理逻辑
     */
    @RequestMapping("doUeditor")
    public String doUeditor(String action, HttpServletRequest request, MultipartFile upfile){
        String result = null;
        if("config".equals(action)){//请求获取服务器配置对象
            result =  new ActionEnter( request, request.getContextPath() ).exec() ;
        }else if("uploadimage".equals(action)){//单图片上传处理
            //1.文件上传
            File dir = new File(realPath);

            if (!dir.exists()){
                dir.mkdirs();
            }

            String originalFilename = upfile.getOriginalFilename();
            String type = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            String target = uuid + type; //真实路径
            String url = uploadPath+target;//虚拟路径

            try {
                upfile.transferTo(new File(dir,target));
                result = new JSONObject(resultMap("SUCCESS",originalFilename,upfile.getSize(),target,type,url)).toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = new JSONObject(resultMap("FAIL",originalFilename,0,null,null,null)).toString();
            }
        }
        //2.返回响应结果对象  必须是指定格式的
        return result;
    }


    //{"state": "SUCCESS","original": "111.jpg","size": "124147","title": "1535961757878095151.jpg","type": ".jpg","url": "/1535961757878095151.jpg"}
    private Map<String,Object> resultMap(String state, String original, long size, String title,String type,  String url){
        Map<String ,Object> result = new HashMap<>();
        result.put("state",state);
        result.put("original",original);
        result.put("size",size);
        result.put("title",title);
        result.put("type",type);
        result.put("url", url);
        return result;
    }
}
