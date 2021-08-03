package com.xuuuuu.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/*
这是一个实体类。
我们导出 Excel 时，有时需要表头，如果需要表头，
我们就可以在相应的实体类中加入 @ExcelProperty(value = "id", index = 0) 注解，
并且继承 BaseRowModel。
其中 value 代表在导出 Excel 时，该字段对应的表头名称；index 代表该字段对应的表头位置。
* */
public class Catagory extends BaseRowModel {
    @ExcelProperty(value = "id", index = 0)
    private Integer id;

    @ExcelProperty(value = "name", index = 1)
    private String name;

    public Catagory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Catagory() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
