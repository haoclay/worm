package com.sgq.tools;

import com.sgq.pojo.Goods;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : sgq
 * Date : 2020/12/15 15:09
 */
public class HomeWorkTool extends MyClient{

    public void doParse() throws IOException {
        Map map = new HashMap(){{
            put("url_temp","https://www.ketangpai.com/Review/index/homeworkid/MDAwMDAwMDAwMLOGy5aIuclqhctyoQ.html");
        }};
        CloseableHttpResponse response = super.doOneRequest(map);
        int statusCode = response.getStatusLine().getStatusCode();
        try {
            HttpEntity entity = response.getEntity();
            // 如果状态响应码为200，则获取html实体内容或者json文件
            if(statusCode == 200){
                String html = EntityUtils.toString(entity, Consts.UTF_8);
                // 提取HTML得到作业信息结果
                Document doc = null;
                // doc获取整个页面的所有数据
                doc = Jsoup.parse(html);
//                System.out.println(doc);
                //输出doc可以看到所获取到的页面源代码
//
                // 通过浏览器查看作业页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
                Elements ulList = doc.select("div[class='sortBody']");
                Elements liList = ulList.select("dl[class='homeworkmanage']");

                // 循环liList的数据（具体获取的数据值还得看doc的页面源代码来获取，可能稍有变动）
                for (Element item : liList) {

                    // 课堂派ID
                    String id = item.select("dd[class='left']").attr("data-val");
                    System.out.println("作业名称："+id);
                    // 学生姓名
                    String name = item.select("dd[class='left usernamegai']").attr("data-val");
                    System.out.println("学生姓名："+name);
                    // 提交状态
                    String submit = item.select("dd[class='colaa batscore fraction']").attr("data-val");
                    System.out.println("提交状态："+submit);
                    System.out.println("------------------------------------");

                }
                // 消耗掉实体
                EntityUtils.consume(response.getEntity());
            } else {
                // 消耗掉实体
                EntityUtils.consume(response.getEntity());
            }
        } finally {
            response.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new HomeWorkTool().doParse();
    }
}
