package com.futuretech.closet.utils;

import android.content.ContentValues;
import android.util.Log;

import com.futuretech.closet.model.SuitClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class JsonUtils {
    public static String getStatusCode(String response) {
        String status=null;
        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(response);
            status = jsonObject.getString("status");
            Log.d(TAG, "json: " + response);
            Log.d(TAG, "StatusCode: " + status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static JSONObject clothes2Json(ContentValues values){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("style",values.get("style"));
            jsonObject.put("color",values.get("color"));
            jsonObject.put("thickness",values.get("thickness"));
            jsonObject.put("attribute",values.get("attribute"));
            jsonObject.put("userid",values.get("userid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject clothes2JsonWithDressid(ContentValues values){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dressid",values.get("dressid"));
            jsonObject.put("style",values.get("style"));
            jsonObject.put("color",values.get("color"));
            jsonObject.put("thickness",values.get("thickness"));
            jsonObject.put("attribute",values.get("attribute"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject reqMatchJson(String userid,String location,String attribute,String past){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid",userid);
            jsonObject.put("location",location);
            jsonObject.put("attribute",attribute);
            jsonObject.put("past",past);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static List<SuitClass> matchJsonResolve(String resp){
        List<SuitClass> suits = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(resp);

            for (int i = 0; i < 12; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject != null) {
                    int dressid1 = jsonObject.optInt("dressid1");
                    int dressid2 = jsonObject.optInt("dressid2");
                    Log.d(TAG, "matchJsonResolve: dressid1:"+dressid1+" dressid2:"+dressid2);
                    // 封装Java对象
                    SuitClass suitClass = new SuitClass(dressid1,dressid2);

                    suits.add(suitClass);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return suits;
    }

    public static JSONObject sendSuitJson(String dressid1,String dressid2){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dressid1",dressid1);
            jsonObject.put("dressid2",dressid2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
