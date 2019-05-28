package com.wu.controller;/**
 * by wyz on 2019/5/25/025.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: buildss
 *
 * @description:
 *
 * @author: Mr.Wu
 *
 * @create: 2019-05-25 10:07
 **/
@Controller
public class IndexController {


    @RequestMapping("/")
    public String indexShow(Model model){
        return "index";
    }
    @RequestMapping("/testjsp")
    public String testShow(Model model){
        return "test";
    }
}
