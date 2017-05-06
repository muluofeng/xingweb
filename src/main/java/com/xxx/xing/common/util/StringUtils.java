package com.xxx.xing.common.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author xing
 * @Created by 2017-04-17 下午4:46.
 */
public class StringUtils {
    public static String loadJson (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static Map<String,String> jsonStringToMap(String json){
        JSONObject jasonObject = new JSONObject(json);
        Map<String, String> data = new HashMap<>();
        // 将json字符串转换成jsonObject
        Iterator ite = jasonObject.keys();
        // 遍历jsonObject数据,添加到Map对象
        while (ite.hasNext()) {
            String key = ite.next().toString();
            String value = jasonObject.get(key).toString();
            data.put(key, value);
        }
        return data;
    }
}
