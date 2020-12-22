package com.sgq.controller;

import com.sgq.dao.PersonMapper;
import com.sgq.dao.StudentMapper;
import com.sgq.pojo.Student;
import com.sgq.response.BaseResponse;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.model.ITemplateEnd;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Author : sgq
 * Date : 2020/12/8 14:46
 */
@Controller
public class MessageSendController {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private PersonMapper personMapper;

    private int count = 0;

    ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com","102898","3502fc2f-4aea-4899-aa90-766efa59cc8d");

    @RequestMapping(value = "/")
    public String  home(){
        return "/page/login_page";
    }

    /**
     * send the Email to the phone
     */
    @RequestMapping(value = "/sendCode.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BaseResponse sendEmail( HttpServletResponse res,
                                  @RequestParam String name,
                                  @RequestParam String time,
                                   @RequestParam String action )  {
        System.out.println(name);
        res.setContentType("text/html;charset=UTF-8");
        Student student = studentMapper.selectByName(name);
        System.out.println("name:"+student.getName()+"-->电话:"+student.getPhone());
        boolean send_stat = getRandomForCodePhone(student.getName(),student.getPhone(),time,action);
          if(send_stat){
            return  BaseResponse.success();
          }
           return BaseResponse.fail();
    }

    private boolean getRandomForCodePhone(String name ,String phone,String time,String action) {
           boolean flag = false;

        try {
            Map message_map = new HashMap();
            message_map.put("number",phone);
            if(action.equals("homework")){
                message_map.put("templateId","2103");
            }else if(action.equals("test")){
                message_map.put("templateId","2628");
            }
            String[] templateParams = new String[2];
            templateParams[0] = name;
            templateParams[1] = time;
            message_map.put("templateParams", templateParams);
            String result =client.send(message_map);
            if(result.charAt(8)=='0'){
                flag = true;
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" test worm");
          return flag;
    }
    @RequestMapping("/f1.do")
    @ResponseBody
    public void f1(@RequestParam String s){
//        System.out.println(Arrays.toString(studentMapper.selectAll().toArray()));
        System.out.println(s);
        System.out.println(personMapper.selectByName(s));
    }
    @RequestMapping("/sendEmailByTeacher.do")
    @ResponseBody
    public BaseResponse sendEmailByTeacher(@RequestParam Integer id) throws Exception {
       List<Student> students = studentMapper.selectByTeachersStudent(id);
        Iterator<Student> iterator = students.iterator();
        int success_count = students.size();
        while (iterator.hasNext()){
            Map message_map = new HashMap();
            Student  student = iterator.next();
            System.out.println(student);
            String[] templateParams = new String[2];
            templateParams[0] = student.getName();
            templateParams[1] = student.getPhone();
            message_map.put("templateId","2103");
            message_map.put("number",student.getPhone());
            message_map.put("templateParams", templateParams);
            String result =client.send(message_map);
            if(result.charAt(8)=='0'){
                   count++;
            }
        }
        if(success_count!=count){
            System.out.println(success_count-count+"人发送失败");
            return BaseResponse.fail();
        }else {
            System.out.println(success_count+"人全部发送成功");
            return  BaseResponse.success();
        }

    }

}
