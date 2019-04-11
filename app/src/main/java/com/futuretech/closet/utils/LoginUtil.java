package com.futuretech.closet.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginUtil {
    private static boolean sendGETRequest(String path, Map<String, String> params, String encode) throws MalformedURLException, IOException {
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            url.append(URLEncoder.encode(entry.getValue(), encode));
            url.append("&");
        }
        //删掉最后一个&
        url.deleteCharAt(url.length() - 1);
        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 100) {
            return true;
        }
        return false;
    }

    public boolean check(String email, String psw) {


        String path = "";
        //将用户名和密码放入HashMap中
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", psw);
        try {
            return sendGETRequest(path, params, "UTF-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

