package com.xuuuuu.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/*
* 使用 EasyExcel，我们需要继承 AnalysisEventListener
 * */
public class ExcelListener extends AnalysisEventListener {

    //其中， invoke() 和 doAfterAllAnalysed() 是必须实现的方法。
    //在 invoke() 中，我们将数据封装到 list 中，再在控制器中，通过 getter() 方法获取数据，
    // 这样我们就可以获取到 easyexcel 帮我们解析好的数据，再将数据进行类型转化，这样，我们就可以对数据进行写入操作。
    private List<Object> datas = new ArrayList<Object>();

    public List<Object> getDatas() {
        return datas;
    }

//    invoke 方法逐行读取数据
    public void invoke(Object o, AnalysisContext analysisContext) {
        datas.add(o);//数据存储到list 供批量处理，或后续自己业务逻辑处理
    }

//    解析结束销毁不用的资源
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
