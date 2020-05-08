package com.sgq.controller;

import com.alibaba.fastjson.JSONObject;
import com.sgq.dao.BrandMapper;
import com.sgq.pojo.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by NAGOYA on 2020/4/29.
 * @author sgq
 */
@Controller
public class BrandController {
    @Resource
    private BrandMapper brandMapper;
    @ResponseBody
    @RequestMapping("/insertBrand.do")
    public Map insertBrand(@RequestBody List<JSONObject> arr_list){
        System.out.println("插入详情:"+arr_list);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Brand> brandList = new ArrayList<>();
          for(JSONObject json:arr_list){
               String clazz_name= (String) json.get("name");
               int class_id = brandMapper.selectClassificationIdByName(clazz_name);
               List<Map> clazz_list= (List) json.get("content");
               for(Map json1:clazz_list){
                   Brand brand = new Brand();
                   brand.setClassId(class_id);
                   brand.setBrandId((Integer) json1.get("id"));
                   brand.setBrandName((String) json1.get("name"));
                   brand.setModifyTime(simpleDateFormat.format(new Date()));
                   brandList.add(brand);
               }

          }
        int inset_before_size=brandList.size();
        int  inset_after_size=brandMapper.insertBatch(brandList);
        Map stat_map = new HashMap();
        if(inset_before_size==inset_after_size){
            stat_map.put("stat","ok");
            stat_map.put("data_size",inset_after_size);
        }else {
            stat_map.put("stat","error");
            brandMapper.deleteAll();
        }

        return stat_map;
    }
    @RequestMapping("/inertPage.do")
    public String  inertPage(){
       return "page/insertBrand";

    }
}
