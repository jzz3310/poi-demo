package com.xuuuuu.controller;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xuuuuu.mapper.CatagoryMapper;
import com.xuuuuu.pojo.Catagory;
import com.xuuuuu.service.ExcleService;
import com.xuuuuu.util.ExcelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 1、什么是EasyExcel，有什么作用？
EasyExcel是一个基于Java的简单、省内存的读写Excel的开源项目。在尽可能节约内存的情况下支持读写百M的Excel。

2、为什么选择EasyExcel，而不是Apache poi或者jxl?
　　Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，
* poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在
* 内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版Excel的解析，能够原本一个3M的excel用POI sax依然需要100M左右内
* 降低到几M，并且再大的excel不会出现内存溢出，03版依赖POI的sax模式。在上层做了模型转换的封装，让使用者更加简单方便。
* */
@Controller
public class ExcleController {

    @Autowired
    ExcleService excleService;

    @Autowired
    CatagoryMapper catagoryMapper;

//    这是导入 Excel 的控制器，实现思路与导入的思路类似，不过这个不需添加响应头信息。
    @RequestMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        //传入参数
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
        //读取信息
        excelReader.read(new Sheet(1, 1, Catagory.class));

        //获取数据
        List<Object> list = listener.getDatas();
        System.out.println(list.size());

        Catagory catagory = new Catagory();

        //转换数据类型,并插入到数据库
        for (int i = 0; i < list.size(); i++) {
            catagory = (Catagory) list.get(i);
            catagoryMapper.insertCategory(catagory);
        }
        System.out.println("导入成功");
        System.out.println("88888888888888888888888888888888888888888888888");
        return "redirect:index";
    }

    //这是导出 Excel 的控制器，导出的思路也很简单。
    @RequestMapping("/expor")
    public String exporExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {

//            添加响应头信息；
            response.setHeader("Content-disposition", "attachment; filename=" + "catagory.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头
//            添加 ExcelWriter；
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);
//            添加 Sheet（表单）；
            Sheet sheet = new Sheet(1, 0, Catagory.class);
            sheet.setSheetName("目录");

            List<Catagory> catagoryList = excleService.findAll();
//            添加数据；
            writer.write(catagoryList, sheet);

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                writer.finish();
        }
//        输出。
        System.out.println("导出成功");
        System.out.println("6666666666666666666666666666666666");

        return "index";
    }

    @RequestMapping("/index")
    public String list(Model model){

        List<Catagory> list = catagoryMapper.findAll();
        model.addAttribute("list",list);
        return  "index";

    }
}
