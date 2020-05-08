package com.sgq.controller;

import com.sgq.pojo.Goods;
import com.sgq.tools.GoodsTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NAGOYA on 2020/4/28.
 * @author sgq
 */
@Controller
public class SpiderController {
    @Resource
    private GoodsTool goodsTool;
    @RequestMapping(value = "/getDataBySpider.do",method = RequestMethod.POST)
    public String getDataBySpider(HttpServletRequest request,
                                  @RequestParam String time,
                                  @RequestParam String goods_type,
                                  @RequestParam String goods_counts,
                                  Model model) throws IOException {
        Map query_map = new HashMap();
        if(time!=null&&time!=""){
            query_map.put("time",time);
        }
        if(goods_type!=null&&goods_type!=""){
            query_map.put("goods_type",goods_type);
        }
        if(goods_counts!=null&&goods_counts!=""){
            query_map.put("goods_counts",goods_counts);
        }
        if(query_map.size()>0){
            List<Goods> goodsList= goodsTool.getSpiderData(query_map);
            model.addAttribute("goodsList",goodsList);
        }else {
            model.addAttribute("stat","error");
        }
         return "page/welcome";
    }

    @RequestMapping(value = "/toQuery.do",method = RequestMethod.GET)
    public String toQuery(){
        return "page/query";
    }


}
