package cn.jinronga.trend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/2/5 0005
 * Time: 19:35
 * E-mail:1460595002@qq.com
 * 类说明:
 */
@Controller
public class ViewController {


    @GetMapping("/") //访问任何路劲就返回到view.html
    public String view ()throws  Exception{

        return "view";//返回view.html
    }
}
