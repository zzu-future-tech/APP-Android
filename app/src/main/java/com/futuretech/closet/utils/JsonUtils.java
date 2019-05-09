package com.futuretech.closet.utils;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
}
