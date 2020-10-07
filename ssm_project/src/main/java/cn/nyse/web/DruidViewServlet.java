package cn.nyse.web;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/druid/*",
        initParams = {
        @WebInitParam(name = "loginUsername",value = "druid"),
        @WebInitParam(name = "loginPassword",value = "123"),
        @WebInitParam(name = "druid-user",value = "loginUser")
        }
)
public class DruidViewServlet extends StatViewServlet {
}
