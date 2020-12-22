package com.sgq.tools;

import com.sgq.pojo.Goods;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author : sgq
 * Date : 2020/12/15 15:24
 */
public abstract class MyClient {
    public CloseableHttpResponse doOneRequest(Map map){

        return this.createOneRequest(map);
    }

    protected  CloseableHttpResponse createOneRequest(Map map){

        // 需要爬取商品信息的网站地址
        String url = (String) map.get("url_temp");
        // 动态模拟请求数据
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 模拟浏览器浏览（user-agent的值可以通过浏览器浏览，查看发出请求的头文件获取）
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
