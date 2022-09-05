package com.example.jumptostringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NAV {

    @RequestMapping("/nav")
        public String navbar (){
            return "nav";
        }

    @RequestMapping("/ham")
        public String nav(){
        return "ham";
    }
    @RequestMapping("/p")
    public String next(){
        return "paging";
    }
    @RequestMapping("/main")
    public String media(){
        return "mainpage";
    }
}
