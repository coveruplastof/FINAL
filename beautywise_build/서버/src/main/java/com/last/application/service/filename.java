package com.last.application.service;

import com.last.application.dto.faceDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

@Controller
public class filename {
    @GetMapping("hihiaa")
    public ModelAndView mybatis(ModelAndView model, faceDTO facedto) {
        String directoryPath = System.getProperty("user.dir") + "/src/main/resources/static/img/";

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        System.out.println(System.getProperty("user.dir"));
        System.out.println("hi"+files[0]);
        model.setViewName("aicommunity"); // 뷰의 이름을 설정해야 합니다.
        return model;
    }
}